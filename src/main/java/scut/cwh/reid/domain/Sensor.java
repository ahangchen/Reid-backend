package scut.cwh.reid.domain;

public class Sensor {
    private int id;
    private double latitude, longitude;
    private String macAddress;

    public Sensor() {
        this.id = -1;
        this.latitude = 0.;
        this.longitude = 0.;
        this.macAddress = "00:11:22:33:44:55";
    }

    public Sensor(int id, double latitude, double longitude, String macAddress) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.macAddress = macAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
