package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.WifiInfo;

public interface WifiSensorRepository extends MongoRepository<WifiInfo, Integer>{
    WifiInfo findByFromSensorId(Integer fromSensorId);
}
