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

import static com.erquiaga.breeder.utils.BreederConstants.*;

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
        S3Object fetchedOrganism = getOrganismS3Object(organismId);
        InputStream organismDataStream = fetchedOrganism.getObjectContent();
        JSONParser jsonParser = new JSONParser();

        return (JSONObject)jsonParser.parse(new InputStreamReader(organismDataStream, "UTF-8"));
    }

    public static S3Object getOrganismS3Object(String organismId) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        return s3Client.getObject(BREEDER_S3_BUCKET, getOrganismObjectKey(organismId));
    }

    public static boolean organismExists(String organismId) {
        String organismKey = ORGANISM_FOLDER + organismId + ORGANISM_FILE_SUFFIX;
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        return s3Client.doesObjectExist(BREEDER_S3_BUCKET, organismKey);
    }

    public static String getOrganismObjectKey(String organismId) {
        return ORGANISM_FOLDER + organismId + ORGANISM_FILE_SUFFIX;
    }

    public static boolean isValidOrganismJson(JSONObject organismJson) {
        boolean isValid = true;

        if(!organismJson.containsKey(ORGANISM_ID_KEY)) {
            //Probably log about it
            isValid = false;
        } else if(!organismJson.containsKey(ORGANISM_TYPE_KEY)) {
            //Probably log about it
            isValid = false;
        } else if(!organismJson.containsKey(BREEDING_RULES_KEY)) {
            //Probably log about it
            isValid = false;
        } else if(!organismJson.containsKey(METADATA_KEY)) {
            //Probably log about it
            isValid = false;
        } else if(!organismJson.containsKey(DNA_KEY)) {
            //Probably log about it
            isValid = false;
        } else if(!((JSONObject)organismJson.get(METADATA_KEY)).containsKey(ORGANISM_NAME_KEY)) {
            //Probably log about it
            isValid = false;
        } else if(!((JSONObject)organismJson.get(DNA_KEY)).containsKey(CHROMOSOMES_KEY)) {
            //Probably log about it
            isValid = false;
        }

        return isValid;
    }
}
