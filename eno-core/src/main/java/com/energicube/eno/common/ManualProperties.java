package com.energicube.eno.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-11-2
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class ManualProperties extends Properties {

    private static Log logger = LogFactory.getLog(ManualProperties.class);

    private static ManualProperties uniqueInstance = null;

    public synchronized static ManualProperties getInstance() {

        if (uniqueInstance == null) {
            uniqueInstance = new ManualProperties();
        }
        return uniqueInstance;

    }

    private ManualProperties() {
        // 加载属性文件
        try {
            load(this.getClass().getClassLoader().getResourceAsStream("manual.properties"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void print(String filePath) {
        Properties props = achieveProperties(filePath);
        Map orderedProp = new TreeMap(props);
        Iterator itr = orderedProp.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            logger.debug(entry.getKey() + "=" + entry.getValue());
        }
    }

    /**
     * 通过key获取值
     *
     * @param filePath 文件路径
     * @param key
     * @return
     */
    public static String getValue(String filePath, String key) {
        try {
            Properties prop = achieveProperties(filePath);
            return prop.getProperty(key);

        } catch (Exception e) {
            logger.error("----", e);
        }
        return "";
    }

    /**
     * 保存值
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        try {
            String path = this.getClass().getClassLoader().getResource("/").getPath();
            OutputStream os = new FileOutputStream(path + "/manual.properties");
            this.setProperty(key, value);
            this.store(os, "Hello World");
            logger.debug("已保存数据到" + path);
            os.close();
        } catch (FileNotFoundException e) {
            logger.error("----", e);
        } catch (IOException e) {
            logger.error("----", e);
        }
    }


    /**
     * 读取文件
     *
     * @param filePath
     * @return
     */
    private static File achieveFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                logger.debug("文件" + filePath + "不存在，创建新文件");
            } catch (IOException e) {
                logger.error("----", e);
            }
        } else {
            logger.debug("成功获取文件" + filePath);
        }
        return file;
    }

    /**
     * 更新保存文件
     *
     * @param filePath  文件路径
     * @param paraName  key名
     * @param paraValue 值
     */
    public static void saveOrUpdate(String filePath, String paraName, String paraValue) {
        Properties prop = achieveProperties(filePath);
        try {
            OutputStream os = new FileOutputStream(filePath);
            prop.setProperty(paraName, paraValue);
            prop.store(os, "Hello World");
            logger.debug("已保存数据到" + filePath);
            os.close();
        } catch (FileNotFoundException e) {
            logger.error("----", e);
        } catch (IOException e) {
            logger.error("----", e);
        }
    }

    /**
     * 读取properties文件
     *
     * @param filePath
     * @return
     */
    private static Properties achieveProperties(String filePath) {
        File file = achieveFile(filePath);
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(file);
            prop.load(is);
            logger.debug("已获取" + filePath + "文件");
            is.close();
        } catch (FileNotFoundException e) {
            logger.error("----", e);
        } catch (IOException e) {
            logger.error("----", e);
        }
        return prop;
    }

    /**
     * 另保存一份文件
     *
     * @param baseFilePath
     * @param desFilePath
     */
    public static void copy(String baseFilePath, String desFilePath) {
        Properties baseProp = achieveProperties(baseFilePath);
        Properties desProp = achieveProperties(desFilePath);
        try {
            OutputStream os = new FileOutputStream(desFilePath);
            Map orderedProp = new TreeMap(baseProp);
            Iterator itr = orderedProp.entrySet().iterator();
            int number = 0;
            while (itr.hasNext()) {
                Map.Entry entry = (Map.Entry) itr.next();
                desProp.setProperty((String) entry.getKey(), (String) entry.getValue());
                logger.debug(entry.getKey() + "=" + entry.getValue());
                number++;
            }
            logger.debug("共拷贝" + number + "条数据");
            desProp.store(os, "Hello World");
            logger.debug("从" + baseFilePath + "到" + desFilePath + "的数据复制完毕");
            os.close();
        } catch (FileNotFoundException e) {
            logger.error("----", e);
        } catch (IOException e) {
            logger.error("----", e);
        }
    }

    /**
     * 打印KEY及值
     *
     * @param filePath
     * @param key
     */
    public static void print(String filePath, String key) {
        Properties prop = achieveProperties(filePath);
        Map orderedProp = new TreeMap(prop);
        if (orderedProp.containsKey(key)) {
            logger.debug(key + "=" + orderedProp.get(key));
        } else {
            logger.debug("key为" + key + "的值不存在");
        }
    }

    /**
     * 删除某key
     *
     * @param filePath
     * @param key
     */
    public static void delete(String filePath, String key) {
        Properties prop = achieveProperties(filePath);
        try {
            OutputStream os = new FileOutputStream(filePath);
            prop.remove(key);
            prop.store(os, "Hello World");
            logger.debug("已保存数据到" + filePath);
            os.close();
        } catch (FileNotFoundException e) {
            logger.error("----", e);
        } catch (IOException e) {
            logger.error("----", e);
        }
    }

}
