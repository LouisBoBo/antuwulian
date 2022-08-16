package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.RegisterForeignModule;
import com.slxk.gpsantu.mvp.contract.RegisterForeignContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.RegisterForeignActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/11/2021 09:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RegisterForeignModule.class, dependencies = AppComponent.class)
public interface RegisterForeignComponent {
    void inject(RegisterForeignActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        RegisterForeignComponent.Builder view(RegisterForeignContract.View view);
        RegisterForeignComponent.Builder appComponent(AppComponent appComponent);
        RegisterForeignComponent build();
    }
}