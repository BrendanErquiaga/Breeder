package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederUtils.getOrganismJson;
import static com.erquiaga.breeder.utils.BreederUtils.getParmeterIfExists;

public class GatherBreedingData {

    //Gets Both Parent Organisms, Passes them to next step
    public String gatherBreedingData(JSONObject breedingObject, Context context) throws IOException, ParseException {
        LambdaLogger logger = context.getLogger();
        logger.log("Gathing Breeding Data");

        JSONObject breedingData = new JSONObject();
        String parentOneId = getParmeterIfExists(breedingObject, PARENT_ONE_ID_KEY, "");
        String parentTwoId = getParmeterIfExists(breedingObject, PARENT_TWO_ID_KEY, "");
        String childId = getParmeterIfExists(breedingObject, CHILD_ORGANISM_ID_KEY, "");

        try {
            JSONObject parentOrganismOne = getOrganismJson(parentOneId);
            JSONObject parentOrganismTwo = getOrganismJson(parentTwoId);

            breedingData.put(PARENT_ONE_DATA_KEY, parentOrganismOne);
            breedingData.put(PARENT_TWO_DATA_KEY, parentOrganismTwo);
            breedingData.put(CHILD_ORGANISM_ID_KEY, childId);
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            throw e;
        }

        return breedingData.toJSONString();
    }
}
