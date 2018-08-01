package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_FOLDER;
import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_S3_BUCKET;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

public class ListGenepools extends ApiGatewayProxyLambda {
    //Handle GET under /genepool
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Handling GET request");
        JSONObject responseJson = new JSONObject();
        int responseCode = SC_OK;

        try {
            String message = "This should list all genepools.";
            String listPrefix = GENEPOOL_FOLDER;
            JSONObject responseBody = new JSONObject();
            JSONArray genepoolList = new JSONArray();

            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
            ObjectListing genepools = s3Client.listObjects(GENEPOOL_S3_BUCKET, listPrefix);

            for(S3ObjectSummary genepoolObjectSummary : genepools.getObjectSummaries()) {
                String genepoolObjectKey = genepoolObjectSummary.getKey();
                if(!genepoolObjectKey.equals(GENEPOOL_FOLDER)) {
                    genepoolObjectKey = genepoolObjectKey.substring(0, genepoolObjectKey.length() - 5);
                    genepoolObjectKey = genepoolObjectKey.substring(GENEPOOL_FOLDER.length(), genepoolObjectKey.length());

                    genepoolList.add(genepoolObjectKey);
                }
            }

            responseBody.put("genepools", genepoolList);
            responseBody.put("message", message);

            responseJson.put("isBase64Encoded", false);
            responseJson.put("statusCode", responseCode);
            responseJson.put("body", responseBody.toString());

        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", SC_BAD_REQUEST);
            responseJson.put("exception", e);
        }

        return responseJson;
    }
}
