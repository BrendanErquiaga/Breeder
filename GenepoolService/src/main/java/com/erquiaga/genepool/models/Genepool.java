package com.erquiaga.genepool.models;

import java.util.ArrayList;
import java.util.List;

public class Genepool {
    public String id;
    public List<String> organismsInGenepool;
    public List<List<String>> genepoolGenerations;
    public int timesBred;

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

    public List<List<String>> getGenepoolGenerations() {
        return genepoolGenerations;
    }

    public void setGenepoolGenerations(List<List<String>> genepoolGenerations) {
        this.genepoolGenerations = genepoolGenerations;
    }

    public int getTimesBred() {
        return timesBred;
    }

    public void setTimesBred(int timesBred) {
        this.timesBred = timesBred;
    }

    public Genepool() {
        this.timesBred = 0;
        this.organismsInGenepool = new ArrayList<>();
        this.genepoolGenerations = new ArrayList<>();
    }

    public Genepool(String id, List<String> organismsInGenepool, List<List<String>> genepoolGenerations, int timesBred) {
        this.id = id;
        this.organismsInGenepool = organismsInGenepool;
        this.genepoolGenerations = genepoolGenerations;
        this.timesBred = timesBred;
    }

    public int genePoolSize() {
        return getOrganismsInGenepool().size();
    }

    public List<String> getLastGeneration() {
        if(genepoolGenerations.size() >= 1) {
            return genepoolGenerations.get(genepoolGenerations.size() - 1);
        } else {
            genepoolGenerations.add(new ArrayList<>());
            return getLastGeneration();
        }
    }

    public boolean addOrganismToGenepool(String organismId) {
        if(!organismIsInGenepool(organismId)) {
            organismsInGenepool.add(organismId);
            addOrganismToLastGeneration(organismId);
            return true;
        } else {
            return false;
        }
    }

    public boolean addOrganismToLastGeneration(String organismId) {
        List<String> lastGeneration = getLastGeneration();

        if(!lastGeneration.contains(organismId)) {
            lastGeneration.add(organismId);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOrganismFromGenerations(String organismId) {
        int generationIndex = getGenerationIndexForOrganism(organismId);

        if(generationIndex != -1) {
            genepoolGenerations.get(generationIndex).remove(organismId);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOrganismFromGenepool(String organismId) throws Exception {
        if(organismIsInGenepool(organismId)) {
            organismsInGenepool.remove(organismId);
            removeOrganismFromGenerations(organismId);
            return true;
        } else {
            return false;
        }
    }

    public void addGenerationToGenepool(List<String> newGeneration) {
        genepoolGenerations.add(new ArrayList<>());

        for (String newOrganismId : newGeneration) {
            addOrganismToGenepool(newOrganismId);
        }
    }

    public void removeGenerationFromGenepool(int generationIndex) throws Exception {
        if(genepoolGenerations.size() > generationIndex && genepoolGenerations.get(generationIndex) != null) {
            for(String organismId : genepoolGenerations.get(generationIndex)) {
                if(organismIsInGenepool(organismId)) {
                    organismsInGenepool.remove(organismId);
                }
            }

            genepoolGenerations.remove(generationIndex);
        }
    }

    public boolean organismIsInGenepool(String organismId) {
        return organismsInGenepool.contains(organismId);
    }

    public int getGenerationIndexForOrganism(String organismID) {

        if(genepoolGenerations.size() > 2) {
            for(int i = 0; i < genepoolGenerations.size(); i++) {
                if(genepoolGenerations.get(i).contains(organismID)) {
                    return i;
                }
            }
        } else if(genepoolGenerations.size() == 1) {
            return 0;
        } else {
            //There aren't any generations so... yea...
            return -1;
        }

        //Couldn't find the organism in a generation so yea...
        return -1;
    }

    public void bredNewGeneration() {
        timesBred++;
        genepoolGenerations.add(new ArrayList<>());
    }

    @Override
    public String toString() {
        return "Genepool{" +
                "id='" + id + '\'' +
                ", organismsInGenepool=" + organismsInGenepool +
                '}';
    }
}
