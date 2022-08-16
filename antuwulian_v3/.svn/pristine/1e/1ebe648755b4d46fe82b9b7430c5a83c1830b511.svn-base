package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmSettingModule;
import com.slxk.gpsantu.mvp.contract.AlarmSettingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmSettingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 11:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmSettingModule.class, dependencies = AppComponent.class)
public interface AlarmSettingComponent {
    void inject(AlarmSettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmSettingComponent.Builder view(AlarmSettingContract.View view);

        AlarmSettingComponent.Builder appComponent(AppComponent appComponent);

        AlarmSettingComponent build();
    }
}