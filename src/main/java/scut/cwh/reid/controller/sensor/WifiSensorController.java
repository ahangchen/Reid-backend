package scut.cwh.reid.controller.sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.domain.WifiInfo;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


@RequestMapping("/sensor")
@Controller
@CrossOrigin
public class WifiSensorController {
    // http://www.dongee.net/2016/08/10/springboot%E8%A1%A8%E5%8D%95%E6%8F%90%E4%BA%A4%E6%97%B6%E6%9C%89%E6%97%A5%E6%9C%9F%E7%9A%84%E6%A0%BC%E5%BC%8F%E5%8C%96%E7%BB%9F%E4%B8%80%E5%A4%84%E7%90%86/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @PostMapping(value = "/wifi")
    public @ResponseBody
    Result recordWifi(WifiInfo wifiInfo) {
        //save wifi info to db
        return ResultUtil.success(wifiSensorRepository.save(wifiInfo));
    }
}
