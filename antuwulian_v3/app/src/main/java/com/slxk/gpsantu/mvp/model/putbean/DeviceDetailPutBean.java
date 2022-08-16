package com.slxk.gpsantu.mvp.model.putbean;

/**
 * 获取设备详细信息
 */
public class DeviceDetailPutBean {

    /**
     * module : device
     * func : GetDetail
     * params : {"simei":"abe0fece-6125-4ab5-beec-b83efcaa44dd"}
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
        /**
         * simei : abe0fece-6125-4ab5-beec-b83efcaa44dd
         */

        private String simei; // 加密的imei号
        private Boolean get_user; // 获取绑定用户信息,默认不获取
        private Boolean get_increment; //是否获取该设备支持的全部增值服务

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public Boolean getGet_user() {
            return get_user;
        }

        public void setGet_user(Boolean get_user) {
            this.get_user = get_user;
        }

        public Boolean getGet_increment() {
            return get_increment;
        }

        public void setGet_increment(Boolean get_increment) {
            this.get_increment = get_increment;
        }

    }
}
