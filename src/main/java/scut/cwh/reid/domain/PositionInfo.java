package scut.cwh.reid.domain;

import java.util.Date;
import java.util.List;

public class PositionInfo {
    private Date captureTime;
    private List<Position> positionList;

    public PositionInfo(Date captureTime,List<Position> positionList){
        this.captureTime=captureTime;
        this.positionList=positionList;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }
}
