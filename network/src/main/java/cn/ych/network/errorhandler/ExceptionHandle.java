package cn.ych.network.errorhandler;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * copyright (C) 2015-2020 广州油菜花信息科技有限公司
 * <p>
 *
 * @author 666666
 * @fileName: HttpErrorHandler
 * @author: ExceptionHandle
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
public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        String msg = "";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            Log.i("HandleException", "httpException.code() : " + httpException.code());
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    msg = httpException.getMessage();
                    if (msg.isEmpty()) {
                        msg = "网络错误";
                    }
                    break;
            }
            ex = new ResponeThrowable(msg, ERROR.HTTP_ERROR);
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            msg = resultException.message;
            ex = new ResponeThrowable(msg, resultException.code);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            msg = "解析错误";
            ex = new ResponeThrowable(msg, ERROR.PARSE_ERROR);
            return ex;
        } else if (e instanceof ConnectException) {
            msg = "连接失败";
            ex = new ResponeThrowable(msg, ERROR.NETWORD_ERROR);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            msg = "证书验证失败";
            ex = new ResponeThrowable(msg, ERROR.SSL_ERROR);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            msg = "连接超时";
            ex = new ResponeThrowable(msg, ERROR.TIMEOUT_ERROR);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            msg = "连接超时";
            ex = new ResponeThrowable(msg, ERROR.TIMEOUT_ERROR);
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            msg = "无效的域名或没有网络连接";
            ex = new ResponeThrowable(msg, ERROR.NET_ERROR);
            return ex;
        } else {
            msg = "未知错误";
            if (e!=null) {
                Log.e("HTTP", "" + e.getMessage());
                e.printStackTrace();
            }
            ex = new ResponeThrowable(msg, ERROR.UNKNOWN);
            return ex;
        }
    }


    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 网络错误
         */
        public static final int NET_ERROR = 1007;
    }

    public static class ResponeThrowable extends Exception {
        public long code;
        public String message;

        public ResponeThrowable(String message, long code) {
            super(message);
            this.code = code;
            this.message = message;
        }

    }

    public static class ServerException extends RuntimeException {
        public long code;
        public String message;

        public ServerException(String message) {
            super(message);
            this.message = message;
        }
    }
}

