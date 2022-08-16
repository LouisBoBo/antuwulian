package com.slxk.gpsantu.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.di.module.LocationModeYiWenModule;
import com.slxk.gpsantu.mvp.contract.LocationModeYiWenContract;
import com.slxk.gpsantu.mvp.ui.activity.LocationModeYiWenActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 16:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LocationModeYiWenModule.class, dependencies = AppComponent.class)
public interface LocationModeYiWenComponent {
    void inject(LocationModeYiWenActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LocationModeYiWenComponent.Builder view(LocationModeYiWenContract.View view);
        LocationModeYiWenComponent.Builder appComponent(AppComponent appComponent);
        LocationModeYiWenComponent build();
    }
}