package com.slxk.gpsantu.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.di.module.BindMobileNextModule;
import com.slxk.gpsantu.mvp.contract.BindMobileNextContract;
import com.slxk.gpsantu.mvp.ui.activity.BindMobileNextActivity;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = BindMobileNextModule.class, dependencies = AppComponent.class)
public interface BindMobileNextComponent {
    void inject(BindMobileNextActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BindMobileNextComponent.Builder view(BindMobileNextContract.View view);

        BindMobileNextComponent.Builder appComponent(AppComponent appComponent);

        BindMobileNextComponent build();
    }
}