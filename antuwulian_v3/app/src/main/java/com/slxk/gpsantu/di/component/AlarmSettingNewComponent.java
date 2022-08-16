package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AlarmSettingNewModule;
import com.slxk.gpsantu.mvp.contract.AlarmSettingNewContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AlarmSettingNewActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2022 09:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AlarmSettingNewModule.class, dependencies = AppComponent.class)
public interface AlarmSettingNewComponent {
    void inject(AlarmSettingNewActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AlarmSettingNewComponent.Builder view(AlarmSettingNewContract.View view);
        AlarmSettingNewComponent.Builder appComponent(AppComponent appComponent);
        AlarmSettingNewComponent build();
    }
}