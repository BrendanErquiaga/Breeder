package com.erquiaga.genepool.models;

import java.util.ArrayList;
import java.util.List;

public class Genepool {
    public String id;
    public List<OrganismGenepoolSpotIdentifier> organismsInGenepool;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrganismGenepoolSpotIdentifier> getOrganismsInGenepool() {
        return organismsInGenepool;
    }

    public void setOrganismsInGenepool(List<OrganismGenepoolSpotIdentifier> organismsInGenepool) {
        this.organismsInGenepool = organismsInGenepool;
    }

    public int genePoolSize() {
        return getOrganismsInGenepool().size();
    }

    public Genepool() {
        this.organismsInGenepool = new ArrayList<>();
    }

    public Genepool(String id, List<OrganismGenepoolSpotIdentifier> organismsInGenepool) {
        this.id = id;
        this.organismsInGenepool = organismsInGenepool;
    }

    public boolean addOrganismToGenepool(String organismId) {
        if(!organismIsInGenepool(organismId)) {
            organismsInGenepool.add(new OrganismGenepoolSpotIdentifier(organismId, genePoolSize()));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOrganismFromGenepool(String organismId) throws Exception {
        if(organismIsInGenepool(organismId)) {
            organismsInGenepool.remove(getOrganismIdentifier(organismId));
            return true;
        } else {
            return false;
        }
    }

    public boolean organismIsInGenepool(String organismId) {
        for(OrganismGenepoolSpotIdentifier organism : organismsInGenepool) {
            if(organism.getOrganismId().equals(organismId)) {
                return true;
            }
        }

        return false;
    }

    public int getOrganismPositionInGenepool(String organismId) throws Exception {
        for(OrganismGenepoolSpotIdentifier organism : organismsInGenepool) {
            if(organism.getOrganismId().equals(organismId)) {
                return organism.getPositionInGenepool();
            }
        }

        throw new Exception("Organism not in genepool");
    }

    public OrganismGenepoolSpotIdentifier getOrganismIdentifier(String organismId) throws Exception {
        for(OrganismGenepoolSpotIdentifier organism : organismsInGenepool) {
            if(organism.getOrganismId().equals(organismId)) {
                return organism;
            }
        }

        throw new Exception("Organism not in genepool");
    }



    @Override
    public String toString() {
        return "Genepool{" +
                "id='" + id + '\'' +
                ", organismsInGenepool=" + organismsInGenepool +
                '}';
    }
}
