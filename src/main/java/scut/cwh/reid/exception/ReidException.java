package scut.cwh.reid.exception;

import scut.cwh.reid.config.ResultEnum;

public class ReidException extends RuntimeException {
    private Integer code;

    public ReidException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
