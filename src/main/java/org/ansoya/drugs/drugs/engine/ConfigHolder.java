package org.ansoya.drugs.drugs.engine;

import com.google.common.base.Strings;

import java.util.Properties;


public abstract class ConfigHolder {

    private static Properties pro = new Properties();

    public static Properties getProperties() {
        return pro;
    }

    public static void init(Properties properties) {
        pro = properties;
    }

    public static String get(String key) {
        return (String) pro.get(key);
    }

    public static String get(String key, String defaultValue) {
        String value = null;
        if (pro.containsKey(key)) {
            value = (String) pro.get(key);
        }
        if (Strings.isNullOrEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }
}
