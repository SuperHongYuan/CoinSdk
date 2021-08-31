package cn.ych.network.interceptor;

import java.io.IOException;

import base.INetworkRequiredInfo;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通用的请求拦截器
 * @author hhy
 */
public class CommonRequestInterceptor implements Interceptor {
    private INetworkRequiredInfo requiredInfo;

    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo) {
        this.requiredInfo = requiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersionCode", this.requiredInfo.getAppVersionCode()+"");
        builder.addHeader("appVersionName", this.requiredInfo.getAppVersionName());
        return chain.proceed(builder.build());
    }
}
