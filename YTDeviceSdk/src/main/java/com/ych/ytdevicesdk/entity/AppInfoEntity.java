package com.ych.ytdevicesdk.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName AppInfoEntity
 * @date 2021/8/24 16:49
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/24 16:49
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public final class AppInfoEntity {
    /**
     * 安卓APP时=包名,硬件板子为具体实现名称,服务程序为服务端名称
     */
    private String AppName;
    /**
     * 板子版本,安卓时为sdk版本
     */
    private Integer HardVer;
    /**
     * 软件版本
     */
    private Integer SoftVer;
    /**
     * 下载地址
     */
    private String DownUrl;
    /**
     * Boot=自动启动
     */
    private String Action;
    /**
     *  应用的名字
     */
    private String AppTitle;
    /**
     * SaveTicket=存票控制板,
     * SaveTicketIO=存票辅助板,
     * CardReader=读卡器,
     * CardSender=发卡机,
     * CardSenderIO=发卡辅助板,
     * Printer=打印机,
     * CoinOut=出币控制板,
     * CoinOutIO=出币IO板,
     * Casher=纸钞机,
     * Casher=纸钞机,
     * Net=网卡,
     * GateIO=闸机辅助板,
     * APK=安卓应用,
     * Server=服务器
     */
    private String DeviceType;

    @Override
    public String toString() {
        return "AppInfoEntity{" +
                "AppName='" + AppName + '\'' +
                ", HardVer=" + HardVer +
                ", SoftVer=" + SoftVer +
                ", DownUrl='" + DownUrl + '\'' +
                ", Action='" + Action + '\'' +
                ", AppTitle='" + AppTitle + '\'' +
                ", DeviceType='" + DeviceType + '\'' +
                '}';
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Integer getHardVer() {
        return HardVer;
    }

    public void setHardVer(Integer hardVer) {
        HardVer = hardVer;
    }

    public Integer getSoftVer() {
        return SoftVer;
    }

    public void setSoftVer(Integer softVer) {
        SoftVer = softVer;
    }

    public String getDownUrl() {
        return DownUrl;
    }

    public void setDownUrl(String downUrl) {
        DownUrl = downUrl;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getAppTitle() {
        return AppTitle;
    }

    public void setAppTitle(String appTitle) {
        AppTitle = appTitle;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }
}
