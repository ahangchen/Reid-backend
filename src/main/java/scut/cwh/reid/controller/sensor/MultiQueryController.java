package scut.cwh.reid.controller.sensor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.domain.VisionInfo;
import scut.cwh.reid.domain.WifiInfo;
import scut.cwh.reid.repository.VisionSensorRepository;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.DateUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/multi")
@CrossOrigin
@Controller
public class MultiQueryController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private VisionSensorRepository visionSensorRepository;

    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @GetMapping(value = "/mac2vision")
    public @ResponseBody
    Result findImgByMacAndTime(@RequestParam String macAddress, @RequestParam Date startTime, @RequestParam Date endTime) {
        //save img file to disk and store path info
        List<WifiInfo> wifiInfos = wifiSensorRepository.findALLByCaptureTimeBetweenAndMacAddress(startTime, endTime, macAddress);
        // can be optimize to select in select
        List<VisionInfo> visionInfos = new LinkedList<>();
        for (WifiInfo wifiInfo: wifiInfos) {
            visionInfos.addAll(
                    visionSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(
                            DateUtils.addSecond(wifiInfo.getCaptureTime(), -15),
                            DateUtils.addSecond(wifiInfo.getCaptureTime(), 15),
                            wifiInfo.getFromSensorId()));
        }
        return ResultUtil.success(visionInfos);
    }

    @GetMapping(value = "/mac2sensor")
    public @ResponseBody
    Result findSensorByMacAndTime(@RequestParam String macAddress, @RequestParam Date startTime, @RequestParam Date endTime) {
        //save img file to disk and store path info
        return ResultUtil.success(
                wifiSensorRepository.findALLByCaptureTimeBetweenAndMacAddress(startTime, endTime, macAddress));
    }
}
