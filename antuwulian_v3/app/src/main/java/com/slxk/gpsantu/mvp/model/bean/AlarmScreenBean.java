package com.slxk.gpsantu.mvp.model.bean;

import com.slxk.gpsantu.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 筛选bean
 */
public class AlarmScreenBean extends BaseBean {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * alarm_name : 7c1f2c71-9810-4f3d-bf97-6c338142fcb7
         * alarm_type : e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_satellite_in|e_alarm_satellite_out|e_alarm_satellite_first|e_alarm_current_low|e_alarm_current_protection|e_alarm_change_sim|e_alarm_current_protection_fly|e_alarm_vor|e_alarm_door|e_alarm_open_cover|e_alarm_low_battery|e_alarm_sleep|e_alarm_harsh_acceletation|e_alarm_harsh_braking|e_alarm_sharp_left|e_alarm_sharp_right|e_alarm_sharp_crash|e_alarm_vehicle_rolling|e_alarm_tilting|e_alarm_sharp_turn|e_alarm_lane_switching|e_alarm_vehicle_stability|e_alarm_vehicle_angle|e_alarm_acc_on|e_alarm_ldlespeed
         */

        private String alarm_name; // 名称
        private String alarm_type; // 类型
        private int weight; // 比重
        private boolean isSelect = false; // 是否选中

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getAlarm_name() {
            return alarm_name;
        }

        public void setAlarm_name(String alarm_name) {
            this.alarm_name = alarm_name;
        }

        public String getAlarm_type() {
            return alarm_type;
        }

        public void setAlarm_type(String alarm_type) {
            this.alarm_type = alarm_type;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

    }
}
