package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmClockModule;
import com.slxk.gpsantu.mvp.contract.AlarmClockContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmClockActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/06/2022 17:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmClockModule.class, dependencies = AppComponent.class)
public interface AlarmClockComponent {
    void inject(AlarmClockActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmClockComponent.Builder view(AlarmClockContract.View view);
        AlarmClockComponent.Builder appComponent(AppComponent appComponent);
        AlarmClockComponent build();
    }
}