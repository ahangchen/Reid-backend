package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.Sensor;

public interface SensorRepository extends MongoRepository<Sensor, Integer> {
    Sensor findById(Integer id);
}
