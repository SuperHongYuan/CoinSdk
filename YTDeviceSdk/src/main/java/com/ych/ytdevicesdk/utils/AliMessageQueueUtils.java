package com.ych.ytdevicesdk.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentNet;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.blankj.utilcode.util.LogUtils;
import com.ych.ytdevicesdk.config.App;

import java.util.HashMap;
import java.util.Map;

/**
 * copyright (C) 2015-2021
 *
 * @author huang
 * @fileName AliMessageQueueUtils
 * @date 2021/8/26 14:03
 * @description
 * @history </n>
 * 作者：huang
 * 修改时间：2021/8/26 14:03
 * 版本：<version>
 * <p>
 * If the implementation is hard to explain, it's a bad idea.
 * If the implementation is easy to explain, it may be a good idea.
 * @hide
 */
public final class AliMessageQueueUtils {

    /**
     * /${productKey}/${deviceName}/user/get
     * mq 监听的话题
     */
    private static final String TOPIC = "/%s/%s/user/get";
    private static volatile AliMessageQueueUtils instance = null;

    /**
     * 连接状态监听器
     */
    private final IConnectNotifyListener connectListener = new IConnectNotifyListener() {
        @Override
        public void onNotify(String connectId, String topic, AMessage aMessage) {
            // 云端下行数据回调
            // connectId 连接类型 topic 下行 topic; aMessage 下行数据
            // 数据解析如下：
            //String pushData = new String((byte[]) aMessage.data);
            // pushData 示例  {"method":"thing.service.test_service","id":"123374967","params":{"vv":60},"version":"1.0.0"}
            // method 服务类型； params 下推数据内容
            String jsonMessage = new String((byte[]) aMessage.data);
            AliMessageEntity aliMessageEntity = JSON.parseObject(jsonMessage, AliMessageEntity.class);
            LogUtils.d(String.format("on notify -> connectId: %s , topic: %s , aMessage: %s ", connectId, topic,aliMessageEntity ));
            if (eventListener!=null){
                eventListener.messageResponse(aliMessageEntity);
            }
        }

        @Override
        public boolean shouldHandle(String connectId, String topic) {
            // 选择是否不处理某个 topic 的下行数据
            // 如果不处理某个topic，则onNotify不会收到对应topic的下行数据
            LogUtils.d(String.format("should Handle -> connectId: %s , topic: %s", connectId, topic));
            return true;
        }

        @Override
        public void onConnectStateChange(String connectId, ConnectState connectState) {
            // 对应连接类型的连接状态变化回调，具体连接状态参考 SDK ConnectState
            LogUtils.d(String.format("On Connect State Change -> connectId: %s , ConnectState: %s", connectId, connectState.name()));

        }
    };

    private DeviceInfo deviceInfo;
    private MessageResponseListener eventListener;

    private AliMessageQueueUtils() {
    }

    /**
     * @return 关键对象
     * @hide 对外隐藏调用
     */
    public static AliMessageQueueUtils getInstance() {
        if (instance == null) {
            synchronized (AliMessageQueueUtils.class) {
                if (instance == null) {
                    instance = new AliMessageQueueUtils();
                }
            }
        }
        return instance;
    }

    public AliMessageQueueUtils isDebug(boolean isDebug) {
        if (isDebug) {
            ALog.setLevel(ALog.LEVEL_DEBUG);
        } else {
            ALog.setLevel(ALog.LEVEL_INFO);
        }
        return instance;
    }

    public AliMessageQueueUtils initLog(boolean enableLog) {
        PersistentNet.getInstance().openLog(enableLog);
        return instance;
    }

    /**
     * 三元验证登录
     *
     * @param productKey   产品密钥
     * @param deviceName   设备名称
     * @param deviceSecret 设备秘钥
     * @param initListener 响应回调
     * @return {@link AliMessageQueueUtils}
     */
    public AliMessageQueueUtils initMessageQuery(String productKey, String deviceName, String deviceSecret, ILinkKitConnectListener initListener) {
        setDeviceInfo(productKey, deviceName, deviceSecret);
        Map<String, ValueWrapper> propertyValues = new HashMap<>(2);
        IoTMqttClientConfig clientConfig = new IoTMqttClientConfig(productKey, deviceName, deviceSecret);
        LinkKitInitParams params = new LinkKitInitParams();
        params.deviceInfo = deviceInfo;
        params.propertyValues = propertyValues;
        params.mqttClientConfig = clientConfig;

        MqttConfigure.automaticReconnect = true;
        LinkKit.getInstance().init(App.getAppContext(), params, initListener);

        return instance;
    }

