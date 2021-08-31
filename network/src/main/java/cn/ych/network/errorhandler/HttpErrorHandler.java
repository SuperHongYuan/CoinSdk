package cn.ych.network.errorhandler;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * copyright (C) 2015-2020 广州油菜花信息科技有限公司
 * <p>
 *
 * @author 666666
 * @fileName: HttpErrorHandler
 * @author: huangHongYuan
 * @Email: hhyyhh100@163.com
 * @date: 2020/9/22 16:14
 * @description: HttpResponseFunc处理以下两类网络错误：
 * 1、http请求相关的错误，例如：404，403，socket timeout等等；
 * 2、应用数据的错误会抛RuntimeException，最后也会走到这个函数来统一处理；
 * @history: <p>
 * 作者：huangHongYuan
 * 修改时间：2020/9/22 16:14
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
