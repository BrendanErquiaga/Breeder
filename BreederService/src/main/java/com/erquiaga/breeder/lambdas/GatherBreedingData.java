package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getParmeterIfExists;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getRequest;

public class GatherBreedingData {
    JSONParser parser = new JSONParser();

    public String gatherParentOneData(JSONObject breedingObject, Context context)  throws IOException, ParseException {
        String parentOneId = getParmeterIfExists(breedingObject, PARENT_ONE_ID_KEY, "");
        String childId = getParmeterIfExists(breedingObject, CHILD_ORGANISM_ID_KEY, "");

        return (!"".equals(parentOneId)) ? gatherOrganismData(parentOneId, childId, context) : "Parent One ID Missing";
    }

    public String gatherParentTwoData(JSONObject breedingObject, Context context)  throws IOException, ParseException {
        String parentTwoId = getParmeterIfExists(breedingObject, PARENT_TWO_ID_KEY, "");
        String childId = getParmeterIfExists(breedingObject, CHILD_ORGANISM_ID_KEY, "");

        return (!"".equals(parentTwoId)) ? gatherOrganismData(parentTwoId, childId, context) : "Parent Two ID Missing";
    }

    private String gatherOrganismData(String parentId, String childId, Context context) throws IOException, ParseException {
        LambdaLogger logger = context.getLogger();
        logger.log("Gathing Organism Data for id: " + parentId);

        JSONObject breedingData = new JSONObject();
        try {
            String parentDataString = getRequest(ORGANISM_CONSTRUCTED_ENDPOINT_DEV + parentId);
            JSONObject parentOrganism = (JSONObject) parser.parse(parentDataString);

            breedingData.put(PARENT_ID_KEY, parentId);
            breedingData.put(PARENT_DATA_KEY, parentOrganism);
            breedingData.put(CHILD_ORGANISM_ID_KEY, childId);
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            throw e;
        }

        return breedingData.toJSONString();
    }


}
