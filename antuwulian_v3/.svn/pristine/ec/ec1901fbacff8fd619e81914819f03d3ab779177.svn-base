package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.MileageStatisticsModule;
import com.slxk.gpsantu.mvp.contract.MileageStatisticsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.MileageStatisticsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 11:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MileageStatisticsModule.class, dependencies = AppComponent.class)
public interface MileageStatisticsComponent {
    void inject(MileageStatisticsActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        MileageStatisticsComponent.Builder view(MileageStatisticsContract.View view);
        MileageStatisticsComponent.Builder appComponent(AppComponent appComponent);
        MileageStatisticsComponent build();
    }
}