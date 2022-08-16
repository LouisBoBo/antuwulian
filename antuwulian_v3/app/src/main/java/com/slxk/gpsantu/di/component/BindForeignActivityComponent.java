package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.BindForeignActivityModule;
import com.slxk.gpsantu.mvp.contract.BindForeignActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.BindForeignActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/25/2021 16:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BindForeignActivityModule.class, dependencies = AppComponent.class)
public interface BindForeignActivityComponent {
    void inject(BindForeignActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindForeignActivityComponent.Builder view(BindForeignActivityContract.View view);

        BindForeignActivityComponent.Builder appComponent(AppComponent appComponent);

        BindForeignActivityComponent build();
    }
}