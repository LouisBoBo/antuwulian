package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.slxk.gpsantu.mvp.model.bean.DeviceBaseResultBean;
import com.slxk.gpsantu.mvp.model.bean.DeviceGroupResultBean;
import com.slxk.gpsantu.mvp.model.bean.GroupAddResultBean;
import com.slxk.gpsantu.mvp.model.bean.TaskProgressResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.DeviceGroupPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FreezeEquipmentPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupAddPutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupDeletePutBean;
import com.slxk.gpsantu.mvp.model.putbean.GroupEditPutBean;
import com.slxk.gpsantu.mvp.model.putbean.RemoveDevicePutBean;
import com.slxk.gpsantu.mvp.model.putbean.TaskProgressPubBean;
import com.slxk.gpsantu.mvp.model.putbean.TransferDevicePutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2021 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface DeviceAllManagementContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh);

        void finishRefresh();

        void endLoadMore();

        void setNoMore();

        void endLoadFail();

        void submitGroupAddSuccess(GroupAddResultBean groupAddResultBean);

        void submitGroupEditSuccess(BaseBean baseBean, String name, String gid);

        void submitTransferDeviceSuccess(DeviceBaseResultBean transferDeviceResultBean);

        void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void submitDeleteGroupSuccess(BaseBean baseBean);

        void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean);

        void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean);

        /**
         * 刷新数据
         */
        void onRefreshData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取车组织列表和车组列表
         * @param bean
         * @return
         */
        Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean);

        /**
         * 添加分组
         * @param bean
         * @return
         */
        Observable<GroupAddResultBean> submitGroupAdd(GroupAddPutBean bean);

        /**
         * 编辑分组名称
         * @param bean
         * @return
         */
        Observable<BaseBean> submitGroupEdit(GroupEditPutBean bean);

        /**
         * 转移设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitTransferDevice(TransferDevicePutBean bean);

        /**
         * 删除设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitDeleteDevice(RemoveDevicePutBean bean);

        /**
         * 删除分组
         * @param bean
         * @return
         */
        Observable<BaseBean> submitDeleteGroup(GroupDeletePutBean bean);

        /**
         * 冻结设备
         * @param bean
         * @return
         */
        Observable<DeviceBaseResultBean> submitFreezeEquipment(FreezeEquipmentPutBean bean);

        /**
         * 获取任务进度
         * @param bean
         * @return
         */
        Observable<TaskProgressResultBean> getTaskProgress(TaskProgressPubBean bean);

    }
}
