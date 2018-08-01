package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.genepool.models.Genepool;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.genepool.lambdas.CreateGenepool.kickOffSaveGenepoolStepFunction;
import static com.erquiaga.genepool.utils.GenepoolConstants.*;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.*;
import static org.apache.http.HttpStatus.*;

public class HandleModifyGenepool extends ApiGatewayProxyLambda {

    //Handle everything under /genepool/{id}/{organism-id}
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        super.handleRequest(inputStream, outputStream, context);
    }

    @Override
    public JSONObject handleGetRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Checking if Organism is in Genepool");

        JSONObject responseJson = new JSONObject();

        try {
            String genepoolId = "";
            String organismId = "";

            if (jsonEventObject.get(EVENT_PATH_PARAMETERS_KEY) != null) {
                genepoolId = getPathParameter(jsonEventObject, GENEPOOL_ID_KEY);
                organismId = getPathParameter(jsonEventObject, ORGANISM_ID_PATH_PARAM_KEY);
            }

            Genepool genepool = getGenepoolIfExists(genepoolId, logger);

            if(genepool != null) {
                if(genepool.organismIsInGenepool(organismId)) {
                    responseJson.put("statusCode", SC_OK);
                    responseJson.put("body", "Organism {" + organismId + "} found in Genepool {" + genepool.getId() + "}.");
                } else {
                    responseJson.put("statusCode", SC_NOT_FOUND);
                    responseJson.put("body", "Organism {" + organismId + "} not found in Genepool {" + genepool.getId() + "}.");
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

    private Genepool getGenepoolIfExists(String genepoolId, LambdaLogger logger) throws IOException, ParseException {
        if(genepoolExists(genepoolId)) {
            JSONObject genepoolJson = getGenepoolJson(genepoolId);
            Gson genepoolGson = new Gson();

            return genepoolGson.fromJson(genepoolJson.toJSONString(), Genepool.class);
        }

        return null;
    }

    @Override
    protected JSONObject handleDeleteRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Removing Organism from Genepool");

        JSONObject responseJson = new JSONObject();
        JSONObject bodyJSON = new JSONObject();
        Gson gson = new Gson();
        String genepoolId = "";
        String organismId = "";
        try {
            if (jsonEventObject.get(EVENT_PATH_PARAMETERS_KEY) != null) {
                genepoolId = getPathParameter(jsonEventObject, GENEPOOL_ID_KEY);
                organismId = getPathParameter(jsonEventObject, ORGANISM_ID_PATH_PARAM_KEY);
            }

            Genepool genepool = getGenepoolIfExists(genepoolId, logger);
            if(genepool != null) {
                if(genepool.organismIsInGenepool(organismId)) {
                    genepool.removeOrganismFromGenepool(organismId);

                    kickOffSaveGenepoolStepFunction(genepool, logger);

                    bodyJSON.put("genepool", gson.toJson(genepool));
                    bodyJSON.put("message", "Organism {" + organismId + "} was successfully removed from Genepool {" + genepool.getId() + "}.");

                    responseJson.put("body", bodyJSON.toJSONString());
                    responseJson.put("statusCode", SC_OK);
                } else {
                    responseJson.put("statusCode", SC_NOT_FOUND);
                    responseJson.put("body", "Organism {" + organismId + "} not found in Genepool {" + genepool.getId() + "}.");
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

    @Override
    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Adding Organism to Genepool");

        JSONObject responseJson = new JSONObject();
        JSONObject bodyJSON = new JSONObject();
        Gson gson = new Gson();
        String genepoolId = "";
        String organismId = "";
        try {
            if (jsonEventObject.get(EVENT_PATH_PARAMETERS_KEY) != null) {
                genepoolId = getPathParameter(jsonEventObject, GENEPOOL_ID_KEY);
                organismId = getPathParameter(jsonEventObject, ORGANISM_ID_PATH_PARAM_KEY);
            }

            Genepool genepool = getGenepoolIfExists(genepoolId, logger);
            if(genepool != null) {
                if(!genepool.organismIsInGenepool(organismId)) {
                    genepool.addOrganismToGenepool(organismId);

                    kickOffSaveGenepoolStepFunction(genepool, logger);

                    bodyJSON.put("genepool", gson.toJson(genepool));
                    bodyJSON.put("message", "Organism {" + organismId + "} was successfully added to Genepool {" + genepool.getId() + "}.");

                    responseJson.put("body", bodyJSON.toJSONString());
                    responseJson.put("statusCode", SC_OK);
                } else {
                    responseJson.put("statusCode", SC_NOT_FOUND);
                    responseJson.put("body", "Organism {" + organismId + "} is already in Genepool {" + genepool.getId() + "}.");
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
