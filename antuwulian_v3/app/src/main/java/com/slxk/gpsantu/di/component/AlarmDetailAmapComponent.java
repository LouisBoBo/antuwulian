package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmDetailAmapModule;
import com.slxk.gpsantu.mvp.contract.AlarmDetailAmapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmDetailAmapActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/09/2021 09:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmDetailAmapModule.class, dependencies = AppComponent.class)
public interface AlarmDetailAmapComponent {
    void inject(AlarmDetailAmapActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmDetailAmapComponent.Builder view(AlarmDetailAmapContract.View view);
        AlarmDetailAmapComponent.Builder appComponent(AppComponent appComponent);
        AlarmDetailAmapComponent build();
    }
}