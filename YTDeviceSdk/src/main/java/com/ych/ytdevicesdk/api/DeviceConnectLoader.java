package com.ych.ytdevicesdk.api;

import com.blankj.utilcode.util.ApiUtils;
import com.ych.ytdevicesdk.IDeviceManager;
import com.ych.ytdevicesdk.entity.DeviceUpdateEntity;
import com.ych.ytdevicesdk.entity.HeartbeatEntity;
import com.ych.ytdevicesdk.entity.RegisterEntity;
import com.ych.ytdevicesdk.entity.RemoteDeviceConfigEntity;

import java.util.Map;
import java.util.Observable;

import io.reactivex.Observer;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName DeviceConnectLoader
 * @date 2021/8/24 18:34
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/24 18:34
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * @hide
 */
public final class DeviceConnectLoader extends BaseLoader {

    public final void connectTest(Observer<BaseResult<String>> observer){
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .connectTest()
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void pushDeviceError(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .pushDeviceError(getYZYHeader(map), getParameter(map))
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void registerProduct(Map<String, Object> map, Observer<BaseResult<RegisterEntity>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .registerProduct(getHeaderYZHRegister(map), getParameter(map))
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void deviceHeartbeat(Map<String, Object> map, Observer<BaseResult<HeartbeatEntity>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .deviceHeartbeat(getYZYHeader(map), getParameter(map))
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void checkUpdate(Map<String, Object> map, Observer<BaseResult<DeviceUpdateEntity>> obServer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .checkUpdate(getYZYHeader(map),getParameter(map))
                .compose(ApiWrapper.getInstance().applySchedulers(obServer));
    }

    public final void getDeviceConfig(Map<String,Object> map,Observer<BaseResult<RemoteDeviceConfigEntity>> observer){
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .getDeviceConfig(getYZYHeader(map),getParameter(map))
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void messageQueueResult(Map<String,Object> map,Observer<BaseResult<String>> observer){
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IDeviceConnectApi.class)
                .mqResult(getYZYHeader(map),getParameter(map))
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }



}