    private void setDeviceInfo(String productKey, String deviceName, String deviceSecret) {
        deviceInfo = new DeviceInfo();
        // 产品类型
        deviceInfo.productKey = productKey;
        // 设备名称
        deviceInfo.deviceName = deviceName;
        // 设备密钥
        deviceInfo.deviceSecret = deviceSecret;
    }

    /**
     * 注册推送连接
     *
     * @return {@link AliMessageQueueUtils}
     */
    public AliMessageQueueUtils registerPushConnect() {
        // 注册下行监听，包括长连接的状态和云端下行的数据
        LinkKit.getInstance().registerOnPushListener(connectListener);
        return instance;
    }

    /**
     * 关闭连接
     */
    public void disConnect() {
        disSubscription();
        LinkKit.getInstance().unRegisterOnPushListener(connectListener);
        LinkKit.getInstance().deinit();
    }

    /**
     * 取消订阅
     *
     * @param productKey key
     * @param deviceName 设备名称
     */
    public void disSubscription(String productKey, String deviceName) {
        // 取消订阅
        MqttSubscribeRequest unSubRequest = new MqttSubscribeRequest();
        // unSubTopic 替换成用户自己需要取消订阅的 topic
        unSubRequest.topic = String.format(TOPIC, productKey, deviceName);
        unSubRequest.isSubscribe = false;
        LinkKit.getInstance().unsubscribe(unSubRequest, new IConnectUnscribeListener() {
            @Override
            public void onSuccess() {
                // 取消订阅成功
                LogUtils.i("MQ 取消订阅成功！");
            }

            @Override
            public void onFailure(AError aError) {
                // 取消订阅失败
                LogUtils.i(String.format("MQ 取消订阅失败！errorInfo: %s ", JSON.toJSONString(aError)));
            }
        });
    }

    public void disSubscription() {
        if (deviceInfo != null) {
            disSubscription(deviceInfo.productKey, deviceInfo.deviceName);
        }
    }

    public AliMessageQueueUtils mqSubscription(){
        if (deviceInfo!=null){
            return mqSubscription(deviceInfo.productKey, deviceInfo.deviceName, new IConnectSubscribeListener() {
                @Override
                public void onSuccess() {
                    LogUtils.i("MQ 订阅成功！");
                }

                @Override
                public void onFailure(AError aError) {
                    LogUtils.i(String.format("MQ 订阅失败！errorInfo: %s ", JSON.toJSONString(aError)));

                }
            });
        }
        return instance;
    }

    /**
     * 订阅mq消息
     *
     * @param productKey               key
     * @param deviceName               设备名称
     * @param connectSubscribeListener 监听器
     * @return {@link AliMessageQueueUtils}
     */
    public AliMessageQueueUtils mqSubscription(String productKey, String deviceName, IConnectSubscribeListener connectSubscribeListener) {
        // 订阅
        MqttSubscribeRequest subscribeRequest = new MqttSubscribeRequest();
        // subTopic 替换成用户自己需要订阅的 topic
        subscribeRequest.topic = String.format(TOPIC, productKey, deviceName);
        subscribeRequest.isSubscribe = true;
        // 支持0或者1
        subscribeRequest.qos = 0;
        LinkKit.getInstance().subscribe(subscribeRequest, connectSubscribeListener);
        return instance;
    }

    public AliMessageQueueUtils setEventListener(MessageResponseListener eventListener){
        this.eventListener = eventListener;
        return instance;
    }

    public interface MessageResponseListener{
        /**
         * 消息响应
         * @param aliMessageEntity 回复的数据
         */
        void messageResponse(AliMessageEntity aliMessageEntity);
    }
}
