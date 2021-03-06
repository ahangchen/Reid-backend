package scut.cwh.reid.api.info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.WifiInfo;
import scut.cwh.reid.repository.repo.WifiInfoRepo;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/wifi")
public class WifiInfoController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private WifiInfoRepo wifiSensorRepository;

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
        List<WifiInfo> wifiInfos = wifiSensorRepository.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, id);
        List<WifiInfo> filterWifiInfos = new ArrayList<>();
        Set<String> wifis = new HashSet<>();
        for (WifiInfo wifiInfo: wifiInfos) {
            if (!wifis.contains(wifiInfo.getMacAddress())) {
                wifis.add(wifiInfo.getMacAddress());
                filterWifiInfos.add(wifiInfo);
            }
        }
        return ResultUtil.success(filterWifiInfos);
    }

    @GetMapping(value = "/mac2st")
    @ResponseBody
    public Result findTrackByMac(@RequestParam String macAddress) {
        //save img file to disk and store path info
        return ResultUtil.success(wifiSensorRepository.findAllByMacAddress(macAddress));
    }
}
