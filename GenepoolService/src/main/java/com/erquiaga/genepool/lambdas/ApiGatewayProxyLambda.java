package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Map;

import static com.erquiaga.genepool.utils.GenepoolConstants.GENERIC_RESPONSE_HEADERS;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

public class ApiGatewayProxyLambda implements RequestStreamHandler {
    JSONParser parser = new JSONParser();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
    {
        LambdaLogger logger = context.getLogger();
        logger.log("Handling API Gateway Proxy request");
        InputStream inputStreamCopy = inputStream;

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamCopy));

        JSONObject responseJson = new JSONObject();
        JSONObject headersJson = new JSONObject();

        try {
            JSONObject jsonEventObject = (JSONObject) parser.parse(reader);
            logger.log(jsonEventObject.toJSONString());

            switch ((String) jsonEventObject.get("httpMethod")) {
                case "GET":
                    responseJson = handleGetRequest(jsonEventObject, context);
                    break;
                case "POST":
                    responseJson = handlePostRequest(jsonEventObject, context);
                    break;
                case "DELETE":
                    responseJson = handleDeleteRequest(jsonEventObject, context);
                    break;
                case "PUT":
                    responseJson = handlePutRequest(jsonEventObject, context);
                    break;
                default:
                    responseJson.put("statusCode", SC_BAD_REQUEST);
                    responseJson.put("message", "This method is not supported");
                    break;
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", SC_BAD_REQUEST);
            responseJson.put("exception", e);
        }

        for(Map.Entry<String,String> entry : GENERIC_RESPONSE_HEADERS.entrySet()) {
            headersJson.put(entry.getKey(), entry.getValue());
        }

        responseJson.put("headers", headersJson);


        responseJson.put("isBase64Encoded", false);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toJSONString());
        writer.close();
    }

    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context)
    {
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a get...");
        responseJson.put("statusCode", SC_OK);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    protected JSONObject handlePutRequest(JSONObject jsonEventObject, Context context) {

        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a put...");
        responseJson.put("statusCode", SC_OK);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    protected JSONObject handleDeleteRequest(JSONObject jsonEventObject, Context context) {

        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a delete...");
        responseJson.put("statusCode", SC_OK);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {

        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a post...");
        responseJson.put("statusCode", SC_OK);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }
}
