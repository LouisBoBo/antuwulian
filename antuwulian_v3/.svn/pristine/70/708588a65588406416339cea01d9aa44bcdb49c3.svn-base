package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.PenetrateModule;
import com.slxk.gpsantu.mvp.contract.PenetrateContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.PenetrateActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/08/2021 16:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PenetrateModule.class, dependencies = AppComponent.class)
public interface PenetrateComponent {
    void inject(PenetrateActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        PenetrateComponent.Builder view(PenetrateContract.View view);
        PenetrateComponent.Builder appComponent(AppComponent appComponent);
        PenetrateComponent build();
    }
}