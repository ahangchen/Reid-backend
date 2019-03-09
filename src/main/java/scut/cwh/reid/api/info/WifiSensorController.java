package scut.cwh.reid.api.info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.WifiInfo;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Controller
@RequestMapping("/wifi")
public class WifiSensorController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @PostMapping(value = "/record")
    public @ResponseBody
    Result recordWifi(WifiInfo wifiInfo) {
        //save wifi info to db
        return ResultUtil.success(wifiSensorRepository.save(wifiInfo));
    }

    @GetMapping(value = "/st2mac")
    @ResponseBody
    public Result findMacBySensorIdAndTime(@RequestParam Integer id, @RequestParam Date startTime, @RequestParam Date endTime) {
        //save img file to disk and store path info
        return ResultUtil.success(wifiSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id));
    }
}
