package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.erquiaga.genepool.models.Genepool;
import com.google.gson.Gson;

import static com.erquiaga.genepool.utils.GenepoolConstants.SAVE_GENEPOOL_STEP_FUNCTION_ARN;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getNextGenepoolId;

public class CreateGenepool {

    //Handle POST under /genepool
    public String createGenepoolHandler(Genepool genepool, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Creating a Genepool");

        genepool = new Genepool();

        if(genepool.getId() == null) {
            genepool.setId(getNextGenepoolId());
        }

        genepool.setId(getNextGenepoolId());
        genepool.addOrganismToGenepool("1");
        genepool.addOrganismToGenepool("2");
        genepool.addOrganismToGenepool("3");
        genepool.addOrganismToGenepool("4");
        genepool.addOrganismToGenepool("5");
        genepool.addOrganismToGenepool("6");
        genepool.addOrganismToGenepool("7");
        genepool.addOrganismToGenepool("8");
        genepool.addOrganismToGenepool("9");
        genepool.addOrganismToGenepool("0");

        kickOffSaveGenepoolStepFunction(genepool, logger);

        return "This should have created a genepool with ID: " + genepool.getId();
    }

    public static StartExecutionResult kickOffSaveGenepoolStepFunction(Genepool genepool, LambdaLogger logger) {
        Gson gson = new Gson();
        String genepoolJsonString = gson.toJson(genepool);

        AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();

        StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
        stepFunctionRequest.setInput(genepoolJsonString);
        stepFunctionRequest.setStateMachineArn(SAVE_GENEPOOL_STEP_FUNCTION_ARN);

        StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

        logger.log(startExecutionResult.toString());

        return startExecutionResult;
    }
}
