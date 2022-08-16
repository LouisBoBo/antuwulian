package com.slxk.gpsantu.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.slxk.gpsantu.di.module.OilElectricityControlModule;
import com.slxk.gpsantu.mvp.contract.OilElectricityControlContract;

import com.jess.arms.di.scope.ActivityScope;
import com.slxk.gpsantu.mvp.ui.activity.OilElectricityControlActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 14:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OilElectricityControlModule.class, dependencies = AppComponent.class)
public interface OilElectricityControlComponent {
    void inject(OilElectricityControlActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        OilElectricityControlComponent.Builder view(OilElectricityControlContract.View view);
        OilElectricityControlComponent.Builder appComponent(AppComponent appComponent);
        OilElectricityControlComponent build();
    }
}