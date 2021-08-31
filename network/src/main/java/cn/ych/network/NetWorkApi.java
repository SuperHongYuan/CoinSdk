package cn.ych.network;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import base.INetworkRequiredInfo;
import cn.ych.network.environment.IEnvironment;
import cn.ych.network.errorhandler.HttpErrorHandler;
import cn.ych.network.interceptor.RetryInterceptor;
import cn.ych.network.interceptor.TimerResponseInterceptor;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * copyright (C) 2015-2020 广州油菜花信息科技有限公司
 * <p>
 *
 * @fileName: NetWorkApi
 * @author: huangHongYuan
 * @Email: hhyyhh100@163.com
 * @date: 2020/9/22 16:14
 * @description:
 * @history: <p>
 * 作者：huangHongYuan
 * 修改时间：2020/9/22 16:14
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public abstract class NetWorkApi implements IEnvironment {

    private static INetworkRequiredInfo iNetworkRequiredInfo;
    public String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();


    public NetWorkApi() {
        if (iNetworkRequiredInfo.isDebug()) {
            mBaseUrl = getTestUrl();
        } else {
            mBaseUrl = getFormalUrl();
        }
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
    }

    public void cleanCash() {
        retrofitHashMap.clear();
        retrofitHashMap = null;
        retrofitHashMap = new HashMap<>();
    }

    /**
     * 获得 retrofit 对象有缓存则拿缓存
     *
     * @param serviceClass api class
     * @return retrofit 对象
     */
    public Retrofit getRetrofit(Class<?> serviceClass) {
        if (retrofitHashMap.get(mBaseUrl + serviceClass.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + serviceClass.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mBaseUrl);
        builder.client(getOkHttpClient());
        builder.addConverterFactory(GsonConverterFactory.create(new Gson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        retrofitHashMap.put(mBaseUrl + serviceClass.getName(), retrofit);
        return retrofit;
    }

    /**
     * 获得 OKHttp client 的配置信息
     *
     * @return okHttp客户
     */
    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            List<Interceptor> interceptors = interceptorList();
            if (interceptors != null) {
                for (Interceptor interceptor : interceptors) {
                    okHttpClientBuilder.addInterceptor(interceptor);
                }
            }
            okHttpClientBuilder.addInterceptor(new TimerResponseInterceptor());
            if (getRetryCount() > 0) {
                okHttpClientBuilder.retryOnConnectionFailure(true);
                okHttpClientBuilder.addInterceptor(new RetryInterceptor(getRetryCount()));
            }
            if (iNetworkRequiredInfo != null && (iNetworkRequiredInfo.isDebug())) {
                okHttpClientBuilder.connectTimeout(iNetworkRequiredInfo.getTimeOut(), TimeUnit.SECONDS);
                okHttpClientBuilder.readTimeout(iNetworkRequiredInfo.getTimeOut(), TimeUnit.SECONDS);
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }


    /**
     * 启动连接转换器
     */
    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return upstream -> {
            Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(getAppErrorHandler())
                    .onErrorResumeNext(new HttpErrorHandler<>());
            observable.subscribe(observer);
            return observable;
        };
    }

    /**
     * APP 需要处理的异常
     *
     * @param <T> 类型
     * @return 类型
     */
    public abstract <T> Function<T, T> getAppErrorHandler();


    /**
     * 批量添加 拦截器
     *
     * @return 批量拦截器
     */
    public abstract List<Interceptor> interceptorList();

    /**
     * 设置重试次数
     *
     * @return 重试次数
     */
    public abstract int getRetryCount();

    /**
     * 获得服务
     *
     * @param service 服务
     * @param <T>     接口类型
     * @return 数据
     */
    public abstract <T> T getService(Class<T> service);

}
