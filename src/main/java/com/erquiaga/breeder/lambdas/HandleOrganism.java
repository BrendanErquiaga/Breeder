package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HandleOrganism extends ApiGatewayProxyLambda {

    //Handle everything under /organism/{id}
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context) {
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "THIS SHOULD GET AN ORGANISM.");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    @Override
    protected JSONObject handlePutRequest(JSONObject jsonEventObject, Context context) {
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This should update an organism");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    @Override
    protected JSONObject handleDeleteRequest(JSONObject jsonEventObject, Context context) {
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This should delete an organism");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    @Override
    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This method isn't supported");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }
}
