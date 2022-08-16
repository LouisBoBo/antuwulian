package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AddDeviceNewModule;
import com.slxk.gpsantu.mvp.contract.AddDeviceNewContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AddDeviceNewActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2022 15:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddDeviceNewModule.class, dependencies = AppComponent.class)
public interface AddDeviceNewComponent {
    void inject(AddDeviceNewActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddDeviceNewComponent.Builder view(AddDeviceNewContract.View view);

        AddDeviceNewComponent.Builder appComponent(AppComponent appComponent);

        AddDeviceNewComponent build();
    }
}