package com.erquiaga.genepool.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.erquiaga.genepool.models.Genepool;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.erquiaga.genepool.utils.GenepoolConstants.*;

public class SaveGenepoolToS3 {
    public void saveGenepool(Genepool genepool, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Saving an organism");

        Gson gson = new Gson();
        String genepoolJsonString = gson.toJson(genepool);
        String genepoolKey = GENEPOOL_FOLDER + genepool.getId() + GENEPOOL_FILE_SUFFIX;

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
            InputStream inputStream = new ByteArrayInputStream(genepoolJsonString.getBytes(StandardCharsets.UTF_8));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/json");

            PutObjectRequest organismObjectRequest =
                    new PutObjectRequest(GENEPOOL_S3_BUCKET, genepoolKey, inputStream, metadata);

            s3Client.putObject(organismObjectRequest);
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
        }

        logger.log("Saved genepool with this ID: " + genepool.getId());
    }
}
