package scut.cwh.reid.controller.position;


import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.*;
import scut.cwh.reid.logic.PositionManager;
import scut.cwh.reid.repository.SensorAreaRepository;
import scut.cwh.reid.repository.SensorRepository;
import scut.cwh.reid.repository.VisionMacSensorRepository;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.DateUtils;
import scut.cwh.reid.utils.PositionUtils;
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
    private SensorAreaRepository sensorAreaRepository;
    @Autowired
    private WifiSensorRepository wifiSensorRepository;
    @Autowired
    private VisionMacSensorRepository visionMacSensorRepository;

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @ResponseBody
    public Result findPositionBySensorIdAndTime(@RequestParam(defaultValue = "-1") List<Integer> id, @RequestParam Date startTime, @RequestParam Date endTime, @RequestParam(defaultValue = "-1") String macAddress) {

        List<SensorArea> sensorAreas = sensorAreaRepository.findAll();
        //输入参数id只有一个且id=-1，查询所有传感器
        if(id.size()==1 && id.get(0)==-1){
            for(SensorArea sensor:sensorAreas){
                id.add(sensor.getId());
            }
        }

        //查询用于计算位置的wifi信息
        List<WifiInfo> dataList = new ArrayList<>();
        for(int i=0;i<id.size();i++){
            List<WifiInfo> wifiList = wifiSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id.get(i));
            dataList.addAll(wifiList);
        }

        //查询用于计算位置的行人图片信息
        List<VisionMacInfo> dataList2 = new ArrayList<>();
        for(int i=0;i<id.size();i++){
            List<VisionMacInfo> visionList = visionMacSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id.get(i));
            dataList2.addAll(visionList);
        }

        List<PositionInfo> mainPositionInfoList = PositionManager.getInstance().queryPositionByWifiInfo(startTime,endTime,macAddress,dataList);
        List<PositionInfo> assisPositionInfoList = PositionManager.getInstance().queryPositionByVisionMacInfo(startTime,endTime,macAddress,dataList2, sensorAreas);

        List<PositionInfo> result = PositionManager.getInstance().deduplicationPositionInfo(mainPositionInfoList,assisPositionInfoList);


        return ResultUtil.success(result);
    }

}
