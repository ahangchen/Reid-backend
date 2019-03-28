package scut.cwh.reid.api.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.info.FiberInfo;
import scut.cwh.reid.domain.map.Fiber2Camera;
import scut.cwh.reid.repository.repo.FiberInfoRepo;
import scut.cwh.reid.repository.repo.FiberMapRepo;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@Controller
@RequestMapping("/fiber")
public class FiberInfoController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    FiberInfoRepo fiberInfoRepo;
    @Autowired
    FiberMapRepo fiberMapRepo;

    @PostMapping(value="/map/record")
    public @ResponseBody Result mapRecord(@RequestBody Fiber2Camera fiber2Camera) {
        return ResultUtil.success(fiberMapRepo.save(fiber2Camera));
    }

    @PostMapping(value="/event/record")
    public @ResponseBody Result eventRecord(@RequestBody FiberInfo fiberInfo) {
//        return ResultUtil.success(fiberInfo);
        return ResultUtil.success(fiberInfoRepo.save(fiberInfo));
    }

    @GetMapping(value="/map/query")
    public @ResponseBody Result mapQuery(@RequestParam int fiberId) {
        return ResultUtil.success(fiberMapRepo.findAllByFiberId(fiberId));
    }

    @GetMapping(value="/event/query")
    public @ResponseBody Result eventQuery(@RequestParam int fiberId, Date start, Date end) {
        return ResultUtil.success(fiberInfoRepo.findALLByCaptureTimeBetweenAndFromSensorId(start, end, fiberId));
    }
}
