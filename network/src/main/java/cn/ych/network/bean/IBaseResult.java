package cn.ych.network.bean;

/**
 * copyright (C) 2015-2020 广州油菜花信息科技有限公司
 * <p>
 *
 * @fileName: IBaseResult
 * @author: huangHongYuan
 * @Email: hhyyhh100@163.com
 * @date: 2020/9/23 20:59
 * @description:
 * @history: <p>
 * 作者：huangHongYuan
 * 修改时间：2020/9/23 20:59
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public interface IBaseResult<T>  {

    /**
     * 状态码
     * @return 状态码
     */
    int getCode();

    /**
     * 错误提示
     * @return 错误提示
     */
    String getMessage();


    /**
     * 实体类
     * @return 具体实体
     */
    T getData();
}
