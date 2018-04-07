package scut.cwh.reid.controller.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.domain.AudioInfo;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.domain.VisionInfo;
import scut.cwh.reid.repository.AudioSensorRepository;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RequestMapping("/sensor")
@Controller

public class AudioSensorController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private AudioSensorRepository audioSensorRepository;

    @PostMapping(value = "/audio")
    public @ResponseBody
    Result recordAudio(AudioInfo audioInfo) {
        //save audio file to disk and store path info
        return ResultUtil.success(audioSensorRepository.save(audioInfo));
    }
}