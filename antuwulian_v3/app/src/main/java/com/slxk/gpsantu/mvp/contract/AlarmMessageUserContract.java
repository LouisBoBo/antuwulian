package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.slxk.gpsantu.mvp.model.bean.AlarmRecordResultBean;
import com.slxk.gpsantu.mvp.model.bean.FindDeviceResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeleteBatchPutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.AlarmRecordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FindDevicePutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 09:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AlarmMessageUserContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getAlarmRecordSuccess(AlarmRecordResultBean alarmRecordResultBean, boolean isRefresh);

        void finishRefresh();

        /**
         *
         * @param type 请求类型，0：列表数据，1：搜索设备
         */
        void endLoadMore(int type);

        void setNoMore(int type);

        void endLoadFail(int type);

        void submitAlarmDeleteSuccess(BaseBean baseBean);

        void submitAlarmDeleteBatchSuccess(BaseBean baseBean);

        /**
         * 关闭加载框
         */
        void onDismissProgress();

        void getFindDeviceDataSuccess(FindDeviceResultBean bean);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取报警消息列表
         * @param bean
         * @return
         */
        Observable<AlarmRecordResultBean> getAlarmRecord(AlarmRecordPutBean bean);

        /**
         * 删除报警消息
         * @param bean
         * @return
         */
        Observable<BaseBean> submitAlarmDelete(AlarmDeletePutBean bean);

        /**
         * 批量删除报警消息
         * @param bean
         * @return
         */
        Observable<BaseBean> submitAlarmDeleteBatch(AlarmDeleteBatchPutBean bean);

        /**
         * 模糊，精准搜索设备
         * @param bean
         * @return
         */
        Observable<FindDeviceResultBean> getFindDeviceData(FindDevicePutBean bean);

    }
}
