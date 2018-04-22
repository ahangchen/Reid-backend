package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.SensorArea;

import java.util.List;

public interface SensorAreaRepository extends MongoRepository<SensorArea, Integer> {
    SensorArea findById(Integer id);
    List<SensorArea> findAll();
}
