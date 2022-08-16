package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.PermissionTransferModule;
import com.slxk.gpsantu.mvp.contract.PermissionTransferContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.PermissionTransferActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 14:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PermissionTransferModule.class, dependencies = AppComponent.class)
public interface PermissionTransferComponent {
    void inject(PermissionTransferActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        PermissionTransferComponent.Builder view(PermissionTransferContract.View view);
        PermissionTransferComponent.Builder appComponent(AppComponent appComponent);
        PermissionTransferComponent build();
    }
}