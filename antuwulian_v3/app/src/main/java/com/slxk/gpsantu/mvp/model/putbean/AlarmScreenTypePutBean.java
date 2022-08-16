package com.slxk.gpsantu.mvp.model.putbean;

/**
 * 报警筛选类型
 */
public class AlarmScreenTypePutBean {

    /**
     * module : alarm
     * func : GetAlarmTypeList
     * params : {}
     */

    private String module;
    private String func;
    private ParamsBean params;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
    }
}
