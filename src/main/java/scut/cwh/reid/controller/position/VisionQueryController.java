package scut.cwh.reid.controller.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.repository.VisionMacSensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@CrossOrigin
@RequestMapping("/vision")
public class VisionQueryController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }

    @Autowired
    private VisionMacSensorRepository visionMacSensorRepository;

    @GetMapping(value = "/list")
    @ResponseBody
    public Result findVisionByMacAddressAndTime(@RequestParam String macAddress, @RequestParam Date startTime, @RequestParam Date endTime) {
        return ResultUtil.success(visionMacSensorRepository.findAllByCaptureTimeBetweenAndMacAddress(startTime, endTime, macAddress));
    }
}
