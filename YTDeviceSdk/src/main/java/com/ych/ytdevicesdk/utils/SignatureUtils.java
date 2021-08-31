package com.ych.ytdevicesdk.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 获取设备签名
 *
 * @author huang
 */
public final class SignatureUtils {

    private static final String SIGNATURE_ALGORITHM = "HmacSHA1";
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 使用 HMAC-SHA1 签名方法对data进行签名
     *
     * @param data 被签名的字符串
     * @param key  密钥
     * @return 加密后的字符串
     */
    public static String getSignature(String data, String key) {
        String result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signatureKey = new SecretKeySpec(key.getBytes(), SIGNATURE_ALGORITHM);
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(SIGNATURE_ALGORITHM);
            //用给定密钥初始化 Mac 对象
            mac.init(signatureKey);
            //完成 Mac 操作
            byte[] rawSignature = mac.doFinal(data.getBytes());
            result = bytes2HexString(rawSignature).toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }
}