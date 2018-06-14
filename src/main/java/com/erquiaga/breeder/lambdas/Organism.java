package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;

public class Organism {

    public String getOrganismHandler(int organismID, String organismType, Context context) {
        return "THIS SHOULD GET THIS ORGANISM: " + organismID;
    }

    public String createOrganismHandler(String organismJSONString, Context context) {
        return "THIS SHOULD CREATE AN ORGANISM THAT LOOKS LIKE THIS: " + organismJSONString;
    }

    public String deleteOrganismHandler(int organismID, String organismType, Context context) {
        return "THIS SHOULD DELETE THIS ORGANISM: " + organismID;
    }

    public String updateOrganismHandler(int organismID, String organismJSONString, Context context) {
        return "THIS SHOULD UPDATE THIS ORGANISM: " + organismID;
    }

    public String listOrganismsHandler(String organismType, Context context) {
        return "THIS SHOULD LIST ORGANISMS OF TYPE: " + organismType;
    }
}
