package com.metasploit.msf.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class RpcObject {
    public String id;
//    public static Map<String, Object> getMap(RpcObject o) throws IllegalArgumentException, IllegalAccessException {
//        Map<String, Object> result = new HashMap<String, Object>();
//        Field[] declaredFields = o.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            result.put(field.getName(), field.get(o));
//        }
//        return result;
//    }
}
