package com.slxk.gpsantu.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.di.module.ForgetPasswordNextModule;
import com.slxk.gpsantu.mvp.contract.ForgetPasswordNextContract;
import com.slxk.gpsantu.mvp.ui.activity.ForgetPasswordNextActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ForgetPasswordNextModule.class, dependencies = AppComponent.class)
public interface ForgetPasswordNextComponent {
    void inject(ForgetPasswordNextActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ForgetPasswordNextComponent.Builder view(ForgetPasswordNextContract.View view);
        ForgetPasswordNextComponent.Builder appComponent(AppComponent appComponent);
        ForgetPasswordNextComponent build();
    }
}