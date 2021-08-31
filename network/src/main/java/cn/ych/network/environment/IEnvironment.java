package cn.ych.network.environment;

/**
 * 通用环境
 * @author hhy
 */
public interface IEnvironment {
    /**
     * 获得正式环境的地址
     * @return 正式环境 地址
     */
    String getFormalUrl();

    /**
     * 测试环境地址
     * @return 测试环境地址
     */
    String getTestUrl();
}
