package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.AccountListModule;
import com.slxk.gpsantu.mvp.contract.AccountListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.AccountListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 11:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AccountListModule.class, dependencies = AppComponent.class)
public interface AccountListComponent {
    void inject(AccountListActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        AccountListComponent.Builder view(AccountListContract.View view);
        AccountListComponent.Builder appComponent(AppComponent appComponent);
        AccountListComponent build();
    }
}