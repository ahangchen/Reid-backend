package scut.cwh.reid.domain;

public class Sensor {
    private int id;
    private double latitude, longitude;
    private String macAddress;
    private String remoteUrl;
    private String type;
    private String desp;
    //type, desp

    public Sensor() {
        this.id = -1;
        this.latitude = 0.;
        this.longitude = 0.;
        this.macAddress = "00:11:22:33:44:55";
        this.remoteUrl = "";
        this.type = "mp4";
        this.desp = "";
    }

    public Sensor(int id, double latitude, double longitude, String macAddress, String remoteUrl, String type, String desp) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.macAddress = macAddress;
        this.remoteUrl = remoteUrl;
        this.type = type;
        this.desp = desp;
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

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
