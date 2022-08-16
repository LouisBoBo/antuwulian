package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.BindMobileModule;
import com.slxk.gpsantu.mvp.contract.BindMobileContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.BindMobileActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2021 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BindMobileModule.class, dependencies = AppComponent.class)
public interface BindMobileComponent {
    void inject(BindMobileActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindMobileComponent.Builder view(BindMobileContract.View view);

        BindMobileComponent.Builder appComponent(AppComponent appComponent);

        BindMobileComponent build();
    }
}