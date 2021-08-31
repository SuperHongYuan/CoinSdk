package com.ych.ytdevicesdk.api;

import com.ych.ytdevicesdk.entity.DeviceUpdateEntity;
import com.ych.ytdevicesdk.entity.HeartbeatEntity;
import com.ych.ytdevicesdk.entity.RegisterEntity;
import com.ych.ytdevicesdk.entity.RemoteDeviceConfigEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName IDeviceConnectApi
 * @date 2021/8/24 18:33
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/24 18:33
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * @hide
 */
public interface IDeviceConnectApi {

    @GET("/yzy/api/v1/DeviceRegister/ConnectTest")
    Observable<BaseResult<String>> connectTest();

    /**
     * 推送设备异常到后台
     * @param header 请求头
     * @param map   请求体
     * @return
     */
    @POST("/YzyIot/api/v1/Device/StartFix")
    Observable<BaseResult<String>> pushDeviceError(@HeaderMap Map<String, Object> header, @Body Map<String, Object> map);


    /**
     * 设备心跳
     * @param header
     * @param map
     * @return
     */
    @POST("/YzyIot/api/v1/Device/Heartbeat")
    Observable<BaseResult<HeartbeatEntity>> deviceHeartbeat(@HeaderMap Map<String, Object> header, @Body Map<String, Object> map);


    /**
     * 注册设备
     * @param header
     * @param map
     * @return
     */
    @POST("/YzyIot/api/v1/Device/RegistWithProduct")
    Observable<BaseResult<RegisterEntity>> registerProduct(@HeaderMap Map<String, Object> header, @Body Map<String, Object> map);


    /**
     * 检查更新
     * @param header
     * @param map
     * @return
     */
    @POST("/YzyIot/api/v1/Device/GetUpdateList")
    Observable<BaseResult<DeviceUpdateEntity>> checkUpdate(@HeaderMap Map<String, Object> header, @Body Map<String, Object> map);


    /**
     * 获得设备配置
     * @param header
     * @param map
     * @return
     */
    @POST("/YzyGame/api/v1/Achievement/DeviceConfig")
    Observable<BaseResult<RemoteDeviceConfigEntity>> getDeviceConfig(@HeaderMap Map<String, Object> header, @Body Map<String, Object> map);


    /**
     * mq消息回复
     * @param header
     * @param map
     * @return
     */
    @POST("/YzyIot/api/v1/Remote/MessageResult")
    Observable<BaseResult<String>> mqResult(@HeaderMap Map<String, Object> header, @Body Map<String, Object> map);

}
