package cn.ych.network.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author huang
 * @param <T> 响应的消息
 */
public abstract class BaseResultObserver<T> implements Observer<T> {
    public Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onStart();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
        onComplete();
    }

    @Override
    public void onComplete() {
        onEnd();
        close();
    }

    /**
     * 关闭连接资源
     */
    private void close() {
        if (disposable!=null&&!disposable.isDisposed()){
            disposable.dispose();
        }
    }

    /**
     * 请求开始时
     */
    public void onStart(){}

    /**
     * 请求完成
     */
    public void onEnd(){}

    /**
     * 请求成功
     * @param t 响应信息
     */
    public abstract void onSuccess(T t);

    /**
     * 请求失败
     * @param e 异常信息
     */
    public abstract void onFailure(Throwable e);
}
