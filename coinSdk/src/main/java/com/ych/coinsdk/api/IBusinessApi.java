package com.ych.coinsdk.api;

import com.ych.coinsdk.entity.ProvisionalRegistrationBean;
import com.ych.ytdevicesdk.api.BaseResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName IBusionessApi
 * @date 2021/11/17 16:03
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/11/17 16:03
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 */
public interface IBusinessApi {


    /**
     * 获取设备配置
     *
     * @param header Authorization TOKEN
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/GetExchangeConfig")
    Observable<BaseResult<String>> getDeviceConfig(@HeaderMap Map<String, Object> header);

    /**
     * 提交现金订单
     *
     * @param header Authorization TOKEN
     * @param params {
     *               "TransID": "100324",
     *               "Count": 55340,
     *               "Money": 59816,
     *               "MachineBalance": 77071,
     *               "OutStatus": 7404,
     *               "Expected": 22461
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/CashOrder")
    Observable<BaseResult<String>> pushCashOrder(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);

    /**
     * 上传提币结果
     *
     * @param header Authorization TOKEN
     * @param params {
     *               "TransID": "70080",
     *               "Count": 86779,
     *               "MachineBalance": 42568,
     *               "OutStatus": 94932,
     *               "Expected": 62054,
     *               "OriginTransID": "71782"
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/ExchangeResult")
    Observable<BaseResult<String>> pushExtractResult(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);

    /**
     * 获取兑币机套餐
     *
     * @param header Authorization TOKEN
     * @param params {
     *               "TransID": "70080",
     *               "Count": 86779,
     *               "MachineBalance": 42568,
     *               "OutStatus": 94932,
     *               "Expected": 62054,
     *               "OriginTransID": "71782"
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/GetExchangePackages")
    Observable<BaseResult<String>> getExchangeGoodsList(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);

    /**
     * 支付兑换订单
     *
     * @param header Authorization TOKEN
     * @param params {
     *               "TransID": "86501",
     *               "PackageID": "87112",
     *               "Amount": 42248,
     *               "Code": "05bsSd2dyy"
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/PayExchangePackage")
    Observable<BaseResult<String>> payExchangeOrder(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);

    /**
     * 支付会员兑换订单
     *
     * @param header Authorization TOKEN
     * @param params {
     *               "TransID": "90155",
     *               "LeaguerID": "91072",
     *               "Amount": 47194
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/PayMemberExchange")
    Observable<BaseResult<String>> payMemberExchangeOrder(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);

    /**
     * 第三方平台核销兑币
     *
     * @param header Authorization TOKEN
     * @param params {
     *               "TransID": "50854",
     *               "Code": "8saNlwZrty",
     *               "Platform": "Koubei"
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/PayThirdWriteOffExchange")
    Observable<BaseResult<String>> payThirdPlatformExchangeOrder(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);

    /**
     * 业务系统获得签名
     *
     * @param header 请求头
     * @param params 参数
     *               {
     *               "MacID": "53816",
     *               "PayDeviceType": "GHkDqObmbs",
     *               "ShowNum": 18728,
     *               "Sign": "enUDmyw1E3",
     *               "TS": "Z3AwZnFrY6"
     *               }
     * @return
     */
    @POST("/Hardware/api/v1.0/Pay/PreRegist")
    Observable<BaseResult<ProvisionalRegistrationBean>> deviceRegister(@HeaderMap Map<String, Object> header, @Body Map<String, Object> params);


}
