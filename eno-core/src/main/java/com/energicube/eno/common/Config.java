package com.energicube.eno.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-6-26
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private static final Log logger = LogFactory.getLog(Config.class);
    private Properties props = new Properties();

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public Config() {
        // 加载属性文件
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream("./properties/config.properties"));

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
