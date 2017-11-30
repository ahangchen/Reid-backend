package scut.cwh.reid.domain;

public class WifiInfo extends SensorInfo{
    private String macAddress;

    public WifiInfo(int year, int month, int day, int hour, int minute, int second, int fromSensorId, String macAddr) {
        super(year, month, day, hour, minute, second, fromSensorId);
        this.macAddress = macAddr;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
