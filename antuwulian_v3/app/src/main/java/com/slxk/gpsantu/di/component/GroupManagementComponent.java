package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.GroupManagementModule;
import com.slxk.gpsantu.mvp.contract.GroupManagementContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.GroupManagementActivity;
import com.slxk.gpsantu.mvp.ui.fragment.DeviceListNewFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2021 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupManagementModule.class, dependencies = AppComponent.class)
public interface GroupManagementComponent {
//    void inject(GroupManagementActivity activity);
    void inject(DeviceListNewFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupManagementComponent.Builder view(GroupManagementContract.View view);

        GroupManagementComponent.Builder appComponent(AppComponent appComponent);

        GroupManagementComponent build();
    }
}