package com.sudo248.orderservice.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}
