package com.git.common;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/27 10:24
 */
public enum ResponseCode {
    //系统模块操作状态
    SUCCESS(0,"操作成功"),
    ERROR(1,"操作失败"),
    SERVER_ERROR(500, "服务器异常"),

    //通用模块操作状态
    ILLEGAL_ARGUMENT(10000,"参数不合法"),
    REPETITIVE_OPERATION(10001,"请勿重复操作"),
    ACCESS_LIMIT(10002,"请求太频繁, 请稍后再试"),
    MAIL_SEND_SUCCESS(10003,"邮件发送成功");

    private Integer code;
    private String msg;

    ResponseCode(Integer code, String msg){
        this.code =  code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
