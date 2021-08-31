package com.ych.ytdevicesdk.utils;

import androidx.annotation.StringDef;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName AliMessageEntity
 * @date 2021/8/27 18:04
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/27 18:04
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * @hide
 */
public class AliMessageEntity {



    private String TransID;
    private Long Timestamp;
    private String Command;
    private Object Data;

    @Override
    public String toString() {
        return "AliMessageEntity{" +
                "TransID='" + TransID + '\'' +
                ", Timestamp=" + Timestamp +
                ", Command='" + Command + '\'' +
                ", Data=" + Data +
                '}';
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }

    public String getCommand() {
        return Command;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
