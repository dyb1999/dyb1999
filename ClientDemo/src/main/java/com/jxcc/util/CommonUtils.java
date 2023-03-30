package com.jxcc.util;

import java.util.*;

/**
 * @Author dingyb
 * @Date 2023/3/30
 * @Description 常用集合转换工具类
 */
public class CommonUtils {
    public CommonUtils() {
    }

    public static boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) > 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) > 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() > 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() > 0;
        } else if (object instanceof Boolean) {
            return Boolean.valueOf(object.toString());
        } else if (object instanceof List) {
            return ((List)object).size() > 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() > 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() > 0;
        } else if (object instanceof Iterator) {
            return ((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() > 0;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof Integer) {
            return Integer.valueOf(object.toString()) == 0;
        } else if (object instanceof Long) {
            return Long.valueOf(object.toString()) == 0L;
        } else if (object instanceof String) {
            return ((String)object).trim().length() == 0;
        } else if (object instanceof StringBuffer) {
            return ((StringBuffer)object).toString().trim().length() == 0;
        } else if (object instanceof Boolean) {
            return Boolean.valueOf(object.toString());
        } else if (object instanceof List) {
            return ((List)object).size() == 0;
        } else if (object instanceof Set) {
            return ((Set)object).size() == 0;
        } else if (object instanceof Map) {
            return ((Map)object).size() == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator)object).hasNext();
        } else if (object.getClass().isArray()) {
            return Arrays.asList(object).size() == 0;
        } else {
            return false;
        }
    }
}
