package com.erquiaga.breeder.utils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.json.simple.JSONObject;

import java.io.IOException;

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

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static String getRequest(String reqUrl) throws IOException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        HttpResponse response = request.execute();
        System.out.println(response.getStatusCode());

        return response.parseAsString();
    }

    public static String postRequest(String reqUrl, String requestBody) throws IOException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
        HttpRequest request = requestFactory.buildPostRequest(url, ByteArrayContent.fromString("application/json", requestBody));
        request.getHeaders().setContentType("application/json");
        request.setConnectTimeout(100000);
        request.setReadTimeout(100000);
        HttpResponse response = request.execute();

        System.out.println(response.getStatusCode());

        return response.parseAsString();
    }
}
