package com.ych.ytdevicesdk.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName RemoteDeviceConfigEntity
 * @date 2021/8/30 10:37
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/30 10:37
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 *
 * @hide
 */
public class RemoteDeviceConfigEntity {

    private Integer StepIndex;
    private String AchieveServer;
    private List<ParaListDTO> ParaList;
    private String DeviceID;
    private String Category;
    private String MallCode;
    private String BizServer;
    private String LocalServer;

    @Override
    public String toString() {
        return "RemoteDeviceConfigEntity{" +
                "StepIndex=" + StepIndex +
                ", AchieveServer='" + AchieveServer + '\'' +
                ", ParaList=" + ParaList +
                ", DeviceID='" + DeviceID + '\'' +
                ", Category='" + Category + '\'' +
                ", MallCode='" + MallCode + '\'' +
                ", BizServer='" + BizServer + '\'' +
                ", LocalServer='" + LocalServer + '\'' +
                '}';
    }

    public Integer getStepIndex() {
        return StepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        StepIndex = stepIndex;
    }

    public String getAchieveServer() {
        return AchieveServer;
    }

    public void setAchieveServer(String achieveServer) {
        AchieveServer = achieveServer;
    }

    public List<ParaListDTO> getParaList() {
        return ParaList;
    }

    public void setParaList(List<ParaListDTO> paraList) {
        ParaList = paraList;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getMallCode() {
        return MallCode;
    }

    public void setMallCode(String mallCode) {
        MallCode = mallCode;
    }

    public String getBizServer() {
        return BizServer;
    }

    public void setBizServer(String bizServer) {
        BizServer = bizServer;
    }

    public String getLocalServer() {
        return LocalServer;
    }

    public void setLocalServer(String localServer) {
        LocalServer = localServer;
    }

    public static class ParaListDTO {
        private String Key;
        private String Name;
        private String Value;

        @Override
        public String toString() {
            return "ParaListDTO{" +
                    "Key='" + Key + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Value='" + Value + '\'' +
                    '}';
        }

        public String getKey() {
            return Key;
        }

        public void setKey(String key) {
            Key = key;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }
}
