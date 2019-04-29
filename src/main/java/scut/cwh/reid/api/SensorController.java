package scut.cwh.reid.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.Sensor;
import scut.cwh.reid.repository.repo.SensorRepo;
import scut.cwh.reid.repository.ctrl.SensorManager;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static scut.cwh.reid.config.ResultEnum.UNKNOW_SENSOR;

@CrossOrigin
@Controller
@RequestMapping("/sensor")
public class SensorController{
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }

    @Autowired
    private SensorRepo sensorRepository;

    @PostMapping(value = "/record")
    public @ResponseBody
    Result recordSensor(@RequestBody Sensor sensor) {
        //save img file to disk and store path info
        int maxSensorId = SensorManager.getMaxSensorId(sensorRepository);
        sensor.setId(maxSensorId + 1);
        return ResultUtil.success(sensorRepository.save(sensor));
    }

    @PostMapping(value = "/modify")
    public @ResponseBody
    Result modifySensor(@RequestBody Sensor sensor) {
        //save img file to disk and store path info
        return ResultUtil.success(sensorRepository.save(sensor));
    }

    @GetMapping(value="/cnt")
    public @ResponseBody
    Result sensorCount() {
        return ResultUtil.success(SensorManager.getMaxSensorId(sensorRepository));
    }
     
    @GetMapping(value = "/list")
    @ResponseBody
    public Result findAllSensors(){
        return ResultUtil.success(sensorRepository.findAll());
    }

    @GetMapping(value = "/list/type")
    @ResponseBody
    public Result findTypeSensors(@RequestParam String type){
        return ResultUtil.success(sensorRepository.findAllByTypeEquals(type));
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public Result removeSensor(@RequestParam int id) {
        if(sensorRepository.findById(id) != null) {
            sensorRepository.removeByIdEquals(id);
            return ResultUtil.success();
        } else {
            return ResultUtil.error(UNKNOW_SENSOR);
        }
    }
}
