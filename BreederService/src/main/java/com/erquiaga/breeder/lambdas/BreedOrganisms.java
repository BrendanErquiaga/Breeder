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
        String responseCode = "200";

        String parentOneId = "-1";
        String parentTwoId = "-1";
        //String organismType = "-";

        try {
            if (jsonEventObject.get("queryStringParameters") != null) {
                JSONObject queryStringParameters = (JSONObject)jsonEventObject.get("queryStringParameters");

                parentOneId = getParmeterIfExists(queryStringParameters, PARENT_ONE_ID_KEY,"-1");
                parentTwoId = getParmeterIfExists(queryStringParameters, PARENT_TWO_ID_KEY, "-1");
                //organismType = getParmeterIfExists(queryStringParameters, ORGANISM_TYPE_KEY, "-");
            }

            String breedingMessage = "";
            String newChildOrganismId = "";

            if(parentOneId != "-1" && parentTwoId != "-1") {
                newChildOrganismId = getNextOrganismId();//TODO Change this so Breeder Service calls Organism Service (or something similar)
                breedingMessage = "Attempting to create new organism with this formula - " +
                        "P1: " + parentOneId + " + P2: " + parentTwoId + " = " + newChildOrganismId;
                responseJson.put("statusCode", responseCode);

                String kickOffResponse = kickOffBreedingProcessStepFunction(parentOneId, parentTwoId, newChildOrganismId);
                logger.log(kickOffResponse);
            } else {
                breedingMessage = "One parentID missing, cannot breed.";
                responseJson.put("statusCode", "400");
            }

            JSONObject responseBody = new JSONObject();
            responseBody.put("message", breedingMessage);
            responseJson.put("isBase64Encoded", false);
            responseJson.put("body", responseBody.toString());
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            responseJson.put("statusCode", "400");
            responseJson.put("exception", e);
        }

        return responseJson;
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
