package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.LocationBaiduModule;
import com.slxk.gpsantu.mvp.contract.LocationBaiduContract;

import com.jess.arms.di.scope.FragmentScope;
import com.slxk.gpsantu.mvp.ui.fragment.LocationBaiduFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = LocationBaiduModule.class, dependencies = AppComponent.class)
public interface LocationBaiduComponent {
    void inject(LocationBaiduFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LocationBaiduComponent.Builder view(LocationBaiduContract.View view);
        LocationBaiduComponent.Builder appComponent(AppComponent appComponent);
        LocationBaiduComponent build();
    }
}