package scut.cwh.reid.domain.map;

public class Fiber2Camera {
    private int fiberId;
    private int cameraId;
    private double startPos;
    private double endPos;

    public Fiber2Camera() {
        this.fiberId = 10;
        this.startPos = 0;
        this.endPos = 100.0;
        this.cameraId = -1;
    }

    public Fiber2Camera(int fiberId, int cameraId, double startPos, double endPos) {
        this.fiberId = fiberId;
        this.cameraId = cameraId;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public int getFiberId() {
        return fiberId;
    }

    public void setFiberId(int fiberId) {
        this.fiberId = fiberId;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public double getStartPos() {
        return startPos;
    }

    public void setStartPos(double startPos) {
        this.startPos = startPos;
    }

    public double getEndPos() {
        return endPos;
    }

    public void setEndPos(double endPos) {
        this.endPos = endPos;
    }
}
