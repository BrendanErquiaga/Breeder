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

        JSONObject responseJson = new JSONObject();
        String responseCode = "200";

        int parentOneId = 0;
        int parentTwoId = 1;
        String organismType = "-";

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            JSONObject event = (JSONObject)parser.parse(reader);
            logger.log(event.toJSONString());
            if (event.get("queryStringParameters") != null) {
                JSONObject queryStringParameters = (JSONObject)event.get("queryStringParameters");

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
                    + parentOneId + " + " + parentTwoId + " = ???. Type: " + organismType;

            JSONObject responseBody = new JSONObject();
            responseBody.put("input", event.toJSONString());
            responseBody.put("message", breedingMessage);

            responseJson.put("isBase64Encoded", false);
            responseJson.put("statusCode", responseCode);
            responseJson.put("body", responseBody.toString());

        } catch (ParseException pex) {
            responseJson.put("statusCode", "400");
            responseJson.put("exception", pex);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toJSONString());
        writer.close();
    }
}
