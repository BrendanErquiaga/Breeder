package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.genepool.models.Genepool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.erquiaga.genepool.utils.GenepoolConstants.EVENT_PATH_PARAMETERS_KEY;
import static com.erquiaga.genepool.utils.GenepoolConstants.GENEPOOL_ID_KEY;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.getGenepoolIfExists;
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

            if(genepool != null) {
                JSONObject breedGenepoolJSON = breedGenepool(genepool, logger);

                responseJson.put("statusCode", SC_OK);
                responseJson.put("body", breedGenepoolJSON.toJSONString());
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

    private JSONObject breedGenepool(Genepool genepool, LambdaLogger logger) {
        JSONObject breedGenepoolJSON = new JSONObject();
        JSONArray breedingPairsJson = new JSONArray();

        List<BreedingPair> breedingPairs = new ArrayList<>();
        List<String> genePoolOrganisms = genepool.getOrganismsInGenepool();
        Random random = new Random();

        while (genePoolOrganisms.size() > 2) {
            BreedingPair nextBreedingPair = new BreedingPair();
            int organismIndex = random.nextInt(genePoolOrganisms.size());
            nextBreedingPair.organismOneId = genePoolOrganisms.get(organismIndex);
            genePoolOrganisms.remove(organismIndex);

            organismIndex = random.nextInt(genePoolOrganisms.size());
            nextBreedingPair.organismTwoId = genePoolOrganisms.get(organismIndex);
            genePoolOrganisms.remove(organismIndex);

            breedingPairs.add(nextBreedingPair);
            breedingPairsJson.add(nextBreedingPair.toString());
        }

        breedGenepoolJSON.put("message", "Successfully bred Genepool.");
        breedGenepoolJSON.put("breedingPairs", breedingPairsJson.toJSONString());

        return breedGenepoolJSON;
    }

    public class BreedingPair {
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


