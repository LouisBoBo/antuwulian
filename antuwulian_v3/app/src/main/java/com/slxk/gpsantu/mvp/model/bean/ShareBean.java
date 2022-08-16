package com.slxk.gpsantu.mvp.model.bean;

/**
 * 分享bean
 */
public class ShareBean {

    private int id;
    private int drawableId; // 图片地址
    private String share; // 分享名称

    public ShareBean(int id, int drawableId, String share){
        super();
        this.id = id;
        this.drawableId = drawableId;
        this.share = share;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }
}
