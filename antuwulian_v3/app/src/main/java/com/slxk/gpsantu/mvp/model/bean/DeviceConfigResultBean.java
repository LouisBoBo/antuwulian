package com.slxk.gpsantu.mvp.model.bean;

import com.slxk.gpsantu.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 获取设备的配置信息，支持的功能等
 */
public class DeviceConfigResultBean extends BaseBean {

    /**
     * monitor : 1079694953
     * mode_fun : e_mode_invalid|e_mode_loc|e_mode_rtls
     * history : 1079694953
     * trace : 1079694953
     * remoteswitch : 1079694953
     * record : 1079694953
     * ontime_switch : 1079694953
     * cmd_read : ["e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location","e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location","e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location","e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location"]
     * cmd_type : ["e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location","e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location","e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location","e_cmd_sleep|e_cmd_wakeup|e_cmd_restart|e_cmd_reset|e_cmd_finddev|e_cmd_query_location"]
     * config : {"speed_value":1079694953,"sstart_time":"bd812db2-e9eb-444e-8f61-6be271ba7ab3","car_switch":{"lowpower":1079694953,"lbsswitch":1079694953,"indicatorlight":1079694953,"fenceswitch":1079694953,"soundswitch":1079694953,"replaystate":1079694953,"openswitch":1079694953,"offswitch":1079694953,"alarmswitch":1079694953,"closeswitch":1079694953,"disconnswitch":1079694953,"dropalarm":1079694953},"language":"e_language_en|e_language_ch","locint_val":1079694953,"phone_book":"09a3a32d-6823-42ab-b4e2-35d0674dfca0","send_time":"905c17f7-47f2-49a9-af80-17fd011d81cb","shake_value":1079694953,"sleep_intval":1079694953}
     */

    private int monitor; // 是否有远程听音功能 0不支持,1支持
    private String mode_fun; // 支持的模式，e_mode_invalid 什么模式都不支持,e_mode_loc 支持多选的模式,e_mode_rtls 支持定时的模式
    private int history; // 是否有历史轨迹的能力, 0不支持,1支持
    private int trace; // 是否有追踪的能力 0不支持,1支持
    private int remoteswitch; // 是否可远程开关机, -1不支持,1支持,0支持，但是不可用
    private int record; // 是否有短录音或者实时录音或者声控录音的能力, 0不支持,1支持
    private int ontime_switch; // 是否有定时开关机功能, 0不支持,1支持
    private int phone_alarm ; // 是否支持电话报警,0不支持,1支持
    private ConfigBean config; // 配置信息
    private List<String> cmd_read; // 支持的指令相关,但是不可用
    private List<String> cmd_type; // 支持的指令相关
    private int protocol = 1; // 设备协议

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getMonitor() {
        return monitor;
    }

    public void setMonitor(int monitor) {
        this.monitor = monitor;
    }

    public String getMode_fun() {
        return mode_fun;
    }

