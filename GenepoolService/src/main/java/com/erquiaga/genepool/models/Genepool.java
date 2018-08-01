package com.erquiaga.genepool.models;

import java.util.ArrayList;
import java.util.List;

public class Genepool {
    public String id;
    public List<String> organismsInGenepool;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOrganismsInGenepool() {
        return organismsInGenepool;
    }

    public void setOrganismsInGenepool(List<String> organismsInGenepool) {
        this.organismsInGenepool = organismsInGenepool;
    }

    public int genePoolSize() {
        return getOrganismsInGenepool().size();
    }

    public Genepool() {
        this.organismsInGenepool = new ArrayList<>();
    }

    public Genepool(String id, List<String> organismsInGenepool) {
        this.id = id;
        this.organismsInGenepool = organismsInGenepool;
    }

    public boolean addOrganismToGenepool(String organismId) {
        if(!organismIsInGenepool(organismId)) {
            organismsInGenepool.add(organismId);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOrganismFromGenepool(String organismId) throws Exception {
        if(organismIsInGenepool(organismId)) {
            organismsInGenepool.remove(organismId);
            return true;
        } else {
            return false;
        }
    }

    public boolean organismIsInGenepool(String organismId) {
        return organismsInGenepool.contains(organismId);
    }

    @Override
    public String toString() {
        return "Genepool{" +
                "id='" + id + '\'' +
                ", organismsInGenepool=" + organismsInGenepool +
                '}';
    }
}
