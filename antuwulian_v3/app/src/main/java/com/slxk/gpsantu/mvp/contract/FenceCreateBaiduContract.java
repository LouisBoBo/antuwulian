package com.slxk.gpsantu.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.slxk.gpsantu.mvp.model.bean.FenceAddResultBean;
import com.slxk.gpsantu.mvp.model.bean.FenceResultBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;
import com.slxk.gpsantu.mvp.model.putbean.FenceAddPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FenceListPutBean;
import com.slxk.gpsantu.mvp.model.putbean.FenceModifyPutBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface FenceCreateBaiduContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getFenceListSuccess(FenceResultBean fenceResultBean);

        void submitFenceAddSuccess(FenceAddResultBean fenceAddResultBean);

        void submitFenceModifySuccess(BaseBean baseBean);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取围栏列表
         * @param bean
         * @return
         */
        Observable<FenceResultBean> getFenceList(FenceListPutBean bean);

        /**
         * 添加围栏
         * @param bean
         * @return
         */
        Observable<FenceAddResultBean> submitFenceAdd(FenceAddPutBean bean);

        /**
         * 修改围栏
         * @param bean
         * @return
         */
        Observable<BaseBean> submitFenceModify(FenceModifyPutBean bean);

    }
}
