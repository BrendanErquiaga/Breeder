package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.breeder.models.Organism;
import com.google.gson.Gson;

import static com.erquiaga.breeder.utils.BreederConstants.ORGANISM_CONSTRUCTED_ENDPOINT_DEV;
import static com.erquiaga.breeder.utils.BreederRequestUtils.postRequest;

public class CallSaveOrganism {

    public String callOrganismServiceSave(Organism organism, Context context) throws Exception {
        LambdaLogger logger = context.getLogger();
        logger.log("Calling Save Organism to S3");

        Gson gson = new Gson();
        String organismJsonString = gson.toJson(organism);
        String saveOrganismResposne = postRequest(ORGANISM_CONSTRUCTED_ENDPOINT_DEV, organismJsonString);
        logger.log(saveOrganismResposne);
        return saveOrganismResposne;
    }
}
