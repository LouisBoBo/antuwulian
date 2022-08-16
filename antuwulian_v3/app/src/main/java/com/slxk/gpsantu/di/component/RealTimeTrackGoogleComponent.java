package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.RealTimeTrackGoogleModule;
import com.slxk.gpsantu.mvp.contract.RealTimeTrackGoogleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.RealTimeTrackGoogleActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/09/2021 17:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RealTimeTrackGoogleModule.class, dependencies = AppComponent.class)
public interface RealTimeTrackGoogleComponent {
    void inject(RealTimeTrackGoogleActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RealTimeTrackGoogleComponent.Builder view(RealTimeTrackGoogleContract.View view);
        RealTimeTrackGoogleComponent.Builder appComponent(AppComponent appComponent);
        RealTimeTrackGoogleComponent build();
    }
}