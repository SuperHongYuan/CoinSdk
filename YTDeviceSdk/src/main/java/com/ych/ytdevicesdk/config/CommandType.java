package com.ych.ytdevicesdk.config;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({CommandType.BIND, CommandType.UNBIND, CommandType.REBOOT})
@Retention(RetentionPolicy.SOURCE)
public @interface CommandType {
    /**
     * 绑定
     */
    String BIND = "Bind";
    /**
     * 取消绑定
     */
    String UNBIND = "Unbind";
    /**
     * 重启
     */
    String REBOOT = "Reboot";
}