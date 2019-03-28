package scut.cwh.reid.repository.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.info.FiberInfo;

import java.util.Date;
import java.util.List;

public interface FiberInfoRepo extends MongoRepository<FiberInfo, Integer>{
    List<FiberInfo> findAllByFromSensorId(Integer fromSensorId);
    List<FiberInfo> findALLByCaptureTimeBetweenAndFromSensorId(Date startTime, Date endTime, Integer fromSensorId);

}
