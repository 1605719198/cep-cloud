package com.jlkj.safety.si.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sudeyou
 */
public class ResponseUtil {

    public static  Page<Map<String, Object>> initPage(Map<String, Object> params) {
        int size = Integer.parseInt(params.get("size").toString());
        return new Page<>(Integer.parseInt(params.get("current").toString()), size);
    }

    public static Object toResult(Map<String, Object> params, Object data) {
        return toResult(params, "", data);
    }

    public static Object toResult(String msg) {
        return toResult(null, msg, null);
    }

    public static Object toResult(Map<String, Object> params) {
        return toResult(params, "", null);
    }

    public static Object toResult(Map<String, Object> params, String msg) {
        return toResult(params, msg, null);
    }

    public static Object toResult(Map<String, Object> params, String msg, Object data) {
        Map<String, Object> out = new HashMap<>(3);
        out.put("code", "0");
        out.put("msg", msg);
        if (null == data) {
            out.put("data", new HashMap<>(1));
        }
        else {
            out.put("data", data);
        }
        return out;
    }

    public static Object toResult(Map<String, Object> params, IPage<Map<String, Object>> list) {
        Map<String, Object> out = new HashMap<>(1);
        out.put("code", "0");
        out.put("msg", "成功");
        Map<String, Object> result = new HashMap<>(1);
        result.put("current", list.getCurrent());
        result.put("size", list.getSize());
        result.put("page", list.getPages());
        result.put("total", list.getTotal());
        result.put("records", list.getRecords());
        out.put("data", result);
        return out;
    }

    public static Object toError(Map<String, Object> params) {
        return toError(params, "服务器错误", null);
    }

    public static Object toError(Map<String, Object> params, String msg) {
        return toError(params, msg, null);
    }

    public static Object toError(Map<String, Object> params, Object data) {
        return toError(params, "", data);
    }

    public static Object toError(Map<String, Object> params, String msg, Object data) {
        Map<String, Object> out = new HashMap<>(3);
        out.put("code", -1);
        out.put("msg", msg);
        if (null == data) {
            out.put("data", new HashMap<>(1));
        }
        else {
            out.put("data", data);
        }
        return out;
    }

    public static Object toTree(Object data) {
        String jsonStr = JSONObject.toJSONString(data);
        jsonStr = jsonStr.replaceAll("\"true\"", "true").replaceAll("\"false\"", "false");
        return JSONObject.parseObject(jsonStr);
    }
}
