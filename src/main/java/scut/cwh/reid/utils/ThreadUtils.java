package scut.cwh.reid.utils;

public class ThreadUtils {
    public static void safeSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
