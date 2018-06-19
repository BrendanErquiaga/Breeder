package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.breeder.models.Organism;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Random;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederUtils.getParmeterIfExists;

public class CombineOrganisms {
    JSONParser parser = new JSONParser();

    public Organism combineOrganisms(String breedingData, Context context) throws ParseException {
        LambdaLogger logger = context.getLogger();
        logger.log("Combining Breeding Data");


        try {
            JSONObject breedingDataJson = (JSONObject) parser.parse(breedingData);
            JSONObject parentOneData = (JSONObject) breedingDataJson.get(PARENT_ONE_DATA_KEY);
            JSONObject parentTwoData = (JSONObject) breedingDataJson.get(PARENT_TWO_DATA_KEY);

            String newOrganismId = getParmeterIfExists(breedingDataJson, CHILD_ORGANISM_ID_KEY,"");

            Organism newOrganism = new Organism();
            newOrganism.setId(newOrganismId);

            setupOrganismDefaults(newOrganism, parentOneData, parentTwoData);

            newOrganism.setDna(getOrganismDNA(parentOneData, parentTwoData));

            return newOrganism;
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void setupOrganismDefaults(Organism organism, JSONObject parentOneData, JSONObject parentTwoData) {

        organism.setType(getParmeterIfExists(parentOneData, ORGANISM_TYPE_KEY, DEFAULT_ORGANISM_TYPE));
        organism.setMetadata(getNewOrganismMetadata(parentOneData, parentTwoData));
        organism.setBreedingRules((JSONObject)parentOneData.get(BREEDING_RULES_KEY));
    }

    private JSONObject getOrganismDNA(JSONObject parentOneData, JSONObject parentTwoData) {
        JSONObject dnaObject = new JSONObject();

        return dnaObject;
    }

    private JSONObject getNewOrganismMetadata(JSONObject parentOneData, JSONObject parentTwoData) {
        JSONObject metadata = new JSONObject();
        String parentOneName = (String)((JSONObject)parentOneData.get(METADATA_KEY)).get(ORGANISM_NAME_KEY);
        String parentTwoName = (String)((JSONObject)parentTwoData.get(METADATA_KEY)).get(ORGANISM_NAME_KEY);
        String[] parentNames = new String[] {parentOneName, parentTwoName};

        metadata.put(ORGANISM_NAME_KEY, getNewOrganismName(parentOneName));
        metadata.put(PARENT_NAMES_KEY, parentNames);

        return metadata;
    }

    private String getNewOrganismName(String parentName) {
        return getRandomName(3) + " child of " + parentName;
    }

    private String getRandomName(int length) {
        StringBuilder name = new StringBuilder();
        Random r = new Random();

        for(int i = 0; i < length; i++) {
            name.append((char)(r.nextInt(26) + 'a'));
        }

        return name.toString();
    }
}
