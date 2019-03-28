package scut.cwh.reid.domain.info;

import java.util.Date;

public class FiberInfo extends SensorInfo {
    private double position;
    private int eventType;
    private double confidence;

    public FiberInfo() {
        super();
        this.position = 0;
        this.eventType = 0;
        this.confidence = 0;
    }

    public FiberInfo(Date captureTime, int fromSensorId, double position, int eventType, double confidence) {
        super(captureTime, fromSensorId);
        this.position = position;
        this.eventType = eventType;
        this.confidence = confidence;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
