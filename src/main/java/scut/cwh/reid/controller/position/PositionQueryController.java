package scut.cwh.reid.controller.position;


import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.*;
import scut.cwh.reid.repository.SensorRepository;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.DateUtils;
import scut.cwh.reid.utils.PositionUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/position")
@Controller
public class PositionQueryController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @ResponseBody
    public Result findPositionBySensorIdAndTime(@RequestParam(defaultValue = "-1") List<Integer> id, @RequestParam Date startTime, @RequestParam Date endTime, @RequestParam(defaultValue = "-1") String macAddress) {

        //输入参数id只有一个且id=-1，查询所有传感器
        if(id.size()==1 && id.get(0)==-1){
            List<Sensor> sensors = sensorRepository.findAll();
            for(Sensor sensor:sensors){
                id.add(sensor.getId());
            }
        }

        List<WifiInfo> dataList = new ArrayList<>();
        for(int i=0;i<id.size();i++){
            List<WifiInfo> wifiList = wifiSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id.get(i));
            dataList.addAll(wifiList);
        }

        //按时间把WifiInfo分箱
        Map<String, Set<WifiInfo>> timeList = new HashMap<>();
        for(WifiInfo wifiInfo:dataList){
            String dateStr = DateUtils.formatDate(wifiInfo.getCaptureTime());
            if(timeList.containsKey(dateStr)){
                timeList.get(dateStr).add(wifiInfo);
            }else{
                timeList.put(dateStr,new HashSet<>());
                timeList.get(dateStr).add(wifiInfo);
            }

        }

        List<PositionInfo> positionInfoList=new ArrayList<>();

        for(Date i=startTime;!i.after(endTime);i=DateUtils.addSecond(i,1)){
            List<Position> positionList = new ArrayList<>();
            String dateStr = DateUtils.formatDate(i);

            if(timeList.containsKey(dateStr)){
                Set<WifiInfo> wifiInfosSet= timeList.get(dateStr);
                //处理同一时间内的WifiInfo，按macAddress分箱
                Map<String, Set<WifiInfo>> macList = new HashMap<>();
                for (WifiInfo wifiInfo:wifiInfosSet) {
                    if (macList.containsKey(wifiInfo.getMacAddress())) {
                        macList.get(wifiInfo.getMacAddress()).add(wifiInfo);
                    } else {
                        macList.put(wifiInfo.getMacAddress(), new HashSet<>());
                        macList.get(wifiInfo.getMacAddress()).add(wifiInfo);
                    }
                }

                //传感器位置 TODO:真实传感器位置
                double positionWiFi[][]= new double[100][2];
                positionWiFi[0][1]=180;
                positionWiFi[0][0]=380;

                positionWiFi[1][1]=880;
                positionWiFi[1][0]=380;

                positionWiFi[2][1]=330;
                positionWiFi[2][0]=880;

                for(Set<WifiInfo> wifiInfoSet:macList.values()){
                    //判断是否可定位,现在只判断是否三点以上 TODO：完善判断逻辑，去除相同点多次出现的情况
                    if(wifiInfoSet.size()>=3){

                        double positions[][] = new double[wifiInfoSet.size()][2];
                        double distances []= new double[wifiInfoSet.size()];

                        int index=0;
                        String tmacAddress="";
                        for(WifiInfo wifiInfo:wifiInfoSet){
                            tmacAddress=wifiInfo.getMacAddress();
                            positions[index][0] = positionWiFi[wifiInfo.getFromSensorId()][1];
                            positions[index][1] = positionWiFi[wifiInfo.getFromSensorId()][0];
                            //System.out.println(wifiInfo.getIntensity() + " " + wifiInfo.getFromSensorId()+ "! x:"+positions[index][0]+" y:"+positions[index][1]);
                            //TODO:信号强度对应真正的距离
                            distances[index] = wifiInfo.getIntensity()*10;
                            index++;
                        }

                        Position position = PositionUtils.calculatedPosition(positions,distances);
                        position.setCaptureTime(i);
                        position.setMacAddress(tmacAddress);

                        if(macAddress.equals("-1") || macAddress.equals(tmacAddress)){
                            positionList.add(position);
                        }
                    }
                }
            }

            PositionInfo positionInfo =new PositionInfo(i,positionList);
            positionInfoList.add(positionInfo);
        }

        return ResultUtil.success(positionInfoList);
    }

}
