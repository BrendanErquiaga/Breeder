package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.erquiaga.breeder.models.Organism;

public class CreateOrganism {

    //Handle POST under /organism
    public String createOrganismHandler(Organism organism, Context context) {
        return "THIS SHOULD CREATE AN ORGANISM THAT LOOKS LIKE THIS: " + organism.toString();
    }
}
