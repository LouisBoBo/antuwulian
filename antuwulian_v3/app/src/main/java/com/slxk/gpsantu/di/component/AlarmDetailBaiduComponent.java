package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmDetailBaiduModule;
import com.slxk.gpsantu.mvp.contract.AlarmDetailBaiduContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmDetailBaiduActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/09/2021 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmDetailBaiduModule.class, dependencies = AppComponent.class)
public interface AlarmDetailBaiduComponent {
    void inject(AlarmDetailBaiduActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmDetailBaiduComponent.Builder view(AlarmDetailBaiduContract.View view);
        AlarmDetailBaiduComponent.Builder appComponent(AppComponent appComponent);
        AlarmDetailBaiduComponent build();
    }
}