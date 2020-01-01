package com.liachen.common.util;

import java.util.HashMap;
import java.util.Map;

public class ToolsUtil {
    public static Map<String, String> getCookie(String cookieStr) {
        String[] cookieStrs = cookieStr.split(";");
        Map<String, String> cookieMap = new HashMap<>();
        for (String cookie : cookieStrs) {
            cookieMap.put(cookie.split("=")[0], cookie.split("=")[1]);
        }
        return cookieMap;
    }
}
