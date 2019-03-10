package scut.cwh.reid.repository.ctrl;

import scut.cwh.reid.domain.info.VisionInfo;
import scut.cwh.reid.repository.VisionSensorRepository;

import java.util.List;

public class VisionRepoManager {
    public static int getImgCnt(VisionSensorRepository visionSensorRepository) {
        int imgCnt = 0;
        List<VisionInfo> lastVisionInfos = visionSensorRepository.findFirstByOrderByImgIdDesc();
        if(lastVisionInfos != null && lastVisionInfos.size() > 0) {
            imgCnt = lastVisionInfos.get(0).getImgId();
        }
        return imgCnt;
    }
}
