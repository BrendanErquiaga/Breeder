package com.erquiaga.breeder.models;

public class Organism {
    public int id;
    public String type;
    public String breedingRulesJson;
    public String metadataJson;
    public String dnaJson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreedingRules() {
        return breedingRulesJson;
    }

    public void setBreedingRules(String breedingRulesJson) {
        this.breedingRulesJson = breedingRulesJson;
    }

    public String getMetadata() {
        return metadataJson;
    }

    public void setMetadata(String metadataJson) {
        this.metadataJson = metadataJson;
    }

    public String getDna() {
        return dnaJson;
    }

    public void setDna(String dnaJson) {
        this.dnaJson = dnaJson;
    }

    public Organism(int id, String type, String breedingRulesJson, String metadataJson, String dnaJson) {
        this.id = id;
        this.type = type;
        this.breedingRulesJson = breedingRulesJson;
        this.metadataJson = metadataJson;
        this.dnaJson = dnaJson;
    }

    public Organism() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getId());
        sb.append("Type: ").append(getType());
        sb.append("Breeding Rules: ").append(getBreedingRules());
        sb.append("Metadata: ").append(getMetadata());
        sb.append("DNA: ").append(getDna());
        return sb.toString();
    }
}
