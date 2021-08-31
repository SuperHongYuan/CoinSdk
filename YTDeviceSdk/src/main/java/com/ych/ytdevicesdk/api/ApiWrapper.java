package com.ych.ytdevicesdk.api;

import com.ych.ytdevicesdk.config.Constants;

import java.util.List;

import cn.ych.network.NetWorkApi;
import cn.ych.network.errorhandler.ExceptionHandle;
import io.reactivex.functions.Function;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * author huang date 2019/4/17
 * description：
 *
 * @author huang
 * @hide
 */
public final class ApiWrapper extends NetWorkApi {

    private static volatile ApiWrapper instance;

    public static ApiWrapper getInstance() {
        if (instance == null) {
            synchronized (ApiWrapper.class) {
                if (instance == null) {
                    instance = new ApiWrapper();
                }
            }
        }
        return instance;
    }


    public ApiWrapper setIsDebug(boolean isDebug) {
        if (isDebug) {
            mBaseUrl = getTestUrl();
        } else {
            mBaseUrl = getFormalUrl();
        }
        return this;
    }

    public static void cleanApiWrapper() {
        if (instance != null) {
            instance.cleanCash();
            instance = null;
        }
    }

    /**
     * APP 需要处理的异常
     *
     * @return 类型
     */
    @Override
    public <T> Function<T, T> getAppErrorHandler() {
        return response -> {
            if (response instanceof BaseResult && ((BaseResult<?>) response).getResponseStatus().getErrorCode() != 0) {
                String msg = ((BaseResult<?>) response).getResponseStatus().getMessage() != null ? ((BaseResult<?>) response).getResponseStatus().getMessage() : "服务器数据异常";
                ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException(msg);
                exception.code = ((BaseResult<?>) response).getResponseStatus().getErrorCode();
                exception.message = msg;
                throw exception;
            }
            return response;
        };
    }

    /**
     * 批量添加 拦截器
     *
     * @return 批量拦截器
     */
    @Override
    public List<Interceptor> interceptorList() {
        return null;
    }

    @Override
    public int getRetryCount() {
        return 5;
    }

    /**
     * 获得服务
     *
     * @param service 服务
     * @return 数据
     */
    @Override
    public <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    /**
     * 获得正式环境的地址
     *
     * @return 正式环境 地址
     */
    @Override
    public String getFormalUrl() {
        return getUrl(Constants.BASE_RELEASE_URL);
    }

    /**
     * 测试环境地址
     *
     * @return 测试环境地址
     */
    @Override
    public String getTestUrl() {
        return getUrl(Constants.BASE_DEBUG_URL);
    }


    /**
     * 添加默认地址
     *
     * @param url
     * @return
     */
    public String getUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new NullPointerException("服务器地址不可以为空");
        }
        if (!url.contains("http")) {
            url = "http://" + url;
        }
        return url;
    }

    /**
     * @param url 检查URL是否正常
     * @return 是否是URL
     */
    public static boolean checkUrl(String url) {
        HttpUrl parse = HttpUrl.parse(url);
        return parse != null;
    }
}