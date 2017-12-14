package scut.cwh.reid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.config.ResultEnum;
import scut.cwh.reid.domain.*;
import scut.cwh.reid.utils.ResultUtil;

import java.io.*;

@Controller
@RequestMapping("/file")
public class FileUploadController {
    @Autowired
    private FileServerProperties fileServerProperties;

    private Result saveFile(FileInfo fileInfo, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(fileInfo.getFilePath())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error(ResultEnum.FILE_UPLOAD_ERROR);
            }
            return ResultUtil.success(fileInfo.getFileUrl());
        } else {
            return ResultUtil.error(ResultEnum.FILE_EMPTY_ERROR);
        }
    }

    @PostMapping(value = "/img")
    @ResponseBody
    public Result saveImg(@RequestParam("file") MultipartFile file) {
        //TODO save img file to disk and store path info
        FileInfo fileInfo = new FileInfo(file.getOriginalFilename(), FileType.IMG, fileServerProperties);
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
