package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederConstants.ORGANISM_FILE_SUFFIX;
import static com.erquiaga.breeder.utils.BreederUtils.getParmeterIfExists;

public class HandleOrganism extends ApiGatewayProxyLambda {

    //Handle everything under /organism/{id}
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Getting Organism");

        JSONObject responseJson = new JSONObject();
        String responseCode = "200";
        String organismId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                organismId = getParmeterIfExists(pathParameters, ORGANISM_ID_KEY, "");
            }

            if(!"".equals(organismId)) {
                String organismKey = ORGANISM_FOLDER + organismId + ORGANISM_FILE_SUFFIX;

                AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
                S3Object fetchedOrganism = s3Client.getObject(BREEDER_S3_BUCKET, organismKey);
                InputStream organismDataStream = fetchedOrganism.getObjectContent();
                JSONParser jsonParser = new JSONParser();
                JSONObject organismJson = (JSONObject)jsonParser.parse(
                        new InputStreamReader(organismDataStream, "UTF-8"));

                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", responseCode);
                responseJson.put("body", organismJson.toString());
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", "404");
                responseJson.put("body", "Organism not found!");
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", "400");
            responseJson.put("exception", e);
        }

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
