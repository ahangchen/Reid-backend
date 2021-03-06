package scut.cwh.reid.repository.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.info.PersonImgInfo;

import java.util.Date;
import java.util.List;

public interface PersonImgInfoRepo extends MongoRepository<PersonImgInfo, Integer> {
    List<PersonImgInfo> findAllByFromSensorId(Integer FromSensorId);
    List<PersonImgInfo> findALLByCaptureTimeBetweenAndFromSensorId(Date startTime, Date endTime, Integer fromSensorId);
    List<PersonImgInfo> findAllByImgUrl(String imgPath);
    List<PersonImgInfo> findFirstByOrderByImgIdDesc();
    List<PersonImgInfo> findAllByFromSensorIdOrderByImgIdDesc(Integer fromSensorId, Pageable pageable);
}
