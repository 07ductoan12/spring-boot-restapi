package com.toan.weatherforecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtility {
    public static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);

    public static String getIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARED-FOR");

        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        LOGGER.info("Client's Ip Address: " + ip);
        return ip;
    }
}