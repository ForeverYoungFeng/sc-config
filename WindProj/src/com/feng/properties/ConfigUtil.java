package com.feng.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	private static final Properties pp=new Properties();
    static{
        try {
            InputStream input= ConfigUtil.class.getResourceAsStream("config.properties");
            pp.load(input);
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }
    public static String getProperties(String key){
    	return pp.getProperty(key);
	    /*InputStream fis = new FileInputStream(file);  
		prop.load(fis);  
		//一定要在修改值之前关闭fis  
		fis.close();  
		OutputStream fos = new FileOutputStream(filePath);  
		 prop.setProperty(key, value);  
		 //保存，并加入注释  
		 prop.store(fos, "Update '" + key + "' value");  
		 fos.close();  */
    }
    
    public static String getProp(String key) {
    	String prop = "";
    	Properties p = new Properties();
    	try {
    		p.load(ConfigUtil.class.getResourceAsStream("/com/feng/javaee/jdbc/config.properties"));
    		prop = p.getProperty(key.toLowerCase());
    		/*Set<Object> keys = p.keySet();
            for (Object object : keys) {
                String key = object.toString().toLowerCase();
                String val = p.getProperty(key);
                map.put(key, val);
            }*/
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return prop;
    }
}