    public void setMode_fun(String mode_fun) {
        this.mode_fun = mode_fun;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public int getTrace() {
        return trace;
    }

    public void setTrace(int trace) {
        this.trace = trace;
    }

    public int getRemoteswitch() {
        return remoteswitch;
    }

    public void setRemoteswitch(int remoteswitch) {
        this.remoteswitch = remoteswitch;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public int getOntime_switch() {
        return ontime_switch;
    }

    public void setOntime_switch(int ontime_switch) {
        this.ontime_switch = ontime_switch;
    }

    public int getPhone_alarm() {
        return phone_alarm;
    }

    public void setPhone_alarm(int phone_alarm) {
        this.phone_alarm = phone_alarm;
    }

    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public List<String> getCmd_read() {
        return cmd_read;
    }

    public void setCmd_read(List<String> cmd_read) {
        this.cmd_read = cmd_read;
    }

    public List<String> getCmd_type() {
        return cmd_type;
    }

    public void setCmd_type(List<String> cmd_type) {
        this.cmd_type = cmd_type;
    }

    public static class ConfigBean {
        /**
         * speed_value : 1079694953
         * sstart_time : bd812db2-e9eb-444e-8f61-6be271ba7ab3
         * car_switch : {"lowpower":1079694953,"lbsswitch":1079694953,"indicatorlight":1079694953,"fenceswitch":1079694953,"soundswitch":1079694953,"replaystate":1079694953,"openswitch":1079694953,"offswitch":1079694953,"alarmswitch":1079694953,"closeswitch":1079694953,"disconnswitch":1079694953,"dropalarm":1079694953}
         * language : e_language_en|e_language_ch
         * locint_val : 1079694953
         * phone_book : 09a3a32d-6823-42ab-b4e2-35d0674dfca0
         * send_time : 905c17f7-47f2-49a9-af80-17fd011d81cb
         * shake_value : 1079694953
         * sleep_intval : 1079694953
         */

        private int speed_value; // 超速阈值 0关闭，>0开启，-1不支持
        private String sstart_time = ""; // 报警时间段 00:00:00
        private String send_time = ""; // 报警时间段 00:00:00
        private CarSwitchBean car_switch; // 设备的开关
        private String language; // 设备当前语言 0：英文   1：中文，默认中文，e_language_en 0：英文，e_language_ch 1：中文，默认中文
        private int locint_val; // 运动定位上报间隔 单位秒 默认30s
        private String phone_book; // 设备电话本（管理员号码集合 用来唤醒设备）json字符串数组如:["15090011988","13108789898"]
        private int shake_value; // 震动报警程度0-关闭,(10-254)开启，-1不支持,
        private int sleep_intval; // 休眠定位上报间隔 单位秒 默认600s
        private PhoneAlarm phone_alarm; // 电话报警
        private List<String> notice_phones; //短信及电话报警 号码

        public List<String> getNotice_phones() {
            return notice_phones;
        }

        public void setNotice_phones(List<String> notice_phones) {
            this.notice_phones = notice_phones;
        }

        public int getSpeed_value() {
            return speed_value;
        }

        public void setSpeed_value(int speed_value) {
            this.speed_value = speed_value;
        }

        public String getSstart_time() {
            return sstart_time;
        }

        public void setSstart_time(String sstart_time) {
            this.sstart_time = sstart_time;
        }

        public CarSwitchBean getCar_switch() {
            return car_switch;
        }

        public void setCar_switch(CarSwitchBean car_switch) {
            this.car_switch = car_switch;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getLocint_val() {
            return locint_val;
        }

        public void setLocint_val(int locint_val) {
            this.locint_val = locint_val;
        }

        public String getPhone_book() {
            return phone_book;
        }

        public void setPhone_book(String phone_book) {
            this.phone_book = phone_book;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public int getShake_value() {
            return shake_value;
        }

        public void setShake_value(int shake_value) {
            this.shake_value = shake_value;
        }

        public int getSleep_intval() {
            return sleep_intval;
        }

        public void setSleep_intval(int sleep_intval) {
            this.sleep_intval = sleep_intval;
        }

        public PhoneAlarm getPhone_alarm() {
            return phone_alarm;
        }

        public void setPhone_alarm(PhoneAlarm phone_alarm) {
            this.phone_alarm = phone_alarm;
        }

        public static class CarSwitchBean {
            /**
             * lowpower : 1079694953
             * lbsswitch : 1079694953
             * indicatorlight : 1079694953
             * fenceswitch : 1079694953
             * soundswitch : 1079694953
             * replaystate : 1079694953
             * openswitch : 1079694953
             * offswitch : 1079694953
             * alarmswitch : 1079694953
             * closeswitch : 1079694953
             * disconnswitch : 1079694953
             * dropalarm : 1079694953
             */

            private int lowpower; // 低电报警,0关闭，1开启，-1不支持
            private int lbsswitch; // 基站开关 0关闭，1开启，-1不支持
            private int indicatorlight; // 指示灯 0关闭，1开启，-1不支持
            private int fenceswitch; // 围栏开关 0关闭，1开启，-1不支持
            private int soundswitch; // 声音提示开关 0关闭，1开启，-1不支持
            private int replaystate; // 继电器开关状态 0关闭，1开启，-1不支持
            private int openswitch; // 开机报警 启动报警 0关闭，1开启，-1不支持
            private int offswitch; // 离线报警0关闭，1开启，-1不支持
            private int alarmswitch; // 报警开关 0关闭，1开启，-1不支持
            private int closeswitch; // 关机报警0关闭，1开启，-1不支持
            private int disconnswitch; // 休眠是否断网 0关闭，1开启，-1不支持
            private int dropalarm; // 脱落报警0关闭，1开启，-1不支持
            private int shakeswicth; //震动报警开关 0关闭
            private int speedswitch; //超速报警开关 0关闭
            private int sosswitch; //SOS报警开关

            public int getSosswitch() {
                return sosswitch;
            }

            public void setSosswitch(int sosswitch) {
                this.sosswitch = sosswitch;
            }

            public int getLowpower() {
                return lowpower;
            }

            public void setLowpower(int lowpower) {
                this.lowpower = lowpower;
            }

            public int getLbsswitch() {
                return lbsswitch;
            }

            public void setLbsswitch(int lbsswitch) {
                this.lbsswitch = lbsswitch;
            }

            public int getIndicatorlight() {
                return indicatorlight;
            }

            public void setIndicatorlight(int indicatorlight) {
                this.indicatorlight = indicatorlight;
            }

            public int getFenceswitch() {
                return fenceswitch;
            }

            public void setFenceswitch(int fenceswitch) {
                this.fenceswitch = fenceswitch;
            }

            public int getSoundswitch() {
                return soundswitch;
            }

            public void setSoundswitch(int soundswitch) {
                this.soundswitch = soundswitch;
            }

            public int getReplaystate() {
                return replaystate;
            }

            public void setReplaystate(int replaystate) {
                this.replaystate = replaystate;
            }

            public int getOpenswitch() {
                return openswitch;
            }

            public void setOpenswitch(int openswitch) {
                this.openswitch = openswitch;
            }

            public int getOffswitch() {
                return offswitch;
            }

            public void setOffswitch(int offswitch) {
                this.offswitch = offswitch;
            }

            public int getAlarmswitch() {
                return alarmswitch;
            }

            public void setAlarmswitch(int alarmswitch) {
                this.alarmswitch = alarmswitch;
            }

            public int getCloseswitch() {
                return closeswitch;
            }

            public void setCloseswitch(int closeswitch) {
                this.closeswitch = closeswitch;
            }

            public int getDisconnswitch() {
                return disconnswitch;
            }

            public void setDisconnswitch(int disconnswitch) {
                this.disconnswitch = disconnswitch;
            }

            public int getDropalarm() {
                return dropalarm;
            }

            public void setDropalarm(int dropalarm) {
                this.dropalarm = dropalarm;
            }

            public int getShakeswicth() {
                return shakeswicth;
            }

            public void setShakeswicth(int shakeswicth) {
                this.shakeswicth = shakeswicth;
            }

            public int getSpeedswitch() {
                return speedswitch;
            }

            public void setSpeedswitch(int speedswitch) {
                this.speedswitch = speedswitch;
            }
        }

        public static class PhoneAlarm {
             private String acc; //acc非法启动报警
             private String out; //断电报警
             private int total; //总次数
             private String vibration; //震动报警

            public String getAcc() {
                return acc;
            }

            public void setAcc(String acc) {
                this.acc = acc;
            }

            public String getOut() {
                return out;
            }

            public void setOut(String out) {
                this.out = out;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getVibration() {
                return vibration;
            }

            public void setVibration(String vibration) {
                this.vibration = vibration;
            }
        }

    }
}
