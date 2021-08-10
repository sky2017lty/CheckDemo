package com.poshing.checkdemo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author litianyi
 */
public class JsonUtils {
    private static final JsonUtils INSTANCE = new JsonUtils();

    private JsonUtils() {
    }

    public static JsonUtils getInstance() {
        return INSTANCE;
    }

    public String formatLayerJson(int code, String msg, int count, String data) {
        return "{\n" +
                "\"code\": " + code + ",\n" +
                "\"msg\": \"" + msg + "\",\n" +
                "\"count\": " + count + ",\n" +
                "\"data\": " + data + "\n" +
                "} ";
    }

    public String formatLayerJson(int code, String msg, JSONArray data) {
        if (data != null) {
            return "{\n" +
                    "\"code\":" + code + ",\n" +
                    "\"msg\":\"" + msg + "\",\n" +
                    "\"count\":" + data.size() + ",\n" +
                    "\"data\":" + data + "\n" +
                    "} ";
        } else {
            return "{\n" +
                    "\"code\": " + code + ",\n" +
                    "\"msg\": \"" + msg + "\"\n" +
                    "} ";
        }
    }

    public String formatLayerJson(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
}
