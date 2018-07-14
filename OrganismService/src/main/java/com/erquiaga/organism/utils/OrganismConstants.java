package com.erquiaga.organism.utils;

public class OrganismConstants {

    //JSON Keys
    public final static String ORGANISM_TYPE_KEY = "type";
    public final static String ORGANISM_ID_KEY = "id";
    public final static String METADATA_KEY = "metadata";
    public final static String BREEDING_RULES_KEY = "breedingRules";
    public final static String ORGANISM_NAME_KEY = "name";
    public final static String DNA_KEY = "dna";
    public final static String CHROMOSOMES_KEY = "chromosomes";

    //Loggins
    public final static String ORGANISM_JSON_MISSING_KEY_MESSAGE = "Organism JSON missing key: ";

    //AWS Keys
    public final static String BREEDER_S3_BUCKET = "organism-collections";
    public final static String ORGANISM_FOLDER = "organisms/";
    public final static String ORGANISM_FILE_SUFFIX = ".json";
    public final static String SAVE_ORGANISM_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveOrganism";
}
