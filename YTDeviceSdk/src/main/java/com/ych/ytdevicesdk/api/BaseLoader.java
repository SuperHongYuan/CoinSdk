package com.ych.ytdevicesdk.api;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.ych.ytdevicesdk.config.Constants;
import com.ych.ytdevicesdk.utils.SignatureUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * 默认的加载器
 * @author huang
 */
public abstract class BaseLoader {


    public boolean useTestUrl = false;

    /**
     * 添加参数 ， 各个接口可以自行实现，如果要加入统一签名可以再这里加入
     *
     * @param map
     * @return
     */
    public Map<String, Object> getParameter(Map<String, Object> map) {
        return map;
    }


    /**
     * 签名放在头里面
     *
     * @param map
     * @return
     */
    public Map<String, Object> getHeaderRegister(Map<String, Object> map) {
        return getHeader(map, Constants.DEFAULT_SIGN_KEY);
    }


    /**
     * 签名放在头里面
     *
     * @param map
     * @return
     */
    public Map<String, Object> getHeaderYZHRegister(Map<String, Object> map) {
        return getYZYHeader(map, Constants.DEFAULT_SIGN_KEY);
    }

    /**
     * 签名放在头里面
     *
     * @param map
     * @return
     */
    public Map<String, Object> getHeader(Map<String, Object> map) {
        return getHeader(map, Constants.SIGN_KEY);
    }


    /**
     * 签名放在头里面
     *
     * @param map
     * @return
     */
    public Map<String, Object> getYZYHeader(Map<String, Object> map) {
        return getYZYHeader(map, Constants.SIGN_KEY);
    }

    /**
     * token放在头里面
     *
     * @param token
     * @return
     */
    public Map<String, Object> getHeaderToken(String token) {
        Map<String, Object> headerMap = new TreeMap<>();
        headerMap.put("Authorization", token);
        return headerMap;
    }

    /**
     * token放在头里面
     * @return
     */
    public Map<String, Object> getHeaderToken() {
        Map<String, Object> headerMap = new TreeMap<>();
        headerMap.put("Authorization", Constants.Token);
        return headerMap;
    }

    private Map<String, Object> getHeader(@NonNull Map<String, Object> map, @NonNull String key) {
        StringBuffer sige = new StringBuffer();
        Map<String, Object> headerMapTyep = new TreeMap<>();
        for (String s : map.keySet()) {
            headerMapTyep.put(s.toLowerCase(), map.get(s));
        }
        LogUtils.d("OkHttp: " + map);
        Iterator<Map.Entry<String, Object>> iterator = headerMapTyep.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sige.append(entry.getKey().toLowerCase()).append("=").append(entry.getValue());
            if (iterator.hasNext()) {
                sige.append("&");
            }
        }
        LogUtils.d("OkHttp: " + sige.toString());
        Map<String, Object> headerMap = new TreeMap<>();
        String genHMAC = SignatureUtils.getSignature(sige.toString(), key);
        if (genHMAC != null) {
            headerMap.put("Authorization", genHMAC);
        }
        return headerMap;
    }

    private Map<String, Object> getYZYHeader(@NonNull Map<String, Object> map, @NonNull String key){
        Map<String, Object> headerMap = new TreeMap<>();
        String genHMAC = SignatureUtils.getSignature(JSON.toJSONString(map), key);
        if (genHMAC != null) {
            headerMap.put("Authorization", genHMAC);
        }
        return headerMap;
    }

    /**
     * 设置是否是debug模式
     * @param useTestUrl
     */
    public void setIsDebug(boolean useTestUrl){
        this.useTestUrl = useTestUrl;
    }
}