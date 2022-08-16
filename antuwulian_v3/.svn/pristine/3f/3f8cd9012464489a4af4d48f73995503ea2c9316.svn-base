package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.slxk.gpsantu.mvp.model.bean.DeviceConfigResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceDetailResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceListForManagerResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceListResultBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.bean.MergeAccountResultBean;
import com.slxk.gpsantu.mvp.model.bean.PhoneCodeResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceConfigPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceListForManagerPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceListPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.MergeAccountPutBean;
import com.slxk.gpsantu.mvp.model.putbean.OnKeyFunctionPutBean;

import io.reactivex.Observable;
import com.slxk.gpsantu.mvp.model.putbean.PhoneCodePutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LocationBaiduContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getDeviceListSuccess(DeviceListResultBean deviceListResultBean, boolean isInitiativeRefresh, boolean clearMap);

        void getDeviceListError(int errcode);

        /**
         * 冻结设备，返回的是269877281
         * 数据已变更,请刷新 返回的是269877251，下发刷新接口
         */
        void getDeviceDetailError();

        void getDeviceDetailInfoSuccess(DeviceDetailResultBean deviceDetailResultBean);

        void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh);

        void finishRefresh();

        /**
         *
         * @param type 请求类型，0：分组列表，1：设备列表，2：搜索设备列表
         */
        void endLoadMore(int type);

        void setNoMore(int type);

        void endLoadFail(int type);

        /**
         * 获取分组下的设备列表
         * @param deviceListForManagerResultBean
         * @param isRefresh 是否刷新数据
         */
        void getDeviceListForGroupSuccess(DeviceListForManagerResultBean deviceListForManagerResultBean, boolean isRefresh);

        void submitOneKeyFunctionSuccess(BaseBean baseBean);

        /**
         * 关闭加载框
         */
        void onDismissProgress();

        void getPhoneCodeSuccess(PhoneCodeResultBean phoneCodeResultBean);

        void submitMergeAccountSuccess(MergeAccountResultBean mergeAccountResultBean);

        void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean);

        /**
         * 获取设备是否有有效定位数据
         */
        void getDeviceHasLocationSuccess(DeviceDetailResultBean deviceDetailResultBean, long imei, String simei);

        void getFindDeviceDataSuccess(FindDeviceResultBean bean);

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{

        /**
         * 获取当前账号下设备列表，设备号登录同求
         * @param bean
         * @return
         */
        Observable<DeviceListResultBean> getDeviceList(DeviceListPutBean bean);

        /**
         * 获取设备详情接口
         * @param bean
         * @return
         */
        Observable<DeviceDetailResultBean> getDeviceDetailInfo(DeviceDetailPutBean bean);

        /**
         * 获取车组织列表和车组列表
         * @param bean
         * @return
         */
        Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean);

        /**
         * 获取分组下的设备列表
         * @param bean
         * @return
         */
        Observable<DeviceListForManagerResultBean> getDeviceListForGroup(DeviceListForManagerPutBean bean);

        /**
         * 一键功能设置
         * @param bean
         * @return
         */
        Observable<BaseBean> submitOneKeyFunction(OnKeyFunctionPutBean bean);


        /**
         * 获取手机验证码
         * @param bean
         * @return
         */
        Observable<PhoneCodeResultBean> getPhoneCode(PhoneCodePutBean bean);

        /**
         * 合并账号
         * @param bean
         * @return
         */
        Observable<MergeAccountResultBean> submitMergeAccount(MergeAccountPutBean bean);

        /**
         * 获取设备的配置信息，支持的功能等
         * @param bean
         * @return
         */
        Observable<DeviceConfigResultBean> getDeviceConfig(DeviceConfigPutBean bean);

        /**
         * 模糊，精准搜索设备
         * @param bean
         * @return
         */
        Observable<FindDeviceResultBean> getFindDeviceData(FindDevicePutBean bean);

    }
}
