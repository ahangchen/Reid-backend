package scut.cwh.reid.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.VisionInfo;
import scut.cwh.reid.repository.VisionSensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


@RequestMapping("/vision")
@CrossOrigin
@Controller
public class VisionSensorController {
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

    @GetMapping(value = "/list")
    @ResponseBody
    public Result findImgBySensorIdAndTime(@RequestParam Integer id, @RequestParam Date startTime, @RequestParam Date endTime) {
        //search with spatial and temporal info
        return ResultUtil.success(visionSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id));
    }

}