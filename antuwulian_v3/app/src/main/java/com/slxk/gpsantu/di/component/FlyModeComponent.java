package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.FlyModeModule;
import com.slxk.gpsantu.mvp.contract.FlyModeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.FlyModeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FlyModeModule.class, dependencies = AppComponent.class)
public interface FlyModeComponent {
    void inject(FlyModeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FlyModeComponent.Builder view(FlyModeContract.View view);

        FlyModeComponent.Builder appComponent(AppComponent appComponent);

        FlyModeComponent build();
    }
}