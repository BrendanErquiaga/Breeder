package com.erquiaga.organism.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.erquiaga.organism.models.Organism;
import com.google.gson.Gson;

import static com.erquiaga.organism.utils.OrganismConstants.SAVE_ORGANISM_STEP_FUNCTION_ARN;
import static com.erquiaga.organism.utils.OrganismRequestUtils.getNextOrganismId;

public class CreateOrganism {

    //Handle POST under /organism
    //TODO Refactor so this doesn't need a strict data model, just a schema to match
    public String createOrganismHandler(Organism organism, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Creating an organism");

        organism.setId(getNextOrganismId());
        Gson gson = new Gson();
        String organismJsonString = gson.toJson(organism);

        AWSStepFunctionsClient awsStepFunctionsClient = (AWSStepFunctionsClient) AWSStepFunctionsClientBuilder.defaultClient();

        StartExecutionRequest stepFunctionRequest = new StartExecutionRequest();
        stepFunctionRequest.setInput(organismJsonString);
        stepFunctionRequest.setStateMachineArn(SAVE_ORGANISM_STEP_FUNCTION_ARN);

        StartExecutionResult startExecutionResult = awsStepFunctionsClient.startExecution(stepFunctionRequest);

        logger.log(startExecutionResult.toString());

        return "This should have created an organism with ID: " + organism.getId();
    }
}
