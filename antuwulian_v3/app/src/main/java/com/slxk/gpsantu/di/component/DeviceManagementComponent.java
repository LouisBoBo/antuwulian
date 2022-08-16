package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.DeviceManagementModule;
import com.slxk.gpsantu.mvp.contract.DeviceManagementContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.DeviceManagementActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DeviceManagementModule.class, dependencies = AppComponent.class)
public interface DeviceManagementComponent {
    void inject(DeviceManagementActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DeviceManagementComponent.Builder view(DeviceManagementContract.View view);

        DeviceManagementComponent.Builder appComponent(AppComponent appComponent);

        DeviceManagementComponent build();
    }
}