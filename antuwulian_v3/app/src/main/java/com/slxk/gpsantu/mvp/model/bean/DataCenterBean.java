package com.slxk.gpsantu.mvp.model.bean;

/**
 * 数据中心bean
 */
public class DataCenterBean {

    private int id; // 功能id
    private String function; // 功能名称
    private int drawableId; // 功能资源文件图片

    public DataCenterBean(int id, String function, int drawableId){
        super();
        this.id = id;
        this.function = function;
        this.drawableId = drawableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
