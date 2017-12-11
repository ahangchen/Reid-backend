package scut.cwh.reid.domain;

import java.util.Date;

public class AudioInfo extends SensorInfo{
    private String audioPath;

    public AudioInfo(Date captureTime, int fromSensorId, String imgPath) {
        super(captureTime, fromSensorId);
        this.audioPath = imgPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
}
