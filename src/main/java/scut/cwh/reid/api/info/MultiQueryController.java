package scut.cwh.reid.api.info;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.PersonImgInfo;
import scut.cwh.reid.domain.info.WifiInfo;
import scut.cwh.reid.repository.PersonImgInfoRepo;
import scut.cwh.reid.repository.WifiSensorRepository;
import scut.cwh.reid.utils.DateUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@CrossOrigin
@Controller
@RequestMapping("/multi")
public class MultiQueryController{
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private PersonImgInfoRepo visionSensorRepository;
    @Autowired
    private WifiSensorRepository wifiSensorRepository;

    @GetMapping(value = "/mac2vision")
    public @ResponseBody
    Result findImgByMacAndTime(@RequestParam String macAddress, @RequestParam Date startTime, @RequestParam Date endTime) {
        //mac2st
        List<WifiInfo> wifiInfos = wifiSensorRepository.findALLByCaptureTimeBetweenAndMacAddress(startTime, endTime, macAddress);
        List<PersonImgInfo> visionInfos = new LinkedList<>();
        for (WifiInfo wifiInfo: wifiInfos) {
            // st2img
            visionInfos.addAll(
                    visionSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(
                            DateUtils.addSecond(wifiInfo.getCaptureTime(), -5),
                            DateUtils.addSecond(wifiInfo.getCaptureTime(), 5),
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
