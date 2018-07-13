package com.erquiaga.breeder.models;

import org.json.simple.JSONObject;

public class Organism {
    public String id;
    public String type;
    public JSONObject breedingRules;
    public JSONObject metadata;
    public JSONObject dna;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getBreedingRules() {
        return breedingRules;
    }

    public void setBreedingRules(JSONObject breedingRulesJson) {
        this.breedingRules = breedingRulesJson;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadataJson) {
        this.metadata = metadataJson;
    }

    public JSONObject getDna() {
        return dna;
    }

    public void setDna(JSONObject dnaJson) {
        this.dna = dnaJson;
    }

    public Organism(String type, JSONObject breedingRules, JSONObject metadata, JSONObject dna) {
        this.type = type;
        this.breedingRules = breedingRules;
        this.metadata = metadata;
        this.dna = dna;
    }

    public Organism() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getId());
        sb.append("Type: ").append(getType());
        sb.append("Breeding Rules: ").append(getBreedingRules().toJSONString());
        sb.append("Metadata: ").append(getMetadata().toJSONString());
        sb.append("DNA: ").append(getDna().toJSONString());
        return sb.toString();
    }
}
