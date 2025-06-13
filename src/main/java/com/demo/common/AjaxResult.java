package com.demo.common;

import java.util.HashMap;


public class AjaxResult extends HashMap<String, Object> {

    public AjaxResult() {

    }

    public AjaxResult(int code, String msg, Object data) {
        super.put("code", code);
        super.put("msg", msg);
        if (data != null) {
            super.put("data", data);
        }
    }


    public static AjaxResult success() {
        return AjaxResult.success("success");
    }


    public static AjaxResult success(Object data) {
        return new AjaxResult(200,  "success", data);
    }

    public static AjaxResult error() {
        return AjaxResult.success("系统异常");
    }

    public static AjaxResult error(String msg) {
        return new AjaxResult(500,  msg, null);
    }



}
