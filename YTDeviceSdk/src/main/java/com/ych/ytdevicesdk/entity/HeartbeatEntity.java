package com.ych.ytdevicesdk.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName HeartbeatEntity
 * @date 2021/8/30 19:00
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/30 19:00
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public class HeartbeatEntity {

    private long timestamp;

    @Override
    public String toString() {
        return "HeartbeatEntity{" +
                "timestamp=" + timestamp +
                '}';
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
