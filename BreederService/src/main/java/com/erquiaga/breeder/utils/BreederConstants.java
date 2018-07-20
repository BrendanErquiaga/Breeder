package com.erquiaga.breeder.utils;

public class BreederConstants {

    //Defaults
    public final static String DEFAULT_ORGANISM_TYPE = "organism";


    //Organism JSON Keys
    public final static String ORGANISM_TYPE_KEY = "type";
    public final static String ORGANISM_ID_KEY = "id";
    public final static String METADATA_KEY = "metadata";
    public final static String BREEDING_RULES_KEY = "breedingRules";
    public final static String ORGANISM_NAME_KEY = "name";
    public final static String PARENT_NAMES_KEY = "parents";
    public final static String DNA_KEY = "dna";
    public final static String CHROMOSOMES_KEY = "chromosomes";
    public final static String CHROMOSOME_ID_KEY = "chromosomeID";
    public final static String TRAITS_KEY = "traits";
    public final static String TRAIT_ID_KEY = "traitId";
    public final static String GENE_A_KEY = "geneA";
    public final static String GENE_B_KEY = "geneB";
    public final static String GENE_EXPRESSION_VALUE_KEY = "expressionValue";
    public final static String GENE_COMBINATION_VALUE_KEY = "combinationValue";

    //Request Keys
    public final static String PARENT_ONE_ID_KEY = "parentOneId";
    public final static String PARENT_TWO_ID_KEY = "parentTwoId";
    public final static String PARENT_ID_KEY = "parentID";
    public final static String PARENT_DATA_KEY = "parentData";
    public final static String CHILD_ORGANISM_ID_KEY = "childOrganismId";
    public final static String CHILD_COUNT_KEY = "childCount";

    //AWS Keys
    public final static String BREED_ORGANISMS_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:BreedOrganisms";

    //Organism Seed Constants
    public final static int NEW_ORGANISM_MAX_NAME_LENGTH = 10;
    public final static int NEW_ORGANISM_MIN_NAME_LENGTH = 3;

    //API Constants
    public final static String ORGANISM_API_HTTP_PROTOCOL= "https://";
    public final static String ORGANISM_API_HOST = "du1ejfbfoe.execute-api.us-west-2.amazonaws.com";
    public final static String ORGANISM_API_STAGE_DEV = "/DEV/";
    public final static String ORGANIAM_API_ENDPOINT = "/organism/";
    public final static String ORGANISM_CONSTRUCTED_ENDPOINT_DEV = ORGANISM_API_HTTP_PROTOCOL + ORGANISM_API_HOST + ORGANISM_API_STAGE_DEV + ORGANIAM_API_ENDPOINT;
}
