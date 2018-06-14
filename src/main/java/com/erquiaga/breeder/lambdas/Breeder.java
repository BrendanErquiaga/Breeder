package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

import static com.erquiaga.breeder.utils.BreederUtils.getParmeterIfExists;
import static com.erquiaga.breeder.utils.BreederConstants.*;

public class Breeder implements RequestStreamHandler {
    JSONParser parser = new JSONParser();
    protected final static String PARENT_ONE_ID_KEY = "parentOneId";
    protected final static String PARENT_TWO_ID_KEY = "parentTwoId";

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
    {
        LambdaLogger logger = context.getLogger();
        logger.log("Handling API Gateway Proxy request");
        InputStream inputStreamCopy = inputStream;

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamCopy));

        JSONObject responseJson = new JSONObject();

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
                    responseJson.put("statusCode", "400");
                    responseJson.put("message", "This method is not supported");
                    break;
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", "400");
            responseJson.put("exception", e);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toJSONString());
        writer.close();
    }

    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context)
    {
        LambdaLogger logger = context.getLogger();
        logger.log("Handling GET request");

        JSONObject responseJson = new JSONObject();
        String responseCode = "200";

        int parentOneId = 0;
        int parentTwoId = 1;
        String organismType = "-";

        try {
            if (jsonEventObject.get("queryStringParameters") != null) {
                JSONObject queryStringParameters = (JSONObject)jsonEventObject.get("queryStringParameters");

                parentOneId = Integer.parseInt(getParmeterIfExists(queryStringParameters, PARENT_ONE_ID_KEY,"0"));
                parentTwoId = Integer.parseInt(getParmeterIfExists(queryStringParameters, PARENT_TWO_ID_KEY, "0"));
                organismType = getParmeterIfExists(queryStringParameters, ORGANISM_TYPE_KEY, "");
            }
//            if (event.get("pathParameters") != null) {
//                JSONObject pps = (JSONObject)event.get("pathParameters");
//                if ( pps.get("proxy") != null) {
//                    city = (String)pps.get("proxy");
//                }
//            }
//            if (event.get("headers") != null) {
//                JSONObject hps = (JSONObject)event.get("headers");
//                if ( hps.get("day") != null) {
//                    day = (String)hps.get("day");
//                }
//            }
//            if (event.get("body") != null) {
//                JSONObject body = (JSONObject)parser.parse((String)event.get("body"));
//                if ( body.get("time") != null) {
//                    time = (String)body.get("time");
//                }
//            }
            String breedingMessage = "This should breed these things: "
                    + parentOneId + " + " + parentTwoId + " = profit. Type: " + organismType;

            JSONObject responseBody = new JSONObject();
            responseBody.put("message", breedingMessage);

            responseJson.put("isBase64Encoded", false);
            responseJson.put("statusCode", responseCode);
            responseJson.put("body", responseBody.toString());

        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", "400");
            responseJson.put("exception", e);
        }

        return responseJson;
    }

    protected JSONObject handlePutRequest(JSONObject jsonEventObject, Context context) {

        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a put...");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    protected JSONObject handleDeleteRequest(JSONObject jsonEventObject, Context context) {

        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a delete...");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }

    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {

        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This is a post...");
        responseJson.put("statusCode", 200);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }
}
