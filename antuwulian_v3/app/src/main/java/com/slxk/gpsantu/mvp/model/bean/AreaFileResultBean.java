package com.slxk.gpsantu.mvp.model.bean;


import com.slxk.gpsantu.mvp.model.entity.BaseBean;

/**
 * 获取区域文件
 */
public class AreaFileResultBean extends BaseBean {

    /**
     * path : b4c5969d-57e0-46d7-9366-814cf4a3e3b2
     * version : 1649726603
     */

    private String path; // 文件路径
    private int version; // 文件版本

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
