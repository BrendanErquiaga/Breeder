package com.erquiaga.breeder.utils;

import org.json.simple.JSONObject;

public class BreederUtils {

    public static String getParmeterIfExists(JSONObject jsonObject, String parameterKey, String defaultValue)
    {
        if (jsonObject.get(parameterKey) != null) {
            return (String)jsonObject.get(parameterKey);
        }

        return defaultValue;
    }
}
