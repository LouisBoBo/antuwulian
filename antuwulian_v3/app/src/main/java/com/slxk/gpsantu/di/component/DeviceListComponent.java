package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.DeviceListModule;
import com.slxk.gpsantu.mvp.contract.DeviceListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.DeviceListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2021 14:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DeviceListModule.class, dependencies = AppComponent.class)
public interface DeviceListComponent {
    void inject(DeviceListActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        DeviceListComponent.Builder view(DeviceListContract.View view);
        DeviceListComponent.Builder appComponent(AppComponent appComponent);
        DeviceListComponent build();
    }
}