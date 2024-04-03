package io.iqark.tcauth.pojo;

public class ErrorMsg {
    private Integer code;
    private String message;

    public ErrorMsg(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
