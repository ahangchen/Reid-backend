package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.Sensor;

import java.util.List;

public interface SensorRepository extends MongoRepository<Sensor, Integer> {
    Sensor findById(Integer id);
    List<Sensor> findFirstByOrderByIdDesc();
    List<Sensor> findAll();
    void removeByIdEquals(Integer id);
}
