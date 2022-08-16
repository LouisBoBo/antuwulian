package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.UserSettingModule;
import com.slxk.gpsantu.mvp.contract.UserSettingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.UserSettingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2022 10:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserSettingModule.class, dependencies = AppComponent.class)
public interface UserSettingComponent {
    void inject(UserSettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserSettingComponent.Builder view(UserSettingContract.View view);

        UserSettingComponent.Builder appComponent(AppComponent appComponent);

        UserSettingComponent build();
    }
}