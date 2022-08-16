package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmDetailGoogleModule;
import com.slxk.gpsantu.mvp.contract.AlarmDetailGoogleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmDetailGoogleActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/09/2021 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmDetailGoogleModule.class, dependencies = AppComponent.class)
public interface AlarmDetailGoogleComponent {
    void inject(AlarmDetailGoogleActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmDetailGoogleComponent.Builder view(AlarmDetailGoogleContract.View view);
        AlarmDetailGoogleComponent.Builder appComponent(AppComponent appComponent);
        AlarmDetailGoogleComponent build();
    }
}