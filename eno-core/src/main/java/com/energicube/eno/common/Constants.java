package com.energicube.eno.common;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;

public class Constants {

    public final static String STATISTICSPANELTYPE = "98"; // 统计面板类型

    public final static String EXTENDPANELTYPE = "99"; // 扩展面板类型

    /**
     * 对密码进行加密
     *
     * @param password 原密码
     * @return 加密后的密码
     */
    public static String getMdPassword(String password) {
        DefaultHashService hashService = new DefaultHashService(); //默认算法 SHA-512
        hashService.setHashAlgorithmName("SHA-512");
//		hashService.setPrivateSalt(new SimpleByteSource("zclf"));//私盐，默认无
//		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
//		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(2);//生成 Hash 值的迭代次数
        HashRequest request = new HashRequest.Builder().setSource(ByteSource.Util.bytes(password)).build();
        String hex = hashService.computeHash(request).toHex();
        return hex;
    }
}