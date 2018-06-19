package com.erquiaga.breeder.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.erquiaga.breeder.utils.BreederConstants.BREEDER_S3_BUCKET;
import static com.erquiaga.breeder.utils.BreederConstants.ORGANISM_FILE_SUFFIX;
import static com.erquiaga.breeder.utils.BreederConstants.ORGANISM_FOLDER;

public class BreederUtils {

    public static String getParmeterIfExists(JSONObject jsonObject, String parameterKey, String defaultValue)
    {
        if (jsonObject.get(parameterKey) != null) {
            return (String)jsonObject.get(parameterKey);
        }

        return defaultValue;
    }

    public static String getNextOrganismId() {
        //TODO Write a better UUID system
        return Long.toString(System.currentTimeMillis());
    }

    public static JSONObject getOrganismJson(String organismId) throws IOException, ParseException {
        String organismKey = ORGANISM_FOLDER + organismId + ORGANISM_FILE_SUFFIX;
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object fetchedOrganism = s3Client.getObject(BREEDER_S3_BUCKET, organismKey);
        InputStream organismDataStream = fetchedOrganism.getObjectContent();
        JSONParser jsonParser = new JSONParser();

        return (JSONObject)jsonParser.parse(new InputStreamReader(organismDataStream, "UTF-8"));
    }
}
