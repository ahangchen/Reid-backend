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

    public Position calculationPosition(Set<WifiInfo> wifiInfoSetPre) {

        double positionWiFi[][]= new double[100][2];
        positionWiFi[0][1]=0;
        positionWiFi[0][0]=0;

        positionWiFi[1][1]=10;
        positionWiFi[1][0]=1;

        positionWiFi[2][1]=6;
        positionWiFi[2][0]=9;

        //对同一传感器id的Wi-Fi信号求平均值
        Map<Integer,Set> wifiInfoMap=new HashMap<>();

        for(WifiInfo wifiInfo:wifiInfoSetPre){
            if(wifiInfoMap.containsKey(wifiInfo.getFromSensorId())){
                wifiInfoMap.get(wifiInfo.getFromSensorId()).add(wifiInfo);
            }else{
                Set wifiSet=new HashSet<>();
                wifiSet.add(wifiInfo);
                wifiInfoMap.put(wifiInfo.getFromSensorId(),wifiSet);
            }
        }

        Set<WifiInfo> wifiInfoSet=new HashSet();

        for (Set<WifiInfo> value : wifiInfoMap.values()) {
            int sum=0;
            WifiInfo finalWifiInfo=null;
            for(WifiInfo wifi:value){
                finalWifiInfo=wifi;
                sum=sum+wifi.getIntensity();
            }
            int avg=sum/value.size();
            finalWifiInfo.setIntensity(avg);
            wifiInfoSet.add(finalWifiInfo);
        }



        if(wifiInfoSet.size()>=3){

            double positions[][] = new double[wifiInfoSet.size()][2];
            double distances []= new double[wifiInfoSet.size()];

            int index=0;
            String tmacAddress="";
            Date date=null;
            for(WifiInfo wifiInfo:wifiInfoSet){
                tmacAddress=wifiInfo.getMacAddress();
                date=wifiInfo.getCaptureTime();
                positions[index][0] = positionWiFi[wifiInfo.getFromSensorId()][0];
                positions[index][1] = positionWiFi[wifiInfo.getFromSensorId()][1];
                //System.out.println(wifiInfo.getIntensity() + " " + wifiInfo.getFromSensorId()+ "! x:"+positions[index][0]+" y:"+positions[index][1]);

                distances[index] = getDistanceByIntensity(wifiInfo.getIntensity());
                index++;
            }

            Position position = PositionUtils.calculatedPosition(positions,distances);
            position.setCaptureTime(date);
            position.setMacAddress(tmacAddress);

            return position;
        }
        return null;
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

}