package com.ych.ytdevicesdk;

import androidx.annotation.StringDef;

import com.ych.ytdevicesdk.entity.AppInfoEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName IDeviceManager
 * @date 2021/8/24 10:07
 * @description 设备管理器;包含设备连接等管理方法
 * @history 1.0
 * </n>
 * 作者：huang
 * 修改时间：2021/8/24 10:07
 * 版本：<version> 1.0
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public interface IDeviceManager {

    /**
     * 错误类型
     */
    @StringDef({ErrorType.REGISTER, ErrorType.UPDATE, ErrorType.BIND, ErrorType.CONNECT,ErrorType.HEARTBEAT,ErrorType.DEVICE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface ErrorType {
        /**
         * 注册
         */
        String REGISTER = "REGISTER";
        /**
         * 更新
         */
        String UPDATE = "UPDATE";
        /**
         * 绑定
         */
        String BIND = "BIND";
        /**
         * 连接
         */
        String CONNECT = "CONNECT";

        /**
         * 心跳
         */
        String HEARTBEAT = "heartbeat";
        /**
         * 设备错误
         */
        String DEVICE_ERROR = "DEVICE_ERROR";
    }

    /**
     * 初始化监听器
     */
    interface InitListener {
        /**
         * 注册失败
         *
         * @param errorCode 错误吗
         * @param errorMsg  错误信息
         */
        void registerFailed(long errorCode, String errorMsg);

        /**
         * 需要更新
         *
         * @param updateAddress 更新地址
         */
        void needUpdate(String... updateAddress);

        /**
         * 启动状态
         *
         * @param isSuccess 是否成功
         * @param deviceNumber 设备编码用于生成二维码进行设备绑定
         */
        void bindStatus(boolean isSuccess,String deviceNumber);

        /**
         * 错误信息
         *
         * @param type      类型
         * @param errorCode 错误码
         * @param msg       错误信息
         */
        void onError(@ErrorType String type, long errorCode, String msg);

    }

    /**
     * 连接测试响应
     */
    interface IConnectTestResponse {
        /**
         * 连接测试返回
         *
         * @param isSuccess 是否成功
         * @param info      返回信息
         */
        void callBack(boolean isSuccess, String info);
    }

    /**
     * 阿里 MQ 消息监听器
     */
    interface IMqMessageListener {
        /**
         * 设备绑定回调
         */
        void deviceBinding();

        /**
         * 设备解绑回调
         */
        void deviceUnBinding();

        /**
         * 设备重启
         */
        void deviceReBoot();
    }

    /**
     * 初始化参数
     *
     * @param SSID          设备的key
     * @param deviceType    设备的类型
     * @param appInfoEntity 应用信息
     * @return {@link IDeviceManager}
     */
    IDeviceManager initSdk(String SSID, String deviceType, AppInfoEntity... appInfoEntity);

    /**
     * 初始化参数
     *
     * @param SSID          设备的key
     * @param deviceType    设备的类型
     * @param appInfoEntity 应用信息
     * @param initListener  初始化监听器
     * @return {@link IDeviceManager}
     */
    IDeviceManager initSdk(String SSID, String deviceType, AppInfoEntity appInfoEntity, InitListener initListener);

    /**
     * 初始化SDK
     *
     * @param initListener 初始化监听器
     * @return {@link IDeviceManager}
     */
    IDeviceManager setInitListener(InitListener initListener);


    /**
     * 调试器，是否开启调试模式
     *
     * @param isOpen     是否开启
     * @param useTestUrl 使用测试服务器
     * @return {@link IDeviceManager}
     */
    IDeviceManager debugger(boolean isOpen, boolean useTestUrl);

    /**
     * 日志开关，是否开启日志输出
     *
     * @param isOpen 是否开启
     * @return {@link IDeviceManager}
     */
    IDeviceManager logger(boolean isOpen);

    /**
     * 连接测试
     *
     * @param iConnectTestResponse 连接测试响应
     * @return {@link IDeviceManager}
     */
    IDeviceManager connectTest(IConnectTestResponse iConnectTestResponse);


    /**
     * 设置mq消息监听
     *
     * @param eventListener mq消息监听器
     * @return {@link IDeviceManager}
     */
    IDeviceManager setEventListener(IMqMessageListener eventListener);

    /**
     * 获取SDK版本
     *
     * @return 版本信息
     */
    String getSdkVersion();

    /**
     * 提交错误信息
     *
     * @param errorType 错误类型
     * @param errorMsg  错误数据
     */
    void deviceErrorSubmit(int errorType, String errorMsg);

}
