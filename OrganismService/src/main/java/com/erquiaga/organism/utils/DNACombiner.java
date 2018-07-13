package com.erquiaga.organism.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Random;

import static com.erquiaga.organism.utils.BreederConstants.*;
import static com.erquiaga.organism.utils.BreederUtils.getParmeterIfExists;

public class DNACombiner {

    public static JSONObject getOrganismDNA(JSONObject p1Data, JSONObject p2Data) {
        JSONObject dnaObject = new JSONObject();

        JSONArray p1Chromosomes = (JSONArray)((JSONObject)p1Data.get(DNA_KEY)).get(CHROMOSOMES_KEY);
        JSONArray p2Chromosomes = (JSONArray)((JSONObject)p2Data.get(DNA_KEY)).get(CHROMOSOMES_KEY);

        dnaObject.put(CHROMOSOMES_KEY, getNewChromosomes(p1Chromosomes, p2Chromosomes));

        return dnaObject;
    }

    private static JSONArray getNewChromosomes(JSONArray p1Chromosomes, JSONArray p2Chromosomes) {
        JSONArray newChromosomes = new JSONArray();

        for(Object p1ChromosomeObject : p1Chromosomes) {
            JSONObject p1Chromosome = (JSONObject) p1ChromosomeObject;
            String p1ID = getParmeterIfExists(p1Chromosome, CHROMOSOME_ID_KEY, "");

            if(!p1ID.equals("")) {
                for(Object p2ChromosomeObject : p2Chromosomes) {
                    JSONObject p2Chromosome = (JSONObject) p2ChromosomeObject;
                    String p2ID = getParmeterIfExists(p2Chromosome, CHROMOSOME_ID_KEY, "");

                    if(p1ID.equals(p2ID)) {
                        newChromosomes.add(createNewChromosome(p1Chromosome, p2Chromosome));
                    }
                }
            }
        }

        return newChromosomes;
    }

    private static JSONObject createNewChromosome(JSONObject p1Chromosome, JSONObject p2Chromosome) {
        JSONObject newChromosome = new JSONObject();
        JSONArray p1Traits = (JSONArray)p1Chromosome.get(TRAITS_KEY);
        JSONArray p2Traits = (JSONArray)p2Chromosome.get(TRAITS_KEY);

        newChromosome.put(CHROMOSOME_ID_KEY, p1Chromosome.get(CHROMOSOME_ID_KEY));
        newChromosome.put(TRAITS_KEY, getNewTraits(p1Traits, p2Traits));

        return newChromosome;
    }

    private static JSONArray getNewTraits(JSONArray p1Traits, JSONArray p2Traits) {
        JSONArray newTraits = new JSONArray();

        for(Object p1TraitObject : p1Traits) {
            JSONObject p1Trait = (JSONObject) p1TraitObject;
            String p1TraitId = getParmeterIfExists(p1Trait, TRAIT_ID_KEY, "");

            if(!p1TraitId.equals("")) {
                for(Object p2TraitObject : p2Traits) {
                    JSONObject p2Trait = (JSONObject) p2TraitObject;
                    String p2TraitId = getParmeterIfExists(p2Trait, TRAIT_ID_KEY, "");

                    if(p1TraitId.equals(p2TraitId)) {
                        newTraits.add(createNewTrait(p1Trait, p2Trait));
                    }
                }
            }
        }

        return newTraits;
    }

    private static JSONObject createNewTrait(JSONObject p1Trait, JSONObject p2Trait) {
        JSONObject newTrait = new JSONObject();
        Random r = new Random();

        newTrait.put(TRAIT_ID_KEY, p1Trait.get(TRAIT_ID_KEY));
        newTrait.put(GENE_A_KEY, p1Trait.get(getRandomGeneKey(r)));
        newTrait.put(GENE_B_KEY, p2Trait.get(getRandomGeneKey(r)));

        return newTrait;
    }

    private static String getRandomGeneKey(Random r) {
        return (r.nextBoolean()) ? GENE_A_KEY : GENE_B_KEY;
    }
}
