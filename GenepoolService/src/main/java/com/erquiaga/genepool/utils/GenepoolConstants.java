package com.erquiaga.genepool.utils;

public class GenepoolConstants {
    public final static String GENEPOOL_ID_KEY = "id";
    public final static String ORGANISMS_IN_GENEPOOL_KEY = "organismsInGenepool";

    //Logging
    public final static String OGENEPOOL_JSON_MISSING_KEY_MESSAGE = "Genepool JSON missing key: ";

    //AWS Keys
    public final static String GENEPOOL_S3_BUCKET = "hazi-organism-collections";
    public final static String GENEPOOL_FOLDER = "genepools/";
    public final static String GENEPOOL_FILE_SUFFIX = ".json";
    public final static String SAVE_GENEPOOL_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveGenepool";
}