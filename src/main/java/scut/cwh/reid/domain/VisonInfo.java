package scut.cwh.reid.domain;

public class VisonInfo extends SensorInfo{
    private String imgPath;

    public VisonInfo(int year, int month, int day, int hour, int minute, int second, int fromSensorId, String imgPath) {
        super(year, month, day, hour, minute, second, fromSensorId);
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
