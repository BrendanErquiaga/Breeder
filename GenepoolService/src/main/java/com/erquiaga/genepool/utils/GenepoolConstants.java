package com.erquiaga.genepool.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GenepoolConstants {
    public final static String GENEPOOL_ID_KEY = "id";
    public final static String ORGANISM_ID_PATH_PARAM_KEY = "organism-id";
    public final static String ORGANISMS_IN_GENEPOOL_KEY = "organismsInGenepool";
    public final static String PARENT_ONE_ID_KEY = "parentOneId";
    public final static String PARENT_TWO_ID_KEY = "parentTwoId";
    public final static String CHILD_ID_KEY = "childId";
    public final static int LARGE_GENEPOOL_SIZE = 100;

    //Logging
    public final static String GENEPOOL_JSON_MISSING_KEY_MESSAGE = "Genepool JSON missing key: ";

    //Event Keys
    public final static String EVENT_PATH_PARAMETERS_KEY = "pathParameters";

    //API Constants
    public final static String BREEDER_API_HTTP_PROTOCOL = "https://";
    public final static String BREEDER_API_HOST = "hib2dhbg51.execute-api.us-west-2.amazonaws.com";
    public final static String BREEDER_API_STAGE_DEV = "/DEV/";
    public final static String BREEDER_API_ENDPOINT = "breed/";
    public final static String BREEDER_CONSTRUCTED_ENDPOINT_DEV = BREEDER_API_HTTP_PROTOCOL + BREEDER_API_HOST + BREEDER_API_STAGE_DEV + BREEDER_API_ENDPOINT;


    //AWS Keys
    public final static String GENEPOOL_S3_BUCKET = "hazi-organism-collections";
    public final static String GENEPOOL_FOLDER = "genepools/";
    public final static String GENEPOOL_FILE_SUFFIX = ".json";
    public final static String SAVE_GENEPOOL_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveGenepool";
    public final static String CALL_BREED_ORGANISM_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:CallBreedOrganisms";
    public final static String BREED_LARGE_GENEPOOL_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:BreedLargeGenepool";

    //CORS Stuff
    public final static Map<String, String> GENERIC_RESPONSE_HEADERS;
    static {
        Map<String, String> responseHeaders = new HashMap<>();

        responseHeaders.put("Access-Control-Allow-Origin", "*");
        responseHeaders.put("Content-Type", "application/json");


        GENERIC_RESPONSE_HEADERS = Collections.unmodifiableMap(responseHeaders);
    }
}
