package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.AudioInfo;
import scut.cwh.reid.domain.VisionInfo;

public interface AudioSensorRepository extends MongoRepository<AudioInfo, Integer> {
    AudioInfo findByFromSensorId(Integer fromSensorId);
}
