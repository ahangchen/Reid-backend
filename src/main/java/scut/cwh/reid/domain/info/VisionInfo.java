package scut.cwh.reid.domain.info;

import java.util.Date;
import java.util.List;

public class VisionInfo extends SensorInfo{
    private int imgId;
    private String imgPath;
    private List<List<Integer>> boxes;

    public VisionInfo() {
        super();
    }

    public VisionInfo(Date captureTime, int fromSensorId, String imgPath, int imgId) {
        super(captureTime, fromSensorId);
        this.imgPath = imgPath;
        this.imgId = imgId;
    }

    public VisionInfo(Date captureTime, int fromSensorId, String imgPath, int imgId, List<List<Integer>> boxes) {
        super(captureTime, fromSensorId);
        this.imgPath = imgPath;
        this.boxes = boxes;
        this.imgId = imgId;
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

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
