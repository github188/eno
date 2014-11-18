package com.energicube.eno.fssm.util;


import com.energicube.eno.common.PropertyUtil;

/**
 * 广场信息工具
 * 获取广场基本信息
 *
 * @author CHENPING
 */
public class PlazainfoUtil {

    private static final String filename = "activemq.properties";
    private static final String PLAZAINFO_BUSINESSTYPE = "plazainfo.businesstype";
    private static final String PLAZAINFO_SYSCODE = "plazainfo.syscode";
    private static final String PLAZAINFO_MANAGER = "plazainfo.manager";
    private static final String PLAZAINFO_PHONE = "plazainfo.phone";

    private static String filePath = "";

    private static PlazainfoUtil instance = null;

    private PlazainfoUtil() {
        filePath = Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "properties/" + filename;
    }

    public static PlazainfoUtil getInstance() {
        if (instance == null) {
            instance = new PlazainfoUtil();
        }
        return instance;
    }

    public String getPlazaSyscode() {
        return PropertyUtil.getPropValue(filePath, PLAZAINFO_SYSCODE);
    }

    public String getPlazaManager() {
        return PropertyUtil.getPropValue(filePath, PLAZAINFO_MANAGER);
    }

    public String getPlazaPhone() {
        return PropertyUtil.getPropValue(filePath, PLAZAINFO_PHONE);
    }

    public String getBusinessType() {
        return PropertyUtil.getPropValue(filePath, PLAZAINFO_BUSINESSTYPE);
    }


}
