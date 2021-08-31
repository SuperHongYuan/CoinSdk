package com.ych.coinsdk;

import com.ych.coinsdk.entity.GoodsEntity;
import com.ych.ytdevicesdk.DeviceManager;
import com.ych.ytdevicesdk.IDeviceManager;
import com.ych.ytdevicesdk.api.BaseResult;
import com.ych.ytdevicesdk.api.DeviceConnectLoader;

import io.reactivex.Observer;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName BusinessManager
 * @date 2021/8/30 18:03
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/30 18:03
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 *
 * 业务系统管理器
 */
public final class BusinessManager implements IBusinessManager {

    private BusinessManager() {

    }

    private static volatile IBusinessManager instance = null;


    /**
     * 获取操作对象
     *
     * @return {@link IBusinessManager}
     */
    public static IBusinessManager getInstance() {
        if (instance == null) {
            synchronized (DeviceManager.class) {
                if (instance == null) {
                    instance = new BusinessManager();
                }
            }
        }
        return instance;
    }


    @Override
    public <T> void getGoodsList(Observer<BaseResult<T>> observer) {

    }

    @Override
    public <T> void generateOrder(GoodsEntity goodsEntity, int count, Observer<BaseResult<T>> observer) {

    }

    @Override
    public <T> void queryPayOrder(String orderId, Observer<BaseResult<T>> observer) {

    }

    @Override
    public <T> void loginMember(String type, String code, Observer<BaseResult<T>> observer) {

    }

    @Override
    public <T> void memberExtract(int number, Observer<BaseResult<T>> observer) {

    }

    @Override
    public <T> void platformExtract(String code, Observer<BaseResult<T>> observer) {

    }

    @Override
    public <T> void extractComplete(String orderID, Observer<BaseResult<T>> observer) {

    }
}
