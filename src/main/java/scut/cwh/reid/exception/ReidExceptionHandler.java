package scut.cwh.reid.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import scut.cwh.reid.domain.Result;
import scut.cwh.reid.utils.ResultUtil;

/**
 * Created by 廖师兄
 * 2017-01-21 13:59
 */
@ControllerAdvice
public class ReidExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(ReidExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof ReidException) {
            ReidException reidException = (ReidException) e;
            return ResultUtil.error(reidException.getCode(), reidException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return ResultUtil.error(-1, "未知错误");
        }
    }
}