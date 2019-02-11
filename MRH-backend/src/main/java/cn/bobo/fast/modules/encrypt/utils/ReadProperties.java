package cn.bobo.fast.modules.encrypt.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liuyu on 2018/12/27 0027.
 */
@Component
public class ReadProperties {
    public static Properties prop = new Properties();
    public static String postUrl;
    public static String wsUrl;
    public static String passWord;
    public static String certNo;

    @PostConstruct
    public void initProp(){
        try {
            ClassLoader classLoader = ReadProperties.class.getClassLoader();
            InputStream in = classLoader.getResourceAsStream("config.properties");
            prop.load(in);
            in.close();
            postUrl = getValue("PostUrl");
            wsUrl = getValue("WsUrl");
            passWord = getValue("PassWord");
            certNo = getValue("CertNo");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        String value = prop.getProperty(key);
        return value;
    }

}
