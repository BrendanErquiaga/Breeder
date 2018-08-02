package com.erquiaga.genepool.utils;

public class GenepoolConstants {
    public final static String GENEPOOL_ID_KEY = "id";
    public final static String ORGANISM_ID_PATH_PARAM_KEY = "organism-id";
    public final static String ORGANISMS_IN_GENEPOOL_KEY = "organismsInGenepool";

    //Logging
    public final static String GENEPOOL_JSON_MISSING_KEY_MESSAGE = "Genepool JSON missing key: ";

    //Event Keys
    public final static String EVENT_PATH_PARAMETERS_KEY = "pathParameters";

    //API Constants
    public final static String BREEDER_API_HTTP_PROTOCOL = "https://";
    public final static String BREEDER_API_HOST = "https://";
    public final static String BREEDER_API_STAGE_DEV = "/DEV/";
    public final static String BREEDER_API_ENDPOINT = "/DEV/";


    //AWS Keys
    public final static String GENEPOOL_S3_BUCKET = "hazi-organism-collections";
    public final static String GENEPOOL_FOLDER = "genepools/";
    public final static String GENEPOOL_FILE_SUFFIX = ".json";
    public final static String SAVE_GENEPOOL_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveGenepool";
}
