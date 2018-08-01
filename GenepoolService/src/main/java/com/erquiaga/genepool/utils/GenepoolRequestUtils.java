package com.erquiaga.genepool.utils;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.erquiaga.genepool.utils.GenepoolConstants.*;

public class GenepoolRequestUtils {

    public static String getPathParameter(JSONObject eventObject, String pathParameterKey) {
        JSONObject pathParameters = (JSONObject)eventObject.get(EVENT_PATH_PARAMETERS_KEY);
        return getParmeterIfExists(pathParameters, pathParameterKey, "");
    }

    public static String getParmeterIfExists(JSONObject jsonObject, String parameterKey, String defaultValue)
    {
        if (jsonObject.get(parameterKey) != null) {
            return (String)jsonObject.get(parameterKey);
        }

        return defaultValue;
    }

    public static String getNextGenepoolId() {
        //TODO Write a better UUID system
        return Long.toString(System.currentTimeMillis());
    }

    public static boolean genepoolExists(String genepoolId) {
        String genepoolObjectKey = getGenepoolObjectKey(genepoolId);
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        return s3Client.doesObjectExist(GENEPOOL_S3_BUCKET, genepoolObjectKey);
    }

    public static String getGenepoolObjectKey(String genepoolId) {
        return GENEPOOL_FOLDER + genepoolId + GENEPOOL_FILE_SUFFIX;
    }

    public static JSONObject getGenepoolJson(String genepoolId) throws IOException, ParseException {
        S3Object fetchedGenepool = getGenepoolS3Object(genepoolId);
        InputStream genepoolDataStream = fetchedGenepool.getObjectContent();
        JSONParser jsonParser = new JSONParser();

        return (JSONObject)jsonParser.parse(new InputStreamReader(genepoolDataStream, "UTF-8"));
    }

    public static S3Object getGenepoolS3Object(String genepoolId) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        return s3Client.getObject(GENEPOOL_S3_BUCKET, getGenepoolObjectKey(genepoolId));
    }
    
    public static boolean isValidGenepoolJson(JSONObject genepoolJson, LambdaLogger logger) {
        boolean isValid = true;

        if(!genepoolJson.containsKey(GENEPOOL_ID_KEY)) {
            logger.log(GENEPOOL_JSON_MISSING_KEY_MESSAGE + GENEPOOL_ID_KEY);
            isValid = false;
        } else if(!genepoolJson.containsKey(ORGANISMS_IN_GENEPOOL_KEY)) {
            logger.log(GENEPOOL_JSON_MISSING_KEY_MESSAGE + ORGANISMS_IN_GENEPOOL_KEY);
            isValid = false;
        }

        return isValid;
    }
}
