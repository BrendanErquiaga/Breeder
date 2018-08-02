package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.genepool.models.Genepool;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.genepool.lambdas.CreateGenepool.kickOffSaveGenepoolStepFunction;
import static com.erquiaga.genepool.utils.GenepoolConstants.EVENT_PATH_PARAMETERS_KEY;
import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_ID_KEY;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getGenepoolIfExists;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getPathParameter;
import static org.apache.http.HttpStatus.*;

public class CullGenepool extends ApiGatewayProxyLambda {

    //Handle everything under /genepool/{id}/cull
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Attempting to Cull Genepool");

        JSONObject responseJson = new JSONObject();

        try {
            String genepoolId = "";

            if (jsonEventObject.get(EVENT_PATH_PARAMETERS_KEY) != null) {
                genepoolId = getPathParameter(jsonEventObject, GENEPOOL_ID_KEY);
            }

            Genepool genepool = getGenepoolIfExists(genepoolId, logger);

            if(genepool != null) {
                int genePoolGenerations = genepool.getGenepoolGenerations().size();

                if(genePoolGenerations > 0) {
                    genepool.removeGenerationFromGenepool(0);
                    kickOffSaveGenepoolStepFunction(genepool, logger);
                    responseJson.put("statusCode", SC_OK);
                    responseJson.put("body", "Removed oldest generation from Genepool.");
                } else {
                    responseJson.put("statusCode", SC_NOT_FOUND);
                    responseJson.put("body", "Genepool has no generations to remove!");
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
}
