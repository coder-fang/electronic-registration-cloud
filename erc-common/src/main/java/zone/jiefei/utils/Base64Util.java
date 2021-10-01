package zone.jiefei.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;

import java.util.Random;

/**
 * @ClassName Base64Util
 * @Description Base64工具类
 * @Author zhangpeitao
 * @Date 2020/7/3117:27
 * @VERSION 1.0
 */
public class Base64Util {
    /**
     * fetch
     *
     * @param name 传入的需要加密的字符串
     * @return java.lang.String
     * @Author zhangpeitao
     * @Description Base64加密
     * @Data 17:29 2020/7/31
     */
    public static String encode( String name ) {
        String encode = Base64.encode(name);
        StringBuilder sb = new StringBuilder();
        sb.append(encode).insert(4, getRandomString(1));
        return sb.toString();
    }

    /**
     * fetch
     *
     * @param encode 需要解密的数据
     * @return java.lang.String
     * @Author zhangpeitao
     * @Description Base64解密
     * @Data 17:34 2020/7/31
     */
    public static String decode( String encode ) {
        String encodeDelete = StrUtil.sub(encode, 0, 4) + StrUtil.sub(encode, 5, encode.length());
        return Base64.decodeStr(encodeDelete);
    }

    /**
     * fetch
     * @Author zhangpeitao
     * @Description length用户要求产生字符串的长度
     * @Data 11:47 2020/8/2
     * @param length 生成的长度
     * @return java.lang.String
     */
    public static String getRandomString( int length ) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
