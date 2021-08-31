package cn.ych.network.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 请求时间拦截器
 * @author hhy
 */
public class TimerResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.d(TAG, "requestTime="+ (System.currentTimeMillis() - requestTime));
        return response;
    }
}