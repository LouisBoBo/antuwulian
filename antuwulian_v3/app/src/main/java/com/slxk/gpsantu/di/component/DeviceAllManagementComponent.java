package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.DeviceAllManagementModule;
import com.slxk.gpsantu.mvp.contract.DeviceAllManagementContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.DeviceAllManagementActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2021 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DeviceAllManagementModule.class, dependencies = AppComponent.class)
public interface DeviceAllManagementComponent {
    void inject(DeviceAllManagementActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        DeviceAllManagementComponent.Builder view(DeviceAllManagementContract.View view);
        DeviceAllManagementComponent.Builder appComponent(AppComponent appComponent);
        DeviceAllManagementComponent build();
    }
}