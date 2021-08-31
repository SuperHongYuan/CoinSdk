package com.ych.coinsdk.config;

import android.app.Application;

import com.ych.ytdevicesdk.BuildConfig;
import com.ych.ytdevicesdk.api.ApiWrapper;

import base.INetworkRequiredInfo;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName App
 * @date 2021/8/30 17:50
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/30 17:50
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public class App extends com.ych.ytdevicesdk.config.App {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetApi();
    }

    private void initNetApi(){
        INetworkRequiredInfo iNetworkRequiredInfo = new INetworkRequiredInfo() {
            @Override
            public int getTimeOut() {
                return 15;
            }

            @Override
            public String getAppVersionName() {
                return "YT_DEVICE_SDK";
            }

            @Override
            public long getAppVersionCode() {
                return 1;
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }

            @Override
            public Application getApplicationContext() {
                return App.this;
            }
        };
        ApiWrapper.init(iNetworkRequiredInfo);
    }
}
