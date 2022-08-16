package com.slxk.gpsantu.mvp.model.api.service;

import com.slxk.gpsantu.mvp.model.api.Api;
import com.slxk.gpsantu.mvp.model.bean.AlarmRecordResultBean;
import com.slxk.gpsantu.mvp.model.bean.AlarmScreenBean;
import com.slxk.gpsantu.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 报警消息相关服务类
 */
public interface AlarmService {

    /**
     * 获取报警消息列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<AlarmRecordResultBean> getAlarmRecord(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除报警消息
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitAlarmDelete(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 批量删除报警消息
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitAlarmDeleteBatch(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取报警筛选类型
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<AlarmScreenBean> getAlarmScreenType(@Query("sid") String sid, @Body RequestBody requestBody);
}
