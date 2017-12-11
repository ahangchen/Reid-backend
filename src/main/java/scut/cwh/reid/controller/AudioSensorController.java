package scut.cwh.reid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.domain.VisionInfo;


@RequestMapping("/sensor")
@Controller
public class AudioSensorController {
    @PostMapping(value = "/audio")
    public @ResponseBody
    String recordAudio(VisionInfo visionInfo) {
        //TODO save img file to disk and store path info
        return new FileServerProperties().getHost();
    }


}