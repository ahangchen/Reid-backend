package scut.cwh.reid.repository.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.Sensor;

import java.util.List;

public interface SensorRepo extends MongoRepository<Sensor, Integer> {
    Sensor findById(Integer id);
    List<Sensor> findFirstByOrderByIdDesc();
    List<Sensor> findAll();
    List<Sensor> findAllByTypeEquals(String type);
    void removeByIdEquals(Integer id);
}
