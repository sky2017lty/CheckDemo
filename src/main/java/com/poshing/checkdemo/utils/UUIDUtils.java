package com.poshing.checkdemo.utils;

import java.util.UUID;

public final class UUIDUtils {
    private UUIDUtils(){}
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
