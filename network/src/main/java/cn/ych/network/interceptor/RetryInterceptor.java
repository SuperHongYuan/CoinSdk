package cn.ych.network.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 重试拦截器
 *
 * @author huang
 */
public class RetryInterceptor implements Interceptor {
    /**
     * 最大重试次数
     */
    public int maxRetry;
    /**
     * 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
     */
    private int retryNum = 0;

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i("okHttp", "retryNum = " + retryNum);
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            Log.i("okHttp", "retryNum = " + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}