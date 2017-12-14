package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.VisionInfo;

public interface VisionSensorRepository extends MongoRepository<VisionInfo, Integer> {
    VisionInfo findByFromSensorId(Integer fromSensorId);
}
