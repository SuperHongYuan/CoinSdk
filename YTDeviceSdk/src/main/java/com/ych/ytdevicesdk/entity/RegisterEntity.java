package com.ych.ytdevicesdk.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName RegisterEntity
 * @date 2021/8/25 17:31
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/25 17:31
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * @hide
 */
public class RegisterEntity {

    private String AppKey;
    private String ProductKey;
    private String DeviceName;
    private String DeviceKey;

    @Override
    public String toString() {
        return "RegisterEntity{" +
                "appKey='" + AppKey + '\'' +
                ", productKey='" + ProductKey + '\'' +
                ", deviceName='" + DeviceName + '\'' +
                ", deviceKey='" + DeviceKey + '\'' +
                '}';
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        this.AppKey = appKey;
    }

    public String getProductKey() {
        return ProductKey;
    }

    public void setProductKey(String productKey) {
        this.ProductKey = productKey;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        this.DeviceName = deviceName;
    }

    public String getDeviceKey() {
        return DeviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.DeviceKey = deviceKey;
    }
}
