package com.erquiaga.organism.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrganismConstants {

    //JSON Keys
    public final static String ORGANISM_TYPE_KEY = "type";
    public final static String ORGANISM_ID_KEY = "id";
    public final static String METADATA_KEY = "metadata";
    public final static String BREEDING_RULES_KEY = "breedingRules";
    public final static String ORGANISM_NAME_KEY = "name";
    public final static String DNA_KEY = "dna";
    public final static String CHROMOSOMES_KEY = "chromosomes";

    //Logging
    public final static String ORGANISM_JSON_MISSING_KEY_MESSAGE = "Organism JSON missing key: ";

    //AWS Keys
    public final static String ORGANISM_S3_BUCKET = "hazi-organism-collections";
    public final static String ORGANISM_FOLDER = "organisms/";
    public final static String ORGANISM_FILE_SUFFIX = ".json";
    public final static String SAVE_ORGANISM_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveOrganism";

    //CORS Stuff
    public final static Map<String, String> GENERIC_RESPONSE_HEADERS;
    static {
        Map<String, String> responseHeaders = new HashMap<>();

        responseHeaders.put("Access-Control-Allow-Origin", "*");
        responseHeaders.put("Content-Type", "application/json");


        GENERIC_RESPONSE_HEADERS = Collections.unmodifiableMap(responseHeaders);
    }
}
