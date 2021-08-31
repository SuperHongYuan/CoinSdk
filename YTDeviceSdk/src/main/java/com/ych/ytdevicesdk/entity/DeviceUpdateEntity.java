package com.ych.ytdevicesdk.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName DeviceUpdata
 * @date 2021/8/30 9:32
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/30 9:32
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * @hide
 */
public class DeviceUpdateEntity {

    private List<UpdatesDTO> Updates;

    public List<UpdatesDTO> getUpdates() {
        return Updates;
    }

    public void setUpdates(List<UpdatesDTO> updates) {
        Updates = updates;
    }

    @Override
    public String toString() {
        return "DeviceUpdateEntity{" +
                "Updates=" + Updates +
                '}';
    }

    public static class UpdatesDTO {
        private String AppName;
        private Integer HardVer;
        private Integer SoftVer;
        private String DownUrl;
        private String Action;
        private String AppTitle;
        private String DeviceType;

        @Override
        public String toString() {
            return "UpdatesDTO{" +
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
}
