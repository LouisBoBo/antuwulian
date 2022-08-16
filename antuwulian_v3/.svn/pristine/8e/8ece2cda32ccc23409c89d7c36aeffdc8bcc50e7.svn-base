package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.FenceCreateGoogleModule;
import com.slxk.gpsantu.mvp.contract.FenceCreateGoogleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.FenceCreateGoogleActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/09/2021 18:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FenceCreateGoogleModule.class, dependencies = AppComponent.class)
public interface FenceCreateGoogleComponent {
    void inject(FenceCreateGoogleActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        FenceCreateGoogleComponent.Builder view(FenceCreateGoogleContract.View view);
        FenceCreateGoogleComponent.Builder appComponent(AppComponent appComponent);
        FenceCreateGoogleComponent build();
    }
}