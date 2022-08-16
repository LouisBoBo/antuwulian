package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.SettingMoreModule;
import com.slxk.gpsantu.mvp.contract.SettingMoreContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.SettingMoreActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/08/2021 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SettingMoreModule.class, dependencies = AppComponent.class)
public interface SettingMoreComponent {
    void inject(SettingMoreActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        SettingMoreComponent.Builder view(SettingMoreContract.View view);
        SettingMoreComponent.Builder appComponent(AppComponent appComponent);
        SettingMoreComponent build();
    }
}