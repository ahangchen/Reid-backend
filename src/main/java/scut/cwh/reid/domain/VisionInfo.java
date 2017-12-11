package scut.cwh.reid.domain;

import java.util.Date;

public class VisionInfo extends SensorInfo{
    private String imgPath;

    public VisionInfo(Date captureTime, int fromSensorId, String imgPath) {
        super(captureTime, fromSensorId);
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
