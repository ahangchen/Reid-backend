package scut.cwh.reid.api.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.domain.base.FileInfo;
import scut.cwh.reid.domain.base.FileType;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.VisionInfo;
import scut.cwh.reid.repository.VisionSensorRepository;
import scut.cwh.reid.utils.FileUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Controller
@RequestMapping("/vision")
public class VisionSensorController {
    @Autowired
    private FileServerProperties fileServerProperties;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private VisionSensorRepository visionSensorRepository;

    @PostMapping(value = "/record")
    public @ResponseBody
    Result recordImg(@RequestBody VisionInfo visionInfo) {
        //save img file to disk and store path info
        return ResultUtil.success(visionSensorRepository.save(visionInfo));
    }

    @PostMapping(value = "/upload_record")
    public @ResponseBody
    Result uploadRecordImg(@RequestBody VisionInfo visionInfo, @RequestParam("file") MultipartFile file) {
        //save img file to disk and store path info
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), FileType.IMG, fileServerProperties);
        FileUtils.saveRequestFile(fileInfo.getFilePath(), file);
        visionInfo.setImgPath(fileInfo.getFilePath());
        return ResultUtil.success(visionSensorRepository.save(visionInfo));
    }

    @GetMapping(value = "/st2imgs")
    @ResponseBody
    public Result findImgBySensorIdAndTime(@RequestParam Integer sensorId, @RequestParam Date startTime, @RequestParam Date endTime) {
        //search with spatial and temporal info
        return ResultUtil.success(visionSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, sensorId));
    }

    @GetMapping(value = "/img2st")
    @ResponseBody
    public Result findSensorIdAndTimeByImg(@RequestParam String filePath) {
        //find similarity imgs in db
        // retrieve img infos
        return ResultUtil.success(visionSensorRepository.findAllByImgPath(filePath));
    }

}