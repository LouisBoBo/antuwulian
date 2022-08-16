package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.slxk.gpsantu.mvp.model.bean.CheckAppUpdateBean;
import com.slxk.gpsantu.mvp.model.bean.LogoutAccountResultBean;
import com.slxk.gpsantu.mvp.model.bean.UnbindPhoneResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.CheckAppUpdatePutBean;
import com.slxk.gpsantu.mvp.model.putbean.LogoutAccountPutBean;
import com.slxk.gpsantu.mvp.model.putbean.SignOutPutBean;
import com.slxk.gpsantu.mvp.model.putbean.UnbindPhonePutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2022 10:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface UserSettingContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getAppUpdateSuccess(CheckAppUpdateBean checkAppUpdateBean);

        void submitLogoutAccountSuccess(LogoutAccountResultBean logoutAccountResultBean);

        /**
         * 关闭加载框
         */
        void onDismissProgress();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {


        /**
         * 获取版本更新
         * @param bean
         * @return
         */
        Observable<CheckAppUpdateBean> getAppUpdate(CheckAppUpdatePutBean bean);

        /**
         * 注销账号
         * @param bean
         * @return
         */
        Observable<LogoutAccountResultBean> submitLogoutAccount(LogoutAccountPutBean bean);



    }
}

