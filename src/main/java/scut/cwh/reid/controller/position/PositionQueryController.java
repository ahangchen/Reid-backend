package scut.cwh.reid.controller.position;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.*;
import scut.cwh.reid.logic.PositionManager;
import scut.cwh.reid.repository.SensorAreaRepository;
import scut.cwh.reid.repository.VisionMacSensorRepository;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.DateUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/position")
@Controller
@CrossOrigin
public class PositionQueryController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }

    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @Autowired
    private VisionMacSensorRepository visionMacSensorRepository;

    @Autowired
    private SensorAreaRepository sensorAreaRepository;

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @ResponseBody
    public Result findPositionByTime(@RequestParam Date startTime, @RequestParam Date endTime) {

        List<PositionInfo> positionInfoList = new ArrayList<>();
        for(Date i=startTime;!i.after(endTime);i=DateUtils.addSecond(i,1)){
            List<WifiInfo> wifiInfoSet=wifiSensorRepository.findAllByCaptureTimeBetween(i,DateUtils.addSecond(i,10));
            List<VisionMacInfo> visionMacInfoList=visionMacSensorRepository.findAllByCaptureTimeBetween(i,DateUtils.addSecond(i,10));

            //按MacAddress分箱
            //处理同一时间内的WifiInfo，按macAddress分箱
            Map<String, Set<WifiInfo>> macList = new HashMap<>();
            for (WifiInfo wifiInfo:wifiInfoSet) {
                if (macList.containsKey(wifiInfo.getMacAddress())) {
                    macList.get(wifiInfo.getMacAddress()).add(wifiInfo);
                } else {
                    macList.put(wifiInfo.getMacAddress(), new HashSet<>());
                    macList.get(wifiInfo.getMacAddress()).add(wifiInfo);
                }
            }

            //处理同一时间内的VisionMacInfo，按macAddress分箱
            Map<String, Set<VisionMacInfo>> visionMacList = new HashMap<>();
            for (VisionMacInfo visionMacInfo:visionMacInfoList) {
                if(visionMacInfo.getMacAddress()==null)
                    continue;
                if (visionMacList.containsKey(visionMacInfo.getMacAddress())) {
                    visionMacList.get(visionMacInfo.getMacAddress()).add(visionMacInfo);
                } else {
                    visionMacList.put(visionMacInfo.getMacAddress(), new HashSet<>());
                    visionMacList.get(visionMacInfo.getMacAddress()).add(visionMacInfo);
                }
            }

            //计算出所有位置
            List<Position> positionList = new ArrayList<>();
            for(Set<WifiInfo> wifiInfos:macList.values()){
                Position position = PositionManager.getInstance().calculationPosition(wifiInfos);
                if(position!=null){
                    positionList.add(position);
                }
            }

            List<Position> positionListAssist = new ArrayList<>();
            for(Set<VisionMacInfo> visionMacInfos:visionMacList.values()){

                if(visionMacInfos.size()==0||visionMacInfos==null)
                    continue;
                Integer fromSensorId=null;
                for(VisionMacInfo visionMacInfo:visionMacInfos){
                    fromSensorId=visionMacInfo.getFromSensorId();
                    break;
                }
                if(fromSensorId==null){
                    continue;
                }

                SensorArea sensorArea=sensorAreaRepository.findById(fromSensorId);
                Position position = PositionManager.getInstance().calculatedPositionByVisionMacInfo(visionMacInfos,sensorArea);
                if(position!=null){
                    positionListAssist.add(position);
                }
            }

            //融合两种数据
            for(Position position:positionListAssist){
                String macAddr=position.getMacAddress();
                boolean exitInPositionList=false;
                for(Position position1:positionList){
                    if(position1.getMacAddress().equals(macAddr)){
                        exitInPositionList=true;
                        break;
                    }
                }
                if(!exitInPositionList){
                    positionList.add(position);
                }
            }

            PositionInfo positionInfo =new PositionInfo(i,positionList);
            positionInfoList.add(positionInfo);
        }

        return ResultUtil.success(positionInfoList);
    }

}
