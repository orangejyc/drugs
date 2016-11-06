package org.ansoya.drugs.drugs.utils;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2016/11/7.
 */
public abstract class Utils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!Strings.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!Strings.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
// 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
