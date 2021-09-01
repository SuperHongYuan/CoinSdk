package com.ych.ytdevicesdk;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.ych.ytdevicesdk.api.BaseResult;
import com.ych.ytdevicesdk.api.DeviceConnectLoader;
import com.ych.ytdevicesdk.config.App;
import com.ych.ytdevicesdk.config.CommandType;
import com.ych.ytdevicesdk.config.Constants;
import com.ych.ytdevicesdk.entity.AppInfoEntity;
import com.ych.ytdevicesdk.entity.DeviceUpdateEntity;
import com.ych.ytdevicesdk.entity.HeartbeatEntity;
import com.ych.ytdevicesdk.entity.RegisterEntity;
import com.ych.ytdevicesdk.entity.RemoteDeviceConfigEntity;
import com.ych.ytdevicesdk.utils.AliMessageEntity;
import com.ych.ytdevicesdk.utils.AliMessageQueueUtils;
import com.ych.ytdevicesdk.utils.SignatureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import cn.ych.network.errorhandler.ExceptionHandle;
import cn.ych.network.observer.BaseResultObserver;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName DeviceManager
 * @date 2021/8/24 14:50
 * @history
 * @description </n>
 * 作者：huang
 * 修改时间：2021/8/24 14:50
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * <p>
 * 设备系统管理器
 */
public final class DeviceManager implements IDeviceManager {

    private final DeviceConnectLoader deviceConnectLoader;
    private AliMessageQueueUtils aliMessageQueueUtils;
    private boolean isOpenLog;
    private boolean isDebug;
    private boolean useTestUrl;

    private DeviceManager() {
        deviceConnectLoader = new DeviceConnectLoader();
    }

    private static volatile IDeviceManager instance = null;

    /**
     * 初始化响应监听器
     */
    private InitListener initListener;

    /**
     * mq 事件集
     */
    private IMqMessageListener eventListener;


    /**
     * 获取操作对象
     *
     * @return {@link IDeviceManager}
     */
    public static IDeviceManager getInstance() {
        if (instance == null) {
            synchronized (DeviceManager.class) {
                if (instance == null) {
                    instance = new DeviceManager();
                }
            }
        }
        return instance;
    }


