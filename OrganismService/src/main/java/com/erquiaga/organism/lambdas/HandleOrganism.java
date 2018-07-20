package com.erquiaga.organism.lambdas;

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

import java.io.*;

import static com.erquiaga.organism.utils.OrganismConstants.*;
import static com.erquiaga.organism.utils.OrganismRequestUtils.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

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
        int responseCode = SC_OK;
        String organismId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                organismId = getParmeterIfExists(pathParameters, ORGANISM_ID_KEY, "");
            }

            if(!"".equals(organismId) && organismExists(organismId)) {
                JSONObject organismJson = getOrganismJson(organismId);

                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", responseCode);
                responseJson.put("body", organismJson.toString());
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", SC_NOT_FOUND);
                responseJson.put("body", "Organism not found!");
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
        logger.log("Updating Organism");

        JSONObject responseJson = new JSONObject();
        JSONParser parser = new JSONParser();
        int responseCode = SC_OK;
        String organismId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                organismId = getParmeterIfExists(pathParameters, ORGANISM_ID_KEY, "");
            }

            JSONObject organismData = null;

            if(jsonEventObject.get("body") != null) {
                organismData = (JSONObject) parser.parse((String) jsonEventObject.get("body"));
            }

            if(!"".equals(organismId) && organismExists(organismId)) {
                if(organismData == null || !isValidOrganismJson(organismData, logger)) {
                    responseJson.put("isBase64Encoded", false);
                    responseJson.put("statusCode", SC_NOT_FOUND);
                    responseJson.put("body", "No Valid Organism Data Provided");
                } else {
                    String organismKey = getOrganismObjectKey(organismId);
                    AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
                    s3Client.deleteObject(ORGANISM_S3_BUCKET, organismKey);
                    AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();

                    StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
                    stepFunctionRequest.setInput(organismData.toJSONString());
                    stepFunctionRequest.setStateMachineArn(SAVE_ORGANISM_STEP_FUNCTION_ARN);

                    StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

                    logger.log(startExecutionResult.toString());

                    String bodyMessage = "Updated Organism at this location - S3:" + ORGANISM_S3_BUCKET + "/" + organismKey;

                    responseJson.put("isBase64Encoded", false);
                    responseJson.put("statusCode", responseCode);
                    responseJson.put("body", bodyMessage);
                }
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", SC_NOT_FOUND);
                responseJson.put("body", "Organism not found!");
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
        logger.log("Deleting Organism");

        JSONObject responseJson = new JSONObject();
        int responseCode = SC_OK;
        String organismId = "";
        try {
            if (jsonEventObject.get("pathParameters") != null) {
                JSONObject pathParameters = (JSONObject)jsonEventObject.get("pathParameters");
                organismId = getParmeterIfExists(pathParameters, ORGANISM_ID_KEY, "");
            }

            if(!"".equals(organismId) && organismExists(organismId)) {
                String organismKey = getOrganismObjectKey(organismId);
                AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
                s3Client.deleteObject(ORGANISM_S3_BUCKET, organismKey);

                String bodyMessage = "Deleted Object at this location - S3:" + ORGANISM_S3_BUCKET + "/" + organismKey;

                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", responseCode);
                responseJson.put("body", bodyMessage);
            } else {
                responseJson.put("isBase64Encoded", false);
                responseJson.put("statusCode", SC_NOT_FOUND);
                responseJson.put("body", "Organism not found!");
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
