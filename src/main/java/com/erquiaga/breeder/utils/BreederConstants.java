package com.erquiaga.breeder.utils;

public class BreederConstants {

    //JSON Keys
    public final static String ORGANISM_TYPE_KEY = "type";
    public final static String ORGANISM_ID_KEY = "id";
    public final static String DNA_KEY = "dna";

    //AWS Keys
    public final static String BREEDER_S3_BUCKET = "breeder-collections";
    public final static String ORGANISM_FOLDER = "organisms/";
    public final static String ORGANISM_FILE_SUFFIX = ".json";
    public final static String SAVE_ORGANISM_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveBreederOrganism";
}
