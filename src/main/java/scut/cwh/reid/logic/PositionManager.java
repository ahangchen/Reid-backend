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

    //用探测的wifi信息根据三边定位计算位置
    public List<PositionInfo> queryPositionByWifiInfo(Date startTime, Date endTime, String macAddress, List<WifiInfo> dataList ) {
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
                positionWiFi[0][1]=6;
                positionWiFi[0][0]=2;

                positionWiFi[1][1]=4;
                positionWiFi[1][0]=2;

                positionWiFi[2][1]=6;
                positionWiFi[2][0]=10;

                for(Set<WifiInfo> wifiInfoSet:macList.values()){
                    //判断是否可定位,现在只判断是否三点以上 TODO：完善判断逻辑，去除相同点多次出现的情况
                    if(wifiInfoSet.size()>=3){

                        double positions[][] = new double[wifiInfoSet.size()][2];
                        double distances []= new double[wifiInfoSet.size()];

                        int index=0;
                        String tmacAddress="";
                        for(WifiInfo wifiInfo:wifiInfoSet){
                            tmacAddress=wifiInfo.getMacAddress();
                            positions[index][0] = positionWiFi[wifiInfo.getFromSensorId()][0];
                            positions[index][1] = positionWiFi[wifiInfo.getFromSensorId()][1];
                            //System.out.println(wifiInfo.getIntensity() + " " + wifiInfo.getFromSensorId()+ "! x:"+positions[index][0]+" y:"+positions[index][1]);

                            distances[index] = getDistanceByIntensity(wifiInfo.getIntensity());
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

    public double getDistanceByIntensity(int intensity){
        double distance=0;
        if (intensity>-30){
            distance=0;
        }else{
            distance=-intensity/10;
        }
        return distance;
    }

    //用探测的行人图片信息，根据传感器对应区域计算位置
    public List<PositionInfo> queryPositionByVisionMacInfo(Date startTime, Date endTime, String macAddress, List<VisionMacInfo> dataList, List<SensorArea> sensorAreas){

        List<PositionInfo> positionInfoList=new ArrayList<>();

        //按时间把VisionMacInfo分箱
        Map<String, Set<VisionMacInfo>> timeList = new HashMap<>();
        for(VisionMacInfo visionMacInfo:dataList){
            String dateStr = DateUtils.formatDate(visionMacInfo.getCaptureTime());
            if(timeList.containsKey(dateStr)){
                timeList.get(dateStr).add(visionMacInfo);
            }else{
                timeList.put(dateStr,new HashSet<>());
                timeList.get(dateStr).add(visionMacInfo);
            }

        }

        for(Date i=startTime;!i.after(endTime);i=DateUtils.addSecond(i,1)){

            List<Position> positionList = new ArrayList<>();
            String dateStr = DateUtils.formatDate(i);

            if(timeList.containsKey(dateStr)){
                Set<VisionMacInfo> visionMacInfoSet= timeList.get(dateStr);
                //处理同一时间内的VisionMacInfo，按macAddress分箱
                Map<String, Set<VisionMacInfo>> visionMacInfoList = new HashMap<>();
                for (VisionMacInfo visionMacInfo:visionMacInfoSet) {
                    if (visionMacInfoList.containsKey(visionMacInfo.getMacAddress())) {
                        visionMacInfoList.get(visionMacInfo.getMacAddress()).add(visionMacInfo);
                    } else {
                        visionMacInfoList.put(visionMacInfo.getMacAddress(), new HashSet<>());
                        visionMacInfoList.get(visionMacInfo.getMacAddress()).add(visionMacInfo);
                    }
                }

                for(Set<VisionMacInfo> visionMacInfos:visionMacInfoList.values()){
                    String tmacAddress="";
                    Integer sensorId=-1;
                    if(visionMacInfos.size()>0){

                        for(VisionMacInfo visionMacInfo:visionMacInfos){
                            tmacAddress=visionMacInfo.getMacAddress();
                            sensorId=visionMacInfo.getFromSensorId();
                            break;
                        }
                        SensorArea curSenSorArea=null;
                        for(SensorArea sensorArea:sensorAreas){
                            if(sensorArea.getId() == sensorId){
                                curSenSorArea=sensorArea;
                                break;
                            }
                        }
                        Position position = PositionUtils.centerOfSensorArea(curSenSorArea);
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

    public List<PositionInfo> deduplicationPositionInfo(List<PositionInfo> mainInfo, List<PositionInfo> assistInfo){
        List<PositionInfo> result = new ArrayList<PositionInfo>();
        result.addAll(mainInfo);


        for(int i=0;i<mainInfo.size();i++){
            //遍历assistInfo里面同个时间的的位置信息，把result里面不存在的信息加到result里面
            for (Position position:assistInfo.get(i).getPositionList()){
                String macAddress = position.getMacAddress();
                if(macAddress==null){
                    continue;
                }
                //查找在result中是否存在
                boolean isIn=false;
                for(Position position1:result.get(i).getPositionList()){
                    if (position1.getMacAddress().equals(macAddress)){
                        isIn=true;
                        break;
                    }
                }
                //如果不存在，加入result
                if(!isIn){
                    result.get(i).getPositionList().add(position);
                }
            }
        }

        return result;
    }

}