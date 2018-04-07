package scut.cwh.reid.controller.reid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.domain.VisionInfo;
import scut.cwh.reid.repository.VisionSensorRepository;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@CrossOrigin
@RequestMapping("/sensor")
public class SensorQueryController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }

    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @GetMapping(value = "/wifi/list")
    @ResponseBody
    public Result findMacBySensorIdAndTime(@RequestParam Integer id, @RequestParam Date startTime, @RequestParam Date endTime) {
        //save img file to disk and store path info
        return ResultUtil.success(wifiSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id));
    }

    @Autowired
    private VisionSensorRepository visionSensorRepository;
    @GetMapping(value = "/vision/list")
    @ResponseBody
    public Result findImgBySensorIdAndTime(@RequestParam Integer id, @RequestParam Date startTime, @RequestParam Date endTime) {
        //save img file to disk and store path info
        return ResultUtil.success(visionSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id));
    }


}
