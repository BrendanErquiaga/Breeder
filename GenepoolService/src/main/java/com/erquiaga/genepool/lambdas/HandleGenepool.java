package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_ID_KEY;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.genepoolExists;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getGenepoolJson;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getParmeterIfExists;
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
        return super.handlePutRequest(jsonEventObject, context);
    }

    @Override
    protected JSONObject handleDeleteRequest(JSONObject jsonEventObject, Context context) {
        return super.handleDeleteRequest(jsonEventObject, context);
    }

    @Override
    protected JSONObject handlePostRequest(JSONObject jsonEventObject, Context context) {
        return super.handlePostRequest(jsonEventObject, context);
    }
}
