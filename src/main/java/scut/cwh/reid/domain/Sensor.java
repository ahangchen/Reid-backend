package scut.cwh.reid.domain;

public class Sensor {
    private long id;
    private double latitude, longitude;
    private String macAddress;

    public Sensor(long id, double latitude, double longitude, String macAddress) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.macAddress = macAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
