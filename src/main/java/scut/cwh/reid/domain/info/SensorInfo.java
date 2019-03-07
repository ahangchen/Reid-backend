package scut.cwh.reid.domain.info;

import java.util.Date;

public class SensorInfo {
    private Date captureTime;
    private int fromSensorId;
    public SensorInfo(){

    }

    public SensorInfo(Date captureTime, int fromSensorId) {
        this.captureTime = captureTime;
        this.fromSensorId = fromSensorId;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    public int getFromSensorId() {
        return fromSensorId;
    }

    public void setFromSensorId(int fromSensorId) {
        this.fromSensorId = fromSensorId;
    }
}
