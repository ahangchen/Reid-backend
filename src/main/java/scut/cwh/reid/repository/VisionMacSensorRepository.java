package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.VisionMacInfo;

import java.util.Date;
import java.util.List;

public interface VisionMacSensorRepository  extends MongoRepository<VisionMacInfo, Integer> {
    List<VisionMacInfo> findAllByFromSensorId(Integer FromSensorId);
    List<VisionMacInfo> findALLByCaptureTimeBetweenAndFromSensorId(Date startTime, Date endTime, Integer fromSensorId);
    List<VisionMacInfo> findAllByCaptureTimeBetweenAndMacAddress(Date startTime, Date endTime, String macAddress);
    List<VisionMacInfo> findAllByCaptureTimeBetween(Date startTime, Date endTime);
}
