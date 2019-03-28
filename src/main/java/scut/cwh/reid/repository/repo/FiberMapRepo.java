package scut.cwh.reid.repository.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import scut.cwh.reid.domain.info.FiberInfo;
import scut.cwh.reid.domain.map.Fiber2Camera;

import java.util.List;

public interface FiberMapRepo extends MongoRepository<Fiber2Camera, Integer>{
    List<Fiber2Camera> findAllByFiberId(int fiberId);
}
