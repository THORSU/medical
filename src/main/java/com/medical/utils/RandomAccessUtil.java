package com.medical.utils;

import java.util.UUID;

public class RandomAccessUtil {
    public static  String getRandom(String type){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return type+uuid;
    }
}
