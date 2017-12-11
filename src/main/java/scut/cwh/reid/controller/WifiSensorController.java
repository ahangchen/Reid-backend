package scut.cwh.reid.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.domain.WifiInfo;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.ResultUtil;


@RequestMapping("/sensor")
@Controller
public class WifiSensorController {
    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @PostMapping(value = "/wifi")
    public @ResponseBody
    Result recordWifi(WifiInfo wifiInfo) {
        //TODO save img file to disk and store path info
        return ResultUtil.success(wifiSensorRepository.save(wifiInfo));
    }
}
