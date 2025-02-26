package com.example.educationmanagementsystem.common;

import lombok.Data;

@Data
public class PlainResult<T>{
    private T data;
    private int code;
    private String msg;
    private boolean success;

    public PlainResult() {}

    public static <T> PlainResult<T> success(T data) {
        PlainResult<T> result = new PlainResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    public static <T> PlainResult<T> fail(int code, String msg) {
        PlainResult<T> result = new PlainResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
