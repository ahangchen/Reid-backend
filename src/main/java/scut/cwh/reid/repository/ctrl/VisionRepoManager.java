package scut.cwh.reid.repository.ctrl;

import scut.cwh.reid.domain.info.PersonImgInfo;
import scut.cwh.reid.repository.repo.PersonImgInfoRepo;

import java.util.List;

public class VisionRepoManager {
    public static int getImgCnt(PersonImgInfoRepo visionSensorRepository) {
        int imgCnt = 0;
        List<PersonImgInfo> lastVisionInfos = visionSensorRepository.findFirstByOrderByImgIdDesc();
        if(lastVisionInfos != null && lastVisionInfos.size() > 0) {
            imgCnt = lastVisionInfos.get(0).getImgId();
        }
        return imgCnt;
    }
}
