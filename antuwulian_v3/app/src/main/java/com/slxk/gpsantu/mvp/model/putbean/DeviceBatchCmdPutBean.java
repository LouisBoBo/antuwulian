package com.slxk.gpsantu.mvp.model.putbean;

import java.util.List;

/**
 * 批量下发指令(BatchCmd)
 */
public class DeviceBatchCmdPutBean {

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

        private List<String> simei; // 设备号
        //需要下发的指令((e_cmd_log_switch))
        //e_cmd_log_switch 设备日志开关，设置value附带值,如果0关,1开
        //e_cmd_set_dev_type 设置设备型号，设置str_value附带值,例如M1
        //e_cmd_set_protocol 更换协议，设置value附带值,1：JT808/2011；2：JT808/2013；3：JT808/2019；100：康凯斯ET130版本；101：康凯斯GT06E版本；110：天琴协议；默认1
        private String cmd;
        private String str_value; // 指令附带的值((M1))
        private Integer value; // 指令附带的值((0))

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getStr_value() {
            return str_value;
        }

        public void setStr_value(String str_value) {
            this.str_value = str_value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }
}
