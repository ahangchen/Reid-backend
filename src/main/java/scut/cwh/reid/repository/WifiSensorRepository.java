package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.info.WifiInfo;

import java.util.Date;
import java.util.List;

public interface WifiSensorRepository extends MongoRepository<WifiInfo, Integer>{
    WifiInfo findByFromSensorId(Integer fromSensorId);
    List<WifiInfo> findALLByCaptureTimeBetweenAndFromSensorId(Date startTime, Date endTime, Integer fromSensorId);
    List<WifiInfo> findAllByCaptureTime(Date captureTime);
    List<WifiInfo> findAllByCaptureTimeBetween(Date startTime, Date endTime);
    List<WifiInfo> findALLByCaptureTimeBetweenAndMacAddress(Date startTime, Date endTime, String macAddress);
}
