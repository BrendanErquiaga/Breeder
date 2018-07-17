package com.erquiaga.breeder.utils;

import org.json.simple.JSONObject;

public class BreederRequestUtils {
    public static String getParmeterIfExists(JSONObject jsonObject, String parameterKey, String defaultValue)
    {
        if (jsonObject.get(parameterKey) != null) {
            return (String)jsonObject.get(parameterKey);
        }

        return defaultValue;
    }

    public static String getNextOrganismId() {
        //TODO Write a better UUID system
        return Long.toString(System.currentTimeMillis());
    }
}
