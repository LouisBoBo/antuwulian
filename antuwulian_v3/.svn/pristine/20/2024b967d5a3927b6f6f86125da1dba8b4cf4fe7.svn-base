package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.UserInfoModifyModule;
import com.slxk.gpsantu.mvp.contract.UserInfoModifyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.UserInfoModifyActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 11:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserInfoModifyModule.class, dependencies = AppComponent.class)
public interface UserInfoModifyComponent {
    void inject(UserInfoModifyActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        UserInfoModifyComponent.Builder view(UserInfoModifyContract.View view);
        UserInfoModifyComponent.Builder appComponent(AppComponent appComponent);
        UserInfoModifyComponent build();
    }
}