package scut.cwh.reid.controller.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.domain.SensorArea;
import scut.cwh.reid.repository.SensorAreaRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/sensorArea")
@CrossOrigin
@Controller
public class SensorAreaSettingController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }

    @Autowired
    private SensorAreaRepository sensorAreaRepository;

    @PostMapping(value = "/setting")
    public @ResponseBody
    Result recordSensor(@RequestBody SensorArea sensorArea) {
        //save img file to disk and store path info
        return ResultUtil.success(sensorAreaRepository.save(sensorArea));
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public Result findAllSensors(){
        return ResultUtil.success(sensorAreaRepository.findAll());
    }
}
