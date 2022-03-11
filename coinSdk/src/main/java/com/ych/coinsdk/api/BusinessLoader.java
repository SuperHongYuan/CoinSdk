package com.ych.coinsdk.api;

import com.ych.coinsdk.entity.ProvisionalRegistrationBean;
import com.ych.ytdevicesdk.api.ApiWrapper;
import com.ych.ytdevicesdk.api.BaseLoader;
import com.ych.ytdevicesdk.api.BaseResult;

import java.util.Map;

import io.reactivex.Observer;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName BusinessLoader
 * @date 2021/11/17 16:38
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/11/17 16:38
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public class BusinessLoader extends BaseLoader {

    public final void getDeviceRegister(Map<String, Object> map, Observer<BaseResult<ProvisionalRegistrationBean>> observer){
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .deviceRegister(getHeaderToken(),map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void getDeviceConfig(Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .getDeviceConfig(getHeaderToken())
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void pushCashOrder(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .pushCashOrder(getHeader(map), map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void pushExtractResult(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .pushExtractResult(getHeader(map), map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void getExchangeGoodsList(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .getExchangeGoodsList(getHeader(map), map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }


    public final void payExchangeOrder(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .payExchangeOrder(getHeader(map), map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void payMemberExchangeOrder(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .payMemberExchangeOrder(getHeader(map), map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }

    public final void payThirdPlatformExchangeOrder(Map<String, Object> map, Observer<BaseResult<String>> observer) {
        ApiWrapper.getInstance()
                .setIsDebug(useTestUrl)
                .getService(IBusinessApi.class)
                .payThirdPlatformExchangeOrder(getHeader(map), map)
                .compose(ApiWrapper.getInstance().applySchedulers(observer));
    }
}
