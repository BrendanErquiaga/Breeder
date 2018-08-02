package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.erquiaga.genepool.models.Genepool;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.erquiaga.genepool.lambdas.CreateGenepool.kickOffSaveGenepoolStepFunction;
import static com.erquiaga.genepool.utils.GenepoolConstants.*;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getGenepoolIfExists;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getNextGenepoolId;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getPathParameter;
import static org.apache.http.HttpStatus.*;

public class BreedGenepool extends ApiGatewayProxyLambda {

    //Handle everything under /genepool/{id}/breed
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Attempting to Breed Genepool");

        JSONObject responseJson = new JSONObject();

        try {
            String genepoolId = "";

            if (jsonEventObject.get(EVENT_PATH_PARAMETERS_KEY) != null) {
                genepoolId = getPathParameter(jsonEventObject, GENEPOOL_ID_KEY);
            }

            Genepool genepool = getGenepoolIfExists(genepoolId, logger);

            //TODO: If Genepool.size > 100, do this asyncronously

            if(genepool != null) {

                if(genepool.getOrganismsInGenepool().size() >= LARGE_GENEPOOL_SIZE) {
                    StartExecutionResult startBreedLargeGenepoolResult = kickoffBreedLargeGenepoolStepFunction(genepool);
                    logger.log(startBreedLargeGenepoolResult.toString());

                    responseJson.put("statusCode", SC_CREATED);
                    responseJson.put("body", "Genepool was very large, breeding has been started asyncronously, please check back later to see if it is complete.");
                } else {
                    JSONObject breedGenepoolJSON = breedGenepool(genepool, logger);

                    responseJson.put("statusCode", SC_OK);
                    responseJson.put("body", breedGenepoolJSON.toJSONString());
                }
            } else {
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

    public static JSONObject breedGenepool(Genepool genepool, LambdaLogger logger) {
        JSONObject breedGenepoolJSON = new JSONObject();

        List<BreedingPair> breedingPairs = buildBreedingPairs(new ArrayList<>(genepool.getOrganismsInGenepool()));
        JSONArray breedingPairsJson = kickoffBreedingPairs(breedingPairs, logger);
        JSONArray newChildrenIds = addNewChildrenToGenepool(genepool, breedingPairsJson, logger);

        breedGenepoolJSON.put("message", "Successfully bred Genepool.");
        breedGenepoolJSON.put("breedingPairs", breedingPairsJson.toJSONString());
        breedGenepoolJSON.put("newChildrenIds", newChildrenIds.toJSONString());

        return breedGenepoolJSON;
    }

    private static JSONArray addNewChildrenToGenepool(Genepool genepool, JSONArray breedingPairs, LambdaLogger logger) {
        JSONArray newChildIds = new JSONArray();
        logger.log("Adding new children to genepool.");

        for(Object breedingPair : breedingPairs) {
            String childId = ((JSONObject) breedingPair).get(CHILD_ID_KEY).toString();

            genepool.addOrganismToGenepool(childId);
            newChildIds.add(childId);
        }

        kickOffSaveGenepoolStepFunction(genepool, logger);

        return newChildIds;
    }

    private static JSONArray kickoffBreedingPairs(List<BreedingPair> breedingPairs, LambdaLogger logger) {
        JSONArray breedingPairsJson = new JSONArray();
        logger.log("Kicking off breeding pairs.");

        for(BreedingPair breedingPair : breedingPairs) {
            JSONObject breedingPairMessage = new JSONObject();
            breedingPairMessage.put(PARENT_ONE_ID_KEY, breedingPair.organismOneId);
            breedingPairMessage.put(PARENT_TWO_ID_KEY, breedingPair.organismTwoId);
            breedingPairMessage.put(CHILD_ID_KEY, getNextGenepoolId());

            logger.log(kickoffBreedOrganismsStepFunction(breedingPairMessage).toString());

            breedingPairsJson.add(breedingPairMessage);
        }

        return breedingPairsJson;
    }

    private static StartExecutionResult kickoffBreedLargeGenepoolStepFunction(Genepool genepool) {
        AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();
        Gson gson = new Gson();
        String genepoolString = gson.toJson(genepool);

        StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
        stepFunctionRequest.setInput(genepoolString);
        stepFunctionRequest.setStateMachineArn(BREED_LARGE_GENEPOOL_STEP_FUNCTION_ARN);

        StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

        return startExecutionResult;
    }

    private static StartExecutionResult kickoffBreedOrganismsStepFunction(JSONObject breedOrganismsObject) {
        AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();

        StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
        stepFunctionRequest.setInput(breedOrganismsObject.toJSONString());
        stepFunctionRequest.setStateMachineArn(CALL_BREED_ORGANISM_STEP_FUNCTION_ARN);

        StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

        return startExecutionResult;
    }

    private static List<BreedingPair> buildBreedingPairs(List<String> genePoolOrganisms) {
        List<BreedingPair> breedingPairs = new ArrayList<>();
        Random random = new Random();

        while (genePoolOrganisms.size() >= 2) {
            BreedingPair nextBreedingPair = new BreedingPair();
            int organismIndex = random.nextInt(genePoolOrganisms.size());
            nextBreedingPair.organismOneId = genePoolOrganisms.get(organismIndex);
            genePoolOrganisms.remove(organismIndex);

            organismIndex = random.nextInt(genePoolOrganisms.size());
            nextBreedingPair.organismTwoId = genePoolOrganisms.get(organismIndex);
            genePoolOrganisms.remove(organismIndex);

            breedingPairs.add(nextBreedingPair);
        }

        return breedingPairs;
    }

    public static class BreedingPair {
        public String organismOneId;
        public String organismTwoId;

        public BreedingPair() {
        }

        @Override
        public String toString() {
            return "BreedingPair{" +
                    "organismOneId='" + organismOneId + '\'' +
                    ", organismTwoId='" + organismTwoId + '\'' +
                    '}';
        }
    }
}


