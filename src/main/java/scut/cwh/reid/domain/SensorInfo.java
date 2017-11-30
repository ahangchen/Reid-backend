package scut.cwh.reid.domain;

public class SensorInfo {
    private int year, month, day, hour, minute, second;
    private int fromSensorId;

    public SensorInfo(int year, int month, int day, int hour, int minute, int second, int fromSensorId) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.fromSensorId = fromSensorId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getFromSensorId() {
        return fromSensorId;
    }

    public void setFromSensorId(int fromSensorId) {
        this.fromSensorId = fromSensorId;
    }
}
