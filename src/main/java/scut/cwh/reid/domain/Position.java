package scut.cwh.reid.domain;

import java.util.Date;

public class Position {
    private Date captureTime;
    private String macAddress;
    private double x;
    private double y;

    public Position(double x,double y){
        this.x=x;
        this.y=y;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
