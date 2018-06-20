package com.erquiaga.breeder.utils;

public class BreederConstants {

    //Defaults
    public final static String DEFAULT_ORGANISM_TYPE = "organism";


    //JSON Keys
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

    //Generated JSON Keys
    public final static String PARENT_ONE_ID_KEY = "parentOneId";
    public final static String PARENT_TWO_ID_KEY = "parentTwoId";
    public final static String PARENT_ONE_DATA_KEY = "parentOneData";
    public final static String PARENT_TWO_DATA_KEY = "parentTwoData";
    public final static String CHILD_ORGANISM_ID_KEY = "childOrganismId";

    //AWS Keys
    public final static String BREEDER_S3_BUCKET = "breeder-collections";
    public final static String ORGANISM_FOLDER = "organisms/";
    public final static String ORGANISM_FILE_SUFFIX = ".json";
    public final static String SAVE_ORGANISM_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:SaveBreederOrganism";
    public final static String BREED_ORGANISMS_STEP_FUNCTION_ARN = "arn:aws:states:us-west-2:057419751866:stateMachine:Breeder_BreedOrganisms";
}
