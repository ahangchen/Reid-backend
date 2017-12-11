package scut.cwh.reid.domain;

import java.util.Date;

public class WifiInfo extends SensorInfo{
    private String macAddress;

    public WifiInfo() {
        super();
    }
    public WifiInfo(Date captureTime, int fromSensorId, String macAddr) {
        super(captureTime, fromSensorId);
        this.macAddress = macAddr;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
