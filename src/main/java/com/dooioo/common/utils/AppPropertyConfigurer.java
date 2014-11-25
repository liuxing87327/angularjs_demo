package com.dooioo.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.Log4jConfigurer;
import java.io.IOException;
import java.util.Properties;

/**
 * 应用的配置文件
 * User: kqy
 * Date: 11-5-23 下午2:58
 */
public class AppPropertyConfigurer extends PropertyPlaceholderConfigurer {
    private static final Log log = LogFactory.getLog(AppPropertyConfigurer.class);

    @Override
    protected Properties mergeProperties() throws IOException {
    	Properties superProps = super.mergeProperties();

        if (IpUtils.isWinOS() || IpUtils.isMacOS()) {
            superProps.setProperty("env", "local");
        } else {
            superProps.setProperty("env", "bae");
        }

        try {
            String resourcedDir = StringUtils.substringBeforeLast(this.getClass().getResource("").getPath(), "com/").replaceAll("%20", " ");
            Log4jConfigurer.initLogging(resourcedDir + superProps.getProperty("env") + "/log4j.properties");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info(">>>>> env: " + superProps.getProperty("env"));
        log.info(">>>>> osName: " + IpUtils.getOsName());
        log.info(">>>>> IP: " + IpUtils.getLocalIP());
        return superProps;
    }
}
