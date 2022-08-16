package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.slxk.gpsantu.mvp.model.bean.DeviceDetailResultBean;
import com.slxk.gpsantu.mvp.model.bean.LocationModeGetResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceBatchCmdPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceDetailPutBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceModeSetPutBean;
import com.slxk.gpsantu.mvp.model.putbean.LocationModeGetPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2020 17:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LocationModeYiWenContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getLocationModeSuccess(LocationModeGetResultBean locationModeGetResultBean);

        void submitLocationModeSuccess(BaseBean baseBean, DeviceModeSetPutBean deviceModeSetPutBean);

        void getDeviceDetailInfoSuccess(DeviceDetailResultBean deviceDetailResultBean);

        /**
         * 冻结设备，返回的是269877281
         * 数据已变更,请刷新 返回的是269877251，下发刷新接口
         */
        void getDeviceDetailError();

        void setDevicePriority(BaseBean baseBean);

    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取定位模式
         * @param bean
         * @return
         */
        Observable<LocationModeGetResultBean> getLocationMode(LocationModeGetPutBean bean);

        /**
         * 设置定位模式
         * @param bean
         * @return
         */
        Observable<BaseBean> submitLocationMode(DeviceModeSetPutBean bean);

        /**
         * 获取设备详情接口
         * @param bean
         * @return
         */
        Observable<DeviceDetailResultBean> getDeviceDetailInfo(DeviceDetailPutBean bean);

        /**
         * 设置定位优先级
         * @param bean
         * @return
         */
        Observable<BaseBean> setDevicePriority(DeviceBatchCmdPutBean bean);

    }
}
