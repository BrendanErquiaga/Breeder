package com.erquiaga.genepool.models;

public class OrganismGenepoolSpotIdentifier {
    public String organismId;
    public int positionInGenepool;

    public String getOrganismId() {
        return organismId;
    }

    public void setOrganismId(String organismId) {
        this.organismId = organismId;
    }

    public int getPositionInGenepool() {
        return positionInGenepool;
    }

    public void setPositionInGenepool(int positionInGenepool) {
        this.positionInGenepool = positionInGenepool;
    }

    public OrganismGenepoolSpotIdentifier() {
    }

    public OrganismGenepoolSpotIdentifier(String organismId, int positionInGenepool) {
        this.organismId = organismId;
        this.positionInGenepool = positionInGenepool;
    }

    @Override
    public String toString() {
        return "OrganismGenepoolSpotIdentifier{" +
                "organismId='" + organismId + '\'' +
                ", positionInGenepool=" + positionInGenepool +
                '}';
    }
}
