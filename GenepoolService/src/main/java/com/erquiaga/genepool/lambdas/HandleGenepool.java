package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_ID_KEY;
import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_S3_BUCKET;
import static com.erquiaga.genepool.utils.GenepoolConstants.SAVE_GENEPOOL_STEP_FUNCTION_ARN;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class HandleGenepool extends ApiGatewayProxyLambda {

    //Handle everything under /genepool/{id}
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Getting Genepool");

        JSONObject responseJson = new JSONObject();
        int responseCode = SC_OK;
        String genepoolId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                genepoolId = getParmeterIfExists(pathParameters, GENEPOOL_ID_KEY, "");
            }

            if(!"".equals(genepoolId) && genepoolExists(genepoolId)) {
                JSONObject genepoolJson = getGenepoolJson(genepoolId);

                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", responseCode);
                responseJson.put("body", genepoolJson.toString());
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", SC_NOT_FOUND);
                responseJson.put("body", "Genepool not found!");
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", SC_BAD_REQUEST);
            responseJson.put("exception", e);
        }

        return responseJson;
    }

    @Override
    protected JSONObject handlePutRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Updating Genepool");

        JSONObject responseJson = new JSONObject();
        JSONParser parser = new JSONParser();
        int responseCode = SC_OK;
        String genepoolId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                genepoolId = getParmeterIfExists(pathParameters, GENEPOOL_ID_KEY, "");
            }

            JSONObject genepoolData = null;

            if(jsonEventObject.get("body") != null) {
                genepoolData = (JSONObject) parser.parse((String) jsonEventObject.get("body"));
            }

            if(!"".equals(genepoolId) && genepoolExists(genepoolId)) {
                if(genepoolData == null || !isValidGenepoolJson(genepoolData, logger)) {
                    responseJson.put("isBase64Encoded", false);
                    responseJson.put("statusCode", SC_NOT_FOUND);
                    responseJson.put("body", "No Valid Genepool Data Provided");
                } else {
                    String genepoolObjectKey = getGenepoolObjectKey(genepoolId);
                    AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
                    s3Client.deleteObject(GENEPOOL_S3_BUCKET, genepoolObjectKey);
                    AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();

                    StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
                    stepFunctionRequest.setInput(genepoolData.toJSONString());
                    stepFunctionRequest.setStateMachineArn(SAVE_GENEPOOL_STEP_FUNCTION_ARN);

                    StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

                    logger.log(startExecutionResult.toString());

                    String bodyMessage = "Updated Genepool at this location - S3:" + GENEPOOL_S3_BUCKET + "/" + genepoolObjectKey;

                    responseJson.put("isBase64Encoded", false);
                    responseJson.put("statusCode", responseCode);
                    responseJson.put("body", bodyMessage);
                }
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", SC_NOT_FOUND);
                responseJson.put("body", "Genepool not found!");
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", SC_BAD_REQUEST);
            responseJson.put("exception", e);
        }

        return responseJson;
    }

    @Override
    protected JSONObject handleDeleteRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Deleting Genepool");

        JSONObject responseJson = new JSONObject();
        int responseCode = SC_OK;
        String genepoolId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                genepoolId = getParmeterIfExists(pathParameters, GENEPOOL_ID_KEY, "");
            }

            if(!"".equals(genepoolId) && genepoolExists(genepoolId)) {
                String genepoolObjectKey = getGenepoolObjectKey(genepoolId);
                AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
                s3Client.deleteObject(GENEPOOL_S3_BUCKET, genepoolObjectKey);

                String bodyMessage = "Deleted Object at this location - S3:" + GENEPOOL_S3_BUCKET + "/" + genepoolObjectKey;

                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", responseCode);
                responseJson.put("body", bodyMessage);
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", SC_NOT_FOUND);
                responseJson.put("body", "Genepool not found!");
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", SC_BAD_REQUEST);
            responseJson.put("exception", e);
        }

        return responseJson;
    }

    @Override
    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", "This method isn't supported");
        responseJson.put("statusCode", SC_OK);
        responseJson.put("body", responseBody.toString());

        return responseJson;
    }
}
