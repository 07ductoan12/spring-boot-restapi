package com.skyapi.weatherforecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

/**
 * CommonUtility
 */
public class CommonUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARED-FOR");

        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        LOGGER.info("Client's IP Address: " + ip);

        return ip;
    }

}
