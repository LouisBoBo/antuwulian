package com.slxk.gpsantu.mvp.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.slxk.gpsantu.mvp.model.bean.DeviceLocationInfoBean;
import com.slxk.gpsantu.mvp.model.bean.PenetrateParamResultBean;

import java.lang.reflect.Type;

/**
 * 设备数据解析工具类
 */
public class DeviceUtils {

    private static Gson gson;

    /**
     * 解析设备更多信息
     *
     * @param locInfo
     * @return
     */
    public static DeviceLocationInfoBean parseDeviceData(String locInfo) {
        DeviceLocationInfoBean bean;
        if (TextUtils.isEmpty(locInfo)) {
            bean = new DeviceLocationInfoBean();
        } else {
            if (gson == null) {
                gson = new GsonBuilder()
                        .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                        .registerTypeAdapter(long.class, new LongDefault0Adapter())
                        .create();
            }
            bean = gson.fromJson(locInfo, DeviceLocationInfoBean.class);
        }
        return bean;
    }

    /**
     * 过滤时间返回异常
     */
    public static class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null") || json.getAsString().length() > 13) {//定义为long类型,如果后台返回""或者null,超过13位则返回0
                    return 0l;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }


    /**
     * 解析透传指令历史回复信息
     * @param param
     * @return
     */
    public static PenetrateParamResultBean paramPenetrateParamData(String param){
        PenetrateParamResultBean bean;
        if (TextUtils.isEmpty(param)){
            bean = new PenetrateParamResultBean();
        }else{
            bean = new Gson().fromJson(param, PenetrateParamResultBean.class);
        }
        return bean;
    }
}
