package scut.cwh.reid.logic;

import scut.cwh.reid.domain.*;
import scut.cwh.reid.utils.DateUtils;
import scut.cwh.reid.utils.PositionUtils;
import java.util.*;

public class PositionManager {

    private PositionManager(){

    }

    private static volatile PositionManager instance = null;

    public static PositionManager getInstance() {
        if (instance == null) {
            synchronized (PositionManager.class) {
                if (instance == null) {
                    instance = new PositionManager();
                }
            }
        }
        return instance;
    }

    public List<PositionInfo> queryPosition(Date startTime, Date endTime, String macAddress, List<WifiInfo> dataList ) {
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

                        //筛选符合macAddress的位置信息
                        if(macAddress.equals("-1") || macAddress.equals(tmacAddress)){
                            positionList.add(position);
                        }
                    }
                }
            }

            PositionInfo positionInfo =new PositionInfo(i,positionList);
            positionInfoList.add(positionInfo);
        }
        return positionInfoList;
    }

}