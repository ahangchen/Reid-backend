package scut.cwh.reid.repository.ctrl;

import scut.cwh.reid.domain.Sensor;
import scut.cwh.reid.repository.repo.SensorRepo;

import java.util.List;

public class SensorManager {
    public static int getMaxSensorId(SensorRepo sensorRepository) {
        List<Sensor> sensors = sensorRepository.findFirstByOrderByIdDesc();
        if(sensors != null && sensors.size() > 0) {
            return sensors.get(0).getId();
        } else {
            return 0;
        }
    }
}
