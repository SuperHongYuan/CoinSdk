package com.ych.coinsdk;

import com.ych.coinsdk.entity.GoodsEntity;
import com.ych.ytdevicesdk.api.BaseResult;

import io.reactivex.Observer;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName IBusinessManager
 * @date 2021/8/30 18:08
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/30 18:08
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public interface IBusinessManager {


    /**
     * 获取商品列表
     *
     * @param observer 响应
     * @param <T>      设置类型
     */
    <T> void getGoodsList(Observer<BaseResult<T>> observer);

    /**
     * 商品下单
     *
     * @param goodsEntity 商品信息
     * @param count       数量
     * @param observer    响应
     * @param <T>         设置类型
     */
    <T> void generateOrder(GoodsEntity goodsEntity, int count, Observer<BaseResult<T>> observer);

    /**
     * 查询支付结果
     *
     * @param orderId  订单ID
     * @param observer 响应
     * @param <T>      设置类型
     */
    <T> void queryPayOrder(String orderId, Observer<BaseResult<T>> observer);

    /**
     * 登录会员
     *
     * @param type     登录方式
     * @param code     数据内容
     * @param observer 响应
     * @param <T>      设置类型
     */
    <T> void loginMember(String type, String code, Observer<BaseResult<T>> observer);

    /**
     * 会员提币
     *
     * @param number   提币数量
     * @param observer 响应
     * @param <T>      设置类型
     */
    <T> void memberExtract(int number, Observer<BaseResult<T>> observer);

    /**
     * 第三方平台提币
     *
     * @param code     核销码
     * @param observer 响应
     * @param <T>      设置类型
     */
    <T> void platformExtract(String code, Observer<BaseResult<T>> observer);

    /**
     * 提币完成
     *
     * @param orderID  订单Id
     * @param observer 响应
     * @param <T>      设置类型
     */
    <T> void extractComplete(String orderID, Observer<BaseResult<T>> observer);
}
