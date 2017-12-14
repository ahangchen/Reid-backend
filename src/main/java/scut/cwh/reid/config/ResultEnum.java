package scut.cwh.reid.config;

public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    FILE_UPLOAD_ERROR(-2, "文件上传出错"),
    FILE_EMPTY_ERROR(-2, "上传的文件是空的"),
    SUCCESS(0, "成功")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
