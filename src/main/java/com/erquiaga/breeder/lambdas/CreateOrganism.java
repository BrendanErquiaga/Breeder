package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsAsyncClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsAsyncClientBuilder;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClient;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.erquiaga.breeder.models.Organism;
import com.google.gson.Gson;

import static com.erquiaga.breeder.utils.BreederConstants.SAVE_ORGANISM_STEP_FUNCTION_ARN;
import static com.erquiaga.breeder.utils.BreederUtils.getNextOrganismId;

public class CreateOrganism {

    //Handle POST under /organism
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
