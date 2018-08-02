package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getNextOrganismId;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getParmeterIfExists;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

public class BreedOrganisms extends ApiGatewayProxyLambda {

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

        try {
            if (jsonEventObject.get("queryStringParameters") != null) {
                JSONObject queryStringParameters = (JSONObject)jsonEventObject.get("queryStringParameters");

                String parentOneId = getParmeterIfExists(queryStringParameters, PARENT_ONE_ID_KEY,"-1");
                String parentTwoId = getParmeterIfExists(queryStringParameters, PARENT_TWO_ID_KEY, "-1");
                String childCountString = getParmeterIfExists(queryStringParameters, CHILD_COUNT_KEY, "1");
                String childId = getParmeterIfExists(queryStringParameters, CHILD_ID_KEY, "-1");

                String breedingMessage = "";

                int childCount = Integer.parseInt(childCountString);

                if(parentOneId != "-1" && parentTwoId != "-1") {

                    if("-1".equals(childId)) {
                        breedingMessage = breedMultipleChildren(parentOneId, parentTwoId, childCount, logger);
                    } else {
                        breedingMessage = "Breeding should produce one child with this id: " + breedOrganisms(parentOneId, parentTwoId, childId, logger);
                    }

                    logger.log(breedingMessage);
                    responseJson.put("statusCode", SC_OK);
                } else {
                    breedingMessage = "One parentID missing, cannot breed.";
                    responseJson.put("statusCode", SC_BAD_REQUEST);
                }

                JSONObject responseBody = new JSONObject();
                responseBody.put("message", breedingMessage);
                responseJson.put("body", responseBody.toString());
                logger.log("Should be done with request.");
            } else {
                throw new Exception("Missing query params.");
            }
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", SC_BAD_REQUEST);
            responseJson.put("exception", e);
        }

        return responseJson;
    }

    private String breedMultipleChildren(String parentOneId, String parentTwoId, int childCount, LambdaLogger logger) {
        StringBuilder breedingMessage = new StringBuilder();
        breedingMessage.append("Breeding should produce children with these IDs [");
        logger.log("Should be breeding this many children: " + childCount);
        String[] childIDs = new String[childCount];

        for(int i = 0; i < childCount; i++) {
            childIDs[i] = breedOrganisms(parentOneId, parentTwoId, getNextOrganismId(), logger);
            breedingMessage.append(childIDs[i]).append(',');
        }

        breedingMessage.append(']');

        return breedingMessage.toString();
    }

    private String breedOrganisms(String parentOneId, String parentTwoId, String newChildId, LambdaLogger logger) {
        logger.log("Kicking off breeding for this new organism: " + newChildId);

        String kickOffResponse = kickOffBreedingProcessStepFunction(parentOneId, parentTwoId, newChildId);
        logger.log(kickOffResponse);

        return newChildId;
    }

    private String kickOffBreedingProcessStepFunction(String parentOneId, String parentTwoId, String childId) {
        JSONObject breedingJson = new JSONObject();
        breedingJson.put(PARENT_ONE_ID_KEY, parentOneId);
        breedingJson.put(PARENT_TWO_ID_KEY, parentTwoId);
        breedingJson.put(CHILD_ORGANISM_ID_KEY, childId);

        AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();

        StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
        stepFunctionRequest.setInput(breedingJson.toJSONString());
        stepFunctionRequest.setStateMachineArn(BREED_ORGANISMS_STEP_FUNCTION_ARN);

        StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

        return startExecutionResult.toString();
    }
}
