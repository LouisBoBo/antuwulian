/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.slxk.gpsantu.mvp.model.api;

import com.slxk.gpsantu.mvp.utils.ConstantValue;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {

    boolean isRelease = false;//是否发布版本
    String APP_DOMAIN = ConstantValue.getAPPServiceIp(isRelease); // API域名
    // 返回成功码
    int SUCCESS = 0; // 成功
    int OUT_OF_DATE = 268697631; // 登录已过期
    int SUCCESS_200 = 200; // 成功，特殊情况
    int Device_Freeze = 269877281; // 设备已冻结
    int Data_Change = 269877251; // 数据已变更
    int Operational_Restrictions = 269877293; // 操作限制
    int Mobile_Bind_Used = 269877295; // 手机号已绑定其他账号或设备
    int Mobile_Code_Error = 269877302; // 验证码错误

    // 登录请求头
    String HEADER_RELEASE = "Content-Type:application/x-www-form-urlencoded";

    // 发布任务请求头
    String HEADER_RELEASE_TYPE = "Content-Type:application/json";

    // 获取手机IP地址信息
    String App_GetIPUrl = "http://pv.sohu.com/cityjson?ie=utf-8";

    // 帮助文档
    String Help_Center = "file:///android_asset/newhelpcenter.html";
    // 帮助文档 英文
    String Help_Center_EN = "file:///android_asset/newhelpcenter_en.html";
    // 帮助文档 越南
    String Help_Center_VI = "file:///android_asset/newhelpcenter_vi.html";

    // 隐私政策协议
    String Privacy_Policy = "http://www.91wlcx.com/privacyPolicy_antu.html";
    // 隐私政策协议英文
    String Privacy_Policy_EN = "http://www.91wlcx.com/privacyPolicy_antu_en.html";
    // 隐私政策协议越南
    String Privacy_Policy_VI = "http://www.91wlcx.com/privacyPolicy_antu_vi.html";

    //钱包管理协议
    String Wallet_management = isRelease ? "http://www.669gps.com/mobile/vas/pay/package" : "http://webcs.cciot.cc/mobile/vas/pay/package";
    //售后工具协议
    String Aftermarket_tools = isRelease ? "http://www.669gps.com/mobile" : "http://webcs.cciot.cc/mobile";
    //用户管理协议
    String User_Management = isRelease ? "http://www.669gps.com/mobile/userManage" : "http://webcs.cciot.cc/mobile/userManage";
    //H5host
    String API_H5_Host = isRelease ? "http://www.669gps.com" : "http://webcs.cciot.cc";

    // mob短信
    String Mob_App_Key = "336c5376a3412";
    // mob短信模板code
    String Mob_Module_Code = "488432";
    // 用户登录类型
    int App_Type = 10;
    //App 更新类型
    String App_Update_Type = "antu";
    //App 充值来源
    String App_Recharge_Name = "atwl";
    // sim卡充值
    String Pay_Sim_Recharge = isRelease ? "http://h5cs.669gps.com/mobile/sim/pay" : "http://webcs.cciot.cc/mobile/sim/pay";
    //增值服务
    String Pay_Increment_Recharge = isRelease ? "http://h5cs.669gps.com/mobile/vas/default/package" : "http://webcs.cciot.cc/mobile/vas/default/package";
    //支付完成返回
    String Pay_Sim_Success_Return = isRelease ? "http://h5cs.669gps.com/mobile/sim/pay/return" : "http://webcs.cciot.cc/mobile/sim/pay/return";
    //支付完成返回
    String Pay_Sim_Referer = isRelease ? "http://h5cs.669gps.com" : "http://webcs.cciot.cc";
    // 录音增值服务充值
    String Pay_Record_Recharge = "http://zbcar.8325.com/pay_zb_v3/app_pay_record_zb.php";

    String HOST_MAP = "http://api.map.baidu.com"; //百度地图

}
