package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.breeder.models.Organism;
import com.erquiaga.breeder.utils.DNACombiner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Random;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getParmeterIfExists;


public class CombineOrganisms {
    JSONParser parser = new JSONParser();

    public Organism combineOrganisms(String breedingData, Context context) throws ParseException {
        LambdaLogger logger = context.getLogger();
        logger.log("Combining Breeding Data");


        try {
            JSONObject breedingDataJson = (JSONObject) parser.parse(breedingData);
            JSONObject p1Data = (JSONObject) breedingDataJson.get(PARENT_ONE_DATA_KEY);
            JSONObject p2Data = (JSONObject) breedingDataJson.get(PARENT_TWO_DATA_KEY);

            String newOrganismId = getParmeterIfExists(breedingDataJson, CHILD_ORGANISM_ID_KEY,"");

            Organism newOrganism = new Organism();
            newOrganism.setId(newOrganismId);

            setupOrganismDefaults(newOrganism, p1Data, p2Data);

            newOrganism.setDna(DNACombiner.getOrganismDNA(p1Data, p2Data));

            return newOrganism;
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void setupOrganismDefaults(Organism organism, JSONObject p1Data, JSONObject p2Data) {

        organism.setType(getParmeterIfExists(p1Data, ORGANISM_TYPE_KEY, DEFAULT_ORGANISM_TYPE));
        organism.setMetadata(getNewOrganismMetadata(p1Data, p2Data));
        organism.setBreedingRules((JSONObject)p1Data.get(BREEDING_RULES_KEY));
    }

    private JSONObject getNewOrganismMetadata(JSONObject p1Data, JSONObject p2Data) {
        JSONObject metadata = new JSONObject();
        String p1Name = (String)((JSONObject)p1Data.get(METADATA_KEY)).get(ORGANISM_NAME_KEY);
        String p2Names = (String)((JSONObject)p2Data.get(METADATA_KEY)).get(ORGANISM_NAME_KEY);
        String[] parentNames = new String[] {p1Name, p2Names};

        metadata.put(ORGANISM_NAME_KEY, getNewOrganismName());
        metadata.put(PARENT_NAMES_KEY, parentNames);

        return metadata;
    }

    private String getNewOrganismName() {
        int length = new Random().nextInt(NEW_ORGANISM_MAX_NAME_LENGTH) + NEW_ORGANISM_MIN_NAME_LENGTH;
        return getRandomName(length);
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
