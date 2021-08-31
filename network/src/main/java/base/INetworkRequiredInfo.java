package base;

import android.app.Application;

/**
 * 网络访问的需要的信息
 * @author hhy
 */
public interface INetworkRequiredInfo {

    /**
     * 延时设置得大一点，防止连接时间过长没得到结果
     */
    int CONNECT_TIMEOUT = 60;

    /**
     * 获得 APP 版本
     * @return version name
     */
    String getAppVersionName();

    /**
     * 获得 APP 版本的 code
     * @return version code
     */
    long getAppVersionCode();

    /**
     * 是否是debug模式
     * @return 是否是 debug
     */
    boolean isDebug();

    /**
     * 主应用的 application
     * @return application
     */
    Application getApplicationContext();

    /**
     * 超时时长
     * @return 超时
     */
    default int getTimeOut(){
        return CONNECT_TIMEOUT;
    };
}