package scut.cwh.reid.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.config.ResultEnum;
import scut.cwh.reid.domain.base.FileInfo;
import scut.cwh.reid.domain.base.FileType;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.utils.FileUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.io.*;

@Controller
@RequestMapping("/file")
@CrossOrigin
public class FileUploadController {
    @Autowired
    private FileServerProperties fileServerProperties;

    private Result saveFile(FileInfo fileInfo, MultipartFile file) {
        String retPath = FileUtils.saveRequestFile(fileInfo.getFilePath(), file);
        if (retPath != null) {
            fileInfo.updateInfo(retPath);
            return ResultUtil.success(fileInfo.getFileUrl());
        } else {
            return ResultUtil.error(ResultEnum.FILE_UPLOAD_ERROR);
        }
    }

    @PostMapping(value = "/img")
    @ResponseBody
    public Result saveImg(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), FileType.IMG, fileServerProperties);
        return saveFile(fileInfo, file);
    }

    @PostMapping(value = "/img/base64")
    @ResponseBody
    public Result saveBase64Img(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = new FileInfo("query_" + file.hashCode() + ".png", FileType.IMG, fileServerProperties);
        return saveFile(fileInfo, file);
    }

    @PostMapping(value = "/audio")
    @ResponseBody
    public Result saveAudio(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), FileType.AUDIO, fileServerProperties);
        return saveFile(fileInfo, file);
    }


    @PostMapping(value = "/other")
    @ResponseBody
    public Result saveOtherFile(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), FileType.OTHER, fileServerProperties);
        return saveFile(fileInfo, file);
    }
}
