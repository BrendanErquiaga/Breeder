package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.JSONObject;

import static com.erquiaga.genepool.utils.GenepoolConstants.*;
import static com.erquiaga.genepool.utils.GenepoolRequestUtils.postRequest;

public class CallBreedOrganisms {

    public String callBreederServiceBreed(JSONObject breedOrganismsObject, Context context) throws Exception {
        LambdaLogger logger = context.getLogger();
        logger.log("Calling Breed Organisms");

        logger.log(breedOrganismsObject.toJSONString());

        String parentOneId = (String) breedOrganismsObject.get(PARENT_ONE_ID_KEY);
        String parentTwoId = (String) breedOrganismsObject.get(PARENT_TWO_ID_KEY);
        String childId = (String) breedOrganismsObject.get(CHILD_ID_KEY);

        StringBuilder urlSB = new StringBuilder();

        urlSB.append(BREEDER_CONSTRUCTED_ENDPOINT_DEV).append("?");
        urlSB.append(PARENT_ONE_ID_KEY).append("=").append(parentOneId);
        urlSB.append("&").append(PARENT_TWO_ID_KEY).append("=").append(parentTwoId);
        urlSB.append("&").append(CHILD_ID_KEY).append("=").append(childId);

        String saveOrganismResposne = postRequest(urlSB.toString(), "");
        logger.log(saveOrganismResposne);
        return saveOrganismResposne;
    }
}
