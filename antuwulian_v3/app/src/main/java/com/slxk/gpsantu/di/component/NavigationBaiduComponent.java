package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.NavigationBaiduModule;
import com.slxk.gpsantu.mvp.contract.NavigationBaiduContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.NavigationBaiduActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NavigationBaiduModule.class, dependencies = AppComponent.class)
public interface NavigationBaiduComponent {
    void inject(NavigationBaiduActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        NavigationBaiduComponent.Builder view(NavigationBaiduContract.View view);
        NavigationBaiduComponent.Builder appComponent(AppComponent appComponent);
        NavigationBaiduComponent build();
    }
}