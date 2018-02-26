package scut.cwh.reid.domain;

import java.util.Date;
import java.util.List;

public class VisionInfo extends SensorInfo{
    private String imgPath;
    private List<List<Integer>> boxes;

    public VisionInfo() {
        super();
    }

    public VisionInfo(Date captureTime, int fromSensorId, String imgPath) {
        super(captureTime, fromSensorId);
        this.imgPath = imgPath;
    }

    public VisionInfo(Date captureTime, int fromSensorId, String imgPath, List<List<Integer>> boxes) {
        super(captureTime, fromSensorId);
        this.imgPath = imgPath;
        this.boxes = boxes;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<List<Integer>> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<List<Integer>> boxes) {
        this.boxes = boxes;
    }
}
