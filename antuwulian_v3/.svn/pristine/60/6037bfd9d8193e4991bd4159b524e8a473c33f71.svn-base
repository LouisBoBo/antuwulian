package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmSetSpecialModule;
import com.slxk.gpsantu.mvp.contract.AlarmSetSpecialContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmSetSpecialActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2021 09:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmSetSpecialModule.class, dependencies = AppComponent.class)
public interface AlarmSetSpecialComponent {
    void inject(AlarmSetSpecialActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmSetSpecialComponent.Builder view(AlarmSetSpecialContract.View view);
        AlarmSetSpecialComponent.Builder appComponent(AppComponent appComponent);
        AlarmSetSpecialComponent build();
    }
}