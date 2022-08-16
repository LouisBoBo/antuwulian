package com.slxk.gpsantu.mvp.model.putbean;

import java.util.List;

/**
 * 批量删除报警消息
 */
public class AlarmDeleteBatchPutBean {

    /**
     * module : alarm
     * func : BatchDel
     * params : {"items":[{"fencename":"7d496af1-4515-4c18-a96a-176c966e8195","simei":"203a1b72-324a-484f-83d7-8f052648df3c","time":106274595,"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos"},{"fencename":"f9757738-5129-49d2-8455-4bc0459b74dc","simei":"657713b9-b885-4893-baf8-eb506c46e18d","time":106274595,"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos"}],"sfamily":"a5f017c6-5cd4-42a9-9490-c95157438df7","simei":["5b61cbad-1b26-491a-82d4-88be6101770c"],"type":["e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos","e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos"]}
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
         * items : [{"fencename":"7d496af1-4515-4c18-a96a-176c966e8195","simei":"203a1b72-324a-484f-83d7-8f052648df3c","time":106274595,"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos"},{"fencename":"f9757738-5129-49d2-8455-4bc0459b74dc","simei":"657713b9-b885-4893-baf8-eb506c46e18d","time":106274595,"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos"}]
         * sfamily : a5f017c6-5cd4-42a9-9490-c95157438df7
         * simei : ["5b61cbad-1b26-491a-82d4-88be6101770c"]
         * type : ["e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos","e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos"]
         */

        private String sfamily; // 需要删除的全部的组织告警，参数items，sfamily,simei三选一填写，设备号登入可以不用
        private List<ItemsBean> items; // 需要删除的告警列表,最多1000，参数items，sfamily,simei三选一填写，设备号登入可以不用
        private List<String> simei; // 需要删除指定设备的全部告警，注意，这些设备号必须是同一个组织下.参数items，sfamily,simei三选一填写，设备号登入可以不用
        private List<String> type; // 如果填写了字段sfamily或simei，那么可以筛选其中几种类型批量删除

        public String getSfamily() {
            return sfamily;
        }

        public void setSfamily(String sfamily) {
            this.sfamily = sfamily;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public static class ItemsBean {
            /**
             * fencename : 7d496af1-4515-4c18-a96a-176c966e8195
             * simei : 203a1b72-324a-484f-83d7-8f052648df3c
             * time : 106274595
             * type : e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos
             */

            private String fencename; // 触发报警的围栏名称
            private String simei; // 设备id,设备号登录可以不用传
            private long time; // 告警时间
            private String type; // 报警类型

            public String getFencename() {
                return fencename;
            }

            public void setFencename(String fencename) {
                this.fencename = fencename;
            }

            public String getSimei() {
                return simei;
            }

            public void setSimei(String simei) {
                this.simei = simei;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
