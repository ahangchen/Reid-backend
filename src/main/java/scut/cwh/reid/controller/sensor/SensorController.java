package scut.cwh.reid.controller.sensor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.Sensor;
import scut.cwh.reid.repository.SensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/sensor")
@CrossOrigin
@Controller
public class SensorController {
     @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private SensorRepository sensorRepository;

    @PostMapping(value = "/setting")
    public @ResponseBody
    Result recordSensor(@RequestBody Sensor sensor) {
        //save img file to disk and store path info
        return ResultUtil.success(sensorRepository.save(sensor));
    }
     
    @GetMapping(value = "/list")
    @ResponseBody
    public Result findAllSensors(){
        return ResultUtil.success(sensorRepository.findAll());
    }
}
