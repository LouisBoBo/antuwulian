package com.slxk.gpsantu.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.di.module.CreatePasswordModule;
import com.slxk.gpsantu.mvp.contract.CreatePasswordContract;
import com.slxk.gpsantu.mvp.ui.activity.CreatePasswordActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 10:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CreatePasswordModule.class, dependencies = AppComponent.class)
public interface CreatePasswordComponent {
    void inject(CreatePasswordActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        CreatePasswordComponent.Builder view(CreatePasswordContract.View view);
        CreatePasswordComponent.Builder appComponent(AppComponent appComponent);
        CreatePasswordComponent build();
    }
}