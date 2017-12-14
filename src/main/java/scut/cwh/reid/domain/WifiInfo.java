package scut.cwh.reid.domain;

import java.util.Date;

public class WifiInfo extends SensorInfo{
    private String macAddress;
    private int intensity;

    public WifiInfo() {
        super();
    }
    public WifiInfo(Date captureTime, int fromSensorId, String macAddr, int intensity) {
        super(captureTime, fromSensorId);
        this.macAddress = macAddr;
        this.intensity = intensity;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
