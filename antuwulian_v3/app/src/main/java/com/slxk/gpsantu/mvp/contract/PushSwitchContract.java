package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.slxk.gpsantu.mvp.model.bean.UidInfoResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.ModifyPasswordPutBean;
import com.slxk.gpsantu.mvp.model.putbean.UidInfoPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/03/2021 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PushSwitchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getUidInfoSuccess(UidInfoResultBean uidInfoResultBean);

        void submitPushSwitchSuccess(boolean pushSwitch, boolean isSetPushSwitch);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 修改密码
         * @param bean
         * @return
         */
        Observable<BaseBean> submitPushSwitch(ModifyPasswordPutBean bean);

        /**
         * 获取推送开关等信息
         * @param bean
         * @return
         */
        Observable<UidInfoResultBean> getUidInfo(UidInfoPutBean bean);

    }
}