package scut.cwh.reid.api.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.PersonImgInfo;
import scut.cwh.reid.repository.PersonImgInfoRepo;
import scut.cwh.reid.repository.ctrl.VisionRepoManager;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Controller
@RequestMapping("/vision")
public class VisionInfoController {
    @Autowired
    private FileServerProperties fileServerProperties;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private PersonImgInfoRepo personImgInfoRepo;

    @PostMapping(value = "/record")
    public @ResponseBody
    Result recordImg(@RequestBody PersonImgInfo visionInfo) {
        //save img file to disk and store path info
        // TODO: sync may be needed
        int curImgCnt = VisionRepoManager.getImgCnt(personImgInfoRepo);
        visionInfo.setImgId(curImgCnt + 1);
        return ResultUtil.success(personImgInfoRepo.save(visionInfo));
    }


    @GetMapping(value = "/imgCnt")
    @ResponseBody
    public Result imgCnt() {
        int imgCnt = VisionRepoManager.getImgCnt(personImgInfoRepo);
        return ResultUtil.success(imgCnt);
    }

    @GetMapping(value = "/st2imgs")
    @ResponseBody
    public Result findImgBySensorIdAndTime(@RequestParam Integer sensorId, @RequestParam Date startTime, @RequestParam Date endTime) {
        //search with spatial and temporal info
        return ResultUtil.success(personImgInfoRepo.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, sensorId));
    }

    @GetMapping(value = "/img2st")
    @ResponseBody
    public Result findSensorIdAndTimeByImg(@RequestParam String filePath) {
        //find similarity imgs in db
        // retrieve img infos
        return ResultUtil.success(personImgInfoRepo.findAllByImgUrl(filePath));
    }

}