package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.TrackGoogleModule;
import com.slxk.gpsantu.mvp.contract.TrackGoogleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.TrackGoogleActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/11/2021 10:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TrackGoogleModule.class, dependencies = AppComponent.class)
public interface TrackGoogleComponent {
    void inject(TrackGoogleActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        TrackGoogleComponent.Builder view(TrackGoogleContract.View view);
        TrackGoogleComponent.Builder appComponent(AppComponent appComponent);
        TrackGoogleComponent build();
    }
}