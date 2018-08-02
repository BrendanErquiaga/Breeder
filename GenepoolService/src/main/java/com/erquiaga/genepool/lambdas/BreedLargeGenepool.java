package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.erquiaga.genepool.models.Genepool;

import static com.erquiaga.genepool.lambdas.BreedGenepool.breedGenepool;

public class BreedLargeGenepool {

    public String breedLargeGenepool(Genepool genepool, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Breeding a Large Genepool");

        return breedGenepool(genepool, logger).toString();
    }
}
