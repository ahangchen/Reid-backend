package scut.cwh.reid.api.info;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import scut.cwh.reid.config.DjangoServerProperties;
import scut.cwh.reid.config.FileServerProperties;
import scut.cwh.reid.domain.Sensor;
import scut.cwh.reid.domain.base.Result;
import scut.cwh.reid.domain.response.DetectResp;
import scut.cwh.reid.domain.info.PersonImgInfo;
import scut.cwh.reid.domain.response.TopNResp;
import scut.cwh.reid.repository.repo.PersonImgInfoRepo;
import scut.cwh.reid.repository.ctrl.VisionRepoManager;
import scut.cwh.reid.repository.repo.SensorRepo;
import scut.cwh.reid.utils.FileUtils;
import scut.cwh.reid.utils.NetworkUtils;
import scut.cwh.reid.utils.ResultUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/vision")
public class VisionInfoController {
    @Autowired
    private FileServerProperties fsp;

    @Autowired
    private DjangoServerProperties djangoServerProperties;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
    }
    @Autowired
    private PersonImgInfoRepo personImgInfoRepo;

    @Autowired
    private SensorRepo sensorRepo;

    @PostMapping(value = "/record")
    public @ResponseBody
    Result recordImg(@RequestBody PersonImgInfo visionInfo) {
        //save img file to disk and store path info
        // TODO: sync may be needed
        int curImgCnt = VisionRepoManager.getImgCnt(personImgInfoRepo);
        visionInfo.setImgId(curImgCnt + 1);
        return ResultUtil.success(personImgInfoRepo.save(visionInfo));
    }


    @GetMapping(value = "/imgCnt")
    @ResponseBody
    public Result imgCnt() {
        int imgCnt = VisionRepoManager.getImgCnt(personImgInfoRepo);
        return ResultUtil.success(imgCnt);
    }

    @GetMapping(value = "/st2imgs")
    @ResponseBody
    public Result findImgBySensorIdAndTime(@RequestParam Integer sensorId, @RequestParam Date startTime, @RequestParam Date endTime) {
        //search with spatial and temporal info
        return ResultUtil.success(personImgInfoRepo.findALLByCaptureTimeBetweenAndFromSensorId(startTime, endTime, sensorId));
    }

    @GetMapping(value = "/img2st")
    @ResponseBody
    public Result findSensorIdAndTimeByImg(@RequestParam String filePath) {
        //find similarity imgs in db
        // retrieve img infos
        return ResultUtil.success(personImgInfoRepo.findAllByImgUrl(filePath));
    }

    @PostMapping(value="/detect_query")
    @ResponseBody
    public Result detectQuery(@RequestParam String queryImgUrl) {
        String queryImgPath = FileUtils.url2Path(queryImgUrl, fsp);
//        return ResultUtil.success(queryImgPath);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("path", queryImgPath);
        String resultJson = NetworkUtils.postForm(djangoServerProperties.getDetectApi(), params);
//        return ResultUtil.success(resultJson);
        DetectResp detectResp = new Gson().fromJson(resultJson, DetectResp.class);
        detectResp.setAnnotate(FileUtils.path2Url(detectResp.getAnnotate(), fsp));
        for(int i = 0; i < detectResp.getPersons().size(); i ++) {
            detectResp.getPersons().set(i, FileUtils.path2Url(detectResp.getPersons().get(i), fsp));
        }
        return ResultUtil.success(detectResp);
    }

    @PostMapping(value="/topn")
    @ResponseBody
    public Result queryTopN(@RequestParam String queryUrl) {
        String queryImgPath = FileUtils.url2Path(queryUrl, fsp);
//        return ResultUtil.success(queryImgPath);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("img_path", queryImgPath);
        params.put("n", "100");
        String resultJson = NetworkUtils.postForm(djangoServerProperties.getTopnApi(), params);
//        return ResultUtil.success(resultJson);
        TopNResp topNResp = new Gson().fromJson(resultJson, TopNResp.class);
        return ResultUtil.success(topNResp);
    }

    @PostMapping(value="/fusion_topn")
    @ResponseBody
    public Result fusionTopN(@RequestParam String queryUrl, @RequestParam String camera, @RequestParam String time) {
        String queryImgPath = FileUtils.url2Path(queryUrl, fsp);
//        return ResultUtil.success(queryImgPath);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("img_path", queryImgPath);
        params.put("n", "100");
        params.put("c", camera);
        params.put("t", time);
        String resultJson = NetworkUtils.postForm(djangoServerProperties.getFusionTopnApi(), params);
//        return ResultUtil.success(resultJson);
        TopNResp topNResp = new Gson().fromJson(resultJson, TopNResp.class);
        return ResultUtil.success(topNResp);
    }

    @GetMapping(value="/recent")
    @ResponseBody
    public Result queryRecent(@RequestParam int perCamera) {
        List<Sensor> cameras = sensorRepo.findAllByTypeEquals("mp4");
        List<List<PersonImgInfo>> cameraPersonImgs = new ArrayList<List<PersonImgInfo>>();
        for (Sensor camera: cameras) {
            List<PersonImgInfo> personImgs = personImgInfoRepo.findAllByFromSensorIdOrderByImgIdDesc(camera.getId(), new PageRequest(0, perCamera));
//            for(int i = 0; i < personImgs.size(); i ++) {
//                personImgs.get(i).setImgUrl(personImgs.get(i).getImgUrl());
//            }
            cameraPersonImgs.add(personImgs);
        }
        return ResultUtil.success(cameraPersonImgs);
    }

    @GetMapping(value="/recent_detail")
    @ResponseBody
    public Result recentImgSeq(@RequestParam int cnt, @RequestParam int sensorId) {
        List<PersonImgInfo> personImgs = personImgInfoRepo.findAllByFromSensorIdOrderByImgIdDesc(sensorId, new PageRequest(0, cnt));
        return ResultUtil.success(personImgs);
    }
}