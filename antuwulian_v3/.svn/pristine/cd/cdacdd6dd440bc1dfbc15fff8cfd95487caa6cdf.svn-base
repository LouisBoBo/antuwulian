package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.LaunchModule;
import com.slxk.gpsantu.mvp.contract.LaunchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.LaunchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/24/2020 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LaunchModule.class, dependencies = AppComponent.class)
public interface LaunchComponent {
    void inject(LaunchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LaunchComponent.Builder view(LaunchContract.View view);

        LaunchComponent.Builder appComponent(AppComponent appComponent);

        LaunchComponent build();
    }
}