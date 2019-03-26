package scut.cwh.reid.domain.info;

import java.util.Date;
import java.util.List;

public class PersonImgInfo extends SensorInfo{
    private int imgId;
    private String imgUrl;
    private List<List<Integer>> boxes;

    public PersonImgInfo() {
        super();
    }

    public PersonImgInfo(Date captureTime, int fromSensorId, String imgPath, int imgId) {
        super(captureTime, fromSensorId);
        this.imgUrl = imgPath;
        this.imgId = imgId;
    }

    public PersonImgInfo(Date captureTime, int fromSensorId, String imgPath, int imgId, List<List<Integer>> boxes) {
        super(captureTime, fromSensorId);
        this.imgUrl = imgPath;
        this.boxes = boxes;
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
