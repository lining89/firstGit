package com.git.common;


import java.io.Serializable;

/**
 * @author Mr ● Li
 * @version 1.0
 * @date 2021/4/27 10:10
 */
public class ServletResponse implements Serializable {

    private Integer status;
    private String msg;
    private Object data;

    public ServletResponse(){}

    public ServletResponse(Integer status, String msg, Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ServletResponse success(){
        return new ServletResponse(ResponseCode.SUCCESS.getCode(), null,null);
    }

    public static ServletResponse success(String msg){
        return new ServletResponse(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static ServletResponse success(Object data){
        return new ServletResponse(ResponseCode.SUCCESS.getCode(),null,data);
    }

    public static ServletResponse success(String msg, Object data){
        return new ServletResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static ServletResponse error(String msg){
        return new ServletResponse(ResponseCode.ERROR.getCode(), msg, null);
    }

    public static ServletResponse error(Object data){
        return new ServletResponse(ResponseCode.ERROR.getCode(), null,data);
    }

    public static ServletResponse error(String msg, Object data){
        return new ServletResponse(ResponseCode.ERROR.getCode(),msg, data);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
