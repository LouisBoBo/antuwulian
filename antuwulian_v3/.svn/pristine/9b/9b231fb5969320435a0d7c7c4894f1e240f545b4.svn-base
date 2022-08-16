package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.UserSwitchModule;
import com.slxk.gpsantu.mvp.contract.UserSwitchContract;


import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.UserSwitchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2021 17:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserSwitchModule.class, dependencies = AppComponent.class)
public interface UserSwitchComponent {
    void inject(UserSwitchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(UserSwitchContract.View view);

        Builder appComponent(AppComponent appComponent);

        UserSwitchComponent build();
    }
}