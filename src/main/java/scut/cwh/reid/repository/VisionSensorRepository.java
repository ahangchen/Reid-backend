package scut.cwh.reid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.info.VisionInfo;

import java.util.Date;
import java.util.List;

public interface VisionSensorRepository extends MongoRepository<VisionInfo, Integer> {
    List<VisionInfo> findAllByFromSensorId(Integer FromSensorId);
    List<VisionInfo> findALLByCaptureTimeBetweenAndFromSensorId(Date startTime, Date endTime, Integer fromSensorId);
    List<VisionInfo> findAllByImgPath(String imgPath);
    List<VisionInfo> findFirstByOrderByImgIdDesc();
}