    @Override
    public IDeviceManager initSdk(String appKey, String deviceType, AppInfoEntity... appInfoEntity) {
        Map<String, Object> map = new TreeMap<>();
        map.put("OSVer", DeviceUtils.getSDKVersionCode());
        map.put("MainBoardNum", PhoneUtils.getSerial());
        map.put("Memery", Runtime.getRuntime().maxMemory() / 1024);
        map.put("Disk", SDCardUtils.getInternalTotalSize() / (1024 * 1024));
        map.put("OSName", "Android");
        map.put("OSVerName", DeviceUtils.getSDKVersionName());
        map.put("RegCode", appKey);
        map.put("DeviceType", deviceType);
        for (AppInfoEntity infoEntity : appInfoEntity) {
            infoEntity.setHardVer(BuildConfig.versionCode);
        }
        map.put("AppInfo", JSONObject.toJSON(appInfoEntity));
        getUniversalMac(map);
        deviceConnectLoader.registerProduct(map, new BaseResultObserver<BaseResult<RegisterEntity>>() {
            @Override
            public void onSuccess(BaseResult<RegisterEntity> result) {
                RegisterEntity data = result.getData();
                LogUtils.d("RegisterEntity response : " + JSON.toJSONString(result));
                if (data != null) {
                    Constants.SIGN_KEY = data.getAppKey();
                    initAliMq(data);
                    // 检查更新
                    checkUpdate(appInfoEntity);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                if (initListener != null) {
                    if (e instanceof ExceptionHandle.ResponeThrowable) {
                        ExceptionHandle.ResponeThrowable throwable = (ExceptionHandle.ResponeThrowable) e;
                        initListener.registerFailed(throwable.code, throwable.message);
                    } else if (e instanceof ExceptionHandle.ServerException) {
                        ExceptionHandle.ServerException throwable = (ExceptionHandle.ServerException) e;
                        initListener.registerFailed(throwable.code, throwable.message);
                    } else {
                        initListener.registerFailed(-9999, e.getMessage());
                    }
                }
            }
        });
        return instance;
    }

    /**
     * 感觉没必要，暂时不调用
     */
    private void checkUpdate(AppInfoEntity... appInfoEntity) {
        Map<String, Object> map = new TreeMap<>();
        map.put("Apps", JSONObject.toJSON(appInfoEntity));
        getUniversalMac(map);
        deviceConnectLoader.checkUpdate(map, new BaseResultObserver<BaseResult<DeviceUpdateEntity>>() {
            @Override
            public void onSuccess(BaseResult<DeviceUpdateEntity> baseResult) {
                DeviceUpdateEntity data = baseResult.getData();
                if (data == null || data.getUpdates() == null) {
                    onFailure(new Throwable("无法获取更新信息"));
                    return;
                }
                if (data.getUpdates().isEmpty()) {
                    getDeviceConfig();
                } else {
                    if (initListener != null) {
                        List<String> updateUrls = new ArrayList<>(3);
                        for (DeviceUpdateEntity.UpdatesDTO update : baseResult.getData().getUpdates()) {
                            updateUrls.add(update.getDownUrl());
                        }
                        String[] updateUrlArrays = new String[updateUrls.size()];
                        initListener.needUpdate(updateUrls.toArray(updateUrlArrays));
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                universalFailure(ErrorType.UPDATE, e);
            }
        });
    }


    public void getDeviceConfig() {
        Map<String, Object> map = new TreeMap<>();
        getUniversalMac(map);
        deviceConnectLoader.getDeviceConfig(map, new BaseResultObserver<BaseResult<RemoteDeviceConfigEntity>>() {
            @Override
            public void onSuccess(BaseResult<RemoteDeviceConfigEntity> entityBaseResult) {
                if (entityBaseResult == null) {
                    onFailure(new Throwable("未能获取到配置信息"));
                    return;
                }
                RemoteDeviceConfigEntity data = entityBaseResult.getData();
                if (data == null) {
                    onFailure(new Throwable("未能获取到配置信息"));
                    return;
                }
                if (initListener != null) {
                    initListener.bindStatus(data.getStepIndex() != 0);
                }
                Constants.BASE_BUSINESS_URL = data.getLocalServer();
                if (Constants.BASE_BUSINESS_URL == null || Constants.BASE_BUSINESS_URL.isEmpty()) {
                    Constants.BASE_BUSINESS_URL = data.getBizServer();
                }
                heartbeat(5 * 60, 11);
            }

            @Override
            public void onFailure(Throwable e) {
                universalFailure(ErrorType.BIND, e);
            }
        });
    }

    private void initAliMq(RegisterEntity data) {
        aliMessageQueueUtils = AliMessageQueueUtils.getInstance()
                .isDebug(isDebug)
                .initLog(isOpenLog)
                .registerPushConnect()
                .initMessageQuery(data.getProductKey(), data.getDeviceName(), data.getDeviceKey(), new ILinkKitConnectListener() {
                    @Override
                    public void onError(AError error) {
                        // 初始化失败 error包含初始化错误信息
                        LogUtils.e("LinkKit onError : " + JSON.toJSON(error));
                        if (initListener != null) {
                            initListener.registerFailed(error.getCode(), error.getMsg());
                        }
                    }

                    @Override
                    public void onInitDone(Object data) {
                        // 初始化成功 data 作为预留参数
                        // MQ 连接成功 开始监听
                        aliMessageQueueUtils.mqSubscription();
                    }
                })
                .setEventListener(aliMessageEntity -> {
                    if (eventListener != null) {
                        switch (aliMessageEntity.getCommand()) {
                            case CommandType.BIND:
                                eventListener.deviceBinding();
                                break;
                            case CommandType.UNBIND:
                                eventListener.deviceUnBinding();
                                break;
                            case CommandType.REBOOT:
                                eventListener.deviceReBoot();
                                break;
                            default:
                                break;
                        }
                    }

                });

    }

    public void disMqConnect() {
        aliMessageQueueUtils.disSubscription();
        aliMessageQueueUtils.disConnect();
    }

    @Override
    public IDeviceManager initSdk(String appKey, String deviceType, AppInfoEntity appInfoEntity, InitListener initListener) {
        return initSdk(appKey, deviceType, appInfoEntity).setInitListener(initListener);
    }

    @Override
    public IDeviceManager setInitListener(InitListener initListener) {
        this.initListener = initListener;
        return instance;
    }

    @Override
    public IDeviceManager debugger(boolean isOpen, boolean useTestUrl) {
        isDebug = isOpen;
        this.useTestUrl = useTestUrl;
        deviceConnectLoader.setIsDebug(useTestUrl);
        return instance;
    }

    @Override
    public IDeviceManager logger(boolean isOpen) {
        isOpenLog = isOpen;
        return instance;
    }

    @Override
    public IDeviceManager connectTest(IConnectTestResponse iConnectTestResponse) {
        deviceConnectLoader.connectTest(new BaseResultObserver<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> stringBaseResult) {

            }

            @Override
            public void onFailure(Throwable e) {
                universalFailure(ErrorType.CONNECT, e);
            }
        });
        return instance;
    }

    /**
     * @param wait       等待时长 单位秒
     * @param deviceType 默认 11
     */
    public void heartbeat(long wait, int deviceType) {
        ThreadUtils.executeByIoAtFixRate(new ThreadUtils.SimpleTask<Boolean>() {
            @Override
            public Boolean doInBackground() throws Throwable {
                WifiManager wifiManager = (WifiManager) App.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                Map<String, Object> map = new TreeMap<>();
                if (connectionInfo != null) {
                    map.put("SSID", connectionInfo.getSSID());
                    map.put("Signal", connectionInfo.getRssi());
                    map.put("Delay", connectionInfo.getLinkSpeed());
                } else {
                    map.put("SSID", "");
                    map.put("Signal", 100);
                    map.put("Delay", 30);
                }
                map.put("Agreement", 0);
                map.put("AgreeVer", "");
                map.put("DeviceType", deviceType);
                getUniversalMac(map);
                deviceConnectLoader.deviceHeartbeat(map, new BaseResultObserver<BaseResult<HeartbeatEntity>>() {
                    @Override
                    public void onSuccess(BaseResult<HeartbeatEntity> stringBaseResult) {

                    }

                    @Override
                    public void onFailure(Throwable e) {
                        universalFailure(ErrorType.HEARTBEAT, e);
                    }
                });
                return null;
            }

            @Override
            public void onSuccess(Boolean result) {

            }
        }, wait, TimeUnit.SECONDS, 10);

    }


    @Override
    public void deviceErrorSubmit(int errorType, String errorMsg) {
        Map<String, Object> map = new TreeMap<>();
        map.put("LogFiles", String.format("errorType %s , errorMsg %s ", errorType, errorMsg));
        getUniversalMac(map);
        deviceConnectLoader.pushDeviceError(map, new BaseResultObserver<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> stringBaseResult) {

            }

            @Override
            public void onFailure(Throwable e) {
                universalFailure(ErrorType.DEVICE_ERROR, e);
            }
        });
    }

    /**
     * 获取通用的参数
     *
     * @param map 原参数
     */
    private void getUniversalMac(Map<String, Object> map) {
        String macId = PhoneUtils.getIMEI();
        String signatureTemp = SignatureUtils.getSignature(macId, Constants.DEFAULT_SIGN_KEY);
        map.put("MacID", macId + signatureTemp.substring(0, 4));
        map.put("TimeSpan", TimeUtils.getNowMills());
    }

    @Override
    public IDeviceManager setEventListener(IMqMessageListener eventListener) {
        this.eventListener = eventListener;
        return instance;
    }


    /**
     * 错误通用处理
     *
     * @param errorType {@link IDeviceManager.ErrorType} 错误类型
     * @param e         具体信息
     */
    private void universalFailure(@ErrorType String errorType, Throwable e) {
        if (initListener != null) {
            if (e instanceof ExceptionHandle.ResponeThrowable) {
                ExceptionHandle.ResponeThrowable throwable = (ExceptionHandle.ResponeThrowable) e;
                initListener.onError(errorType, throwable.code, throwable.message);
            } else if (e instanceof ExceptionHandle.ServerException) {
                ExceptionHandle.ServerException throwable = (ExceptionHandle.ServerException) e;
                initListener.onError(errorType, throwable.code, throwable.message);
            } else {
                initListener.onError(errorType, -9999, e.getMessage());
            }
        }
    }

    @Override
    public String getSdkVersion() {
        return BuildConfig.versionName;
    }

}
