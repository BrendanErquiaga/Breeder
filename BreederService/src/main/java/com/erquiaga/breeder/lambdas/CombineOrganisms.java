package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.breeder.models.Organism;
import com.erquiaga.breeder.utils.DNACombiner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Random;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getParmeterIfExists;


public class CombineOrganisms {
    JSONParser parser = new JSONParser();

    public Organism combineOrganisms(String[] breedingData, Context context) throws Exception {
        LambdaLogger logger = context.getLogger();
        logger.log("Combining Breeding Data");

        try {
            JSONObject breedingObject1 = (JSONObject) parser.parse(breedingData[0]);
            JSONObject breedingObject2 = (JSONObject) parser.parse(breedingData[1]);

            String newOrganismId = getParmeterIfExists(breedingObject1, CHILD_ORGANISM_ID_KEY,null);

            if(newOrganismId != null && breedingObject1 != null && breedingObject2 != null) {
                Organism newOrganism = new Organism();
                newOrganism.setId(newOrganismId);

                JSONObject parentOrganismData1 = (JSONObject)breedingObject1.get(PARENT_DATA_KEY);
                JSONObject parentOrganismData2 = (JSONObject)breedingObject2.get(PARENT_DATA_KEY);

                setupOrganismDefaults(newOrganism, parentOrganismData1, parentOrganismData2);

                newOrganism.setDna(DNACombiner.getOrganismDNA(parentOrganismData1, parentOrganismData2));

                return newOrganism;
            } else {
                throw new Exception("Child Organism ID not found.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
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
