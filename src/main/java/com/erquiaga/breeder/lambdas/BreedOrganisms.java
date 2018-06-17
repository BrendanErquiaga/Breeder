package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.breeder.utils.BreederConstants.ORGANISM_TYPE_KEY;
import static com.erquiaga.breeder.utils.BreederUtils.getParmeterIfExists;

public class BreedOrganisms extends ApiGatewayProxyLambda {

    protected final static String PARENT_ONE_ID_KEY = "parentOneId";
    protected final static String PARENT_TWO_ID_KEY = "parentTwoId";

    //Handle everything under /breeding/breed
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    public JSONObject handlePostRequest(JSONObject jsonEventObject, Context context)
    {
        LambdaLogger logger = context.getLogger();
        logger.log("Handling POST request");

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
}
