package com.erquiaga.organism.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.erquiaga.organism.models.Organism;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.erquiaga.organism.utils.OrganismConstants.ORGANISM_S3_BUCKET;
import static com.erquiaga.organism.utils.OrganismConstants.ORGANISM_FILE_SUFFIX;
import static com.erquiaga.organism.utils.OrganismConstants.ORGANISM_FOLDER;

public class SaveOrganismToS3 {

    public void saveOrganism(Organism organism, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Saving an organism");

        Gson gson = new Gson();
        String organismJsonString = gson.toJson(organism);
        String organismKey = ORGANISM_FOLDER + organism.getId() + ORGANISM_FILE_SUFFIX;

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
            InputStream inputStream = new ByteArrayInputStream(organismJsonString.getBytes(StandardCharsets.UTF_8));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/json");

            PutObjectRequest organismObjectRequest =
                    new PutObjectRequest(ORGANISM_S3_BUCKET, organismKey, inputStream, metadata);

            s3Client.putObject(organismObjectRequest);
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
        }

        logger.log("Saved organism with this ID: " + organism.getId());
    }
}
