package com.erquiaga.breeder.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

import static com.erquiaga.breeder.utils.BreederConstants.*;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getOrganismJson;
import static com.erquiaga.breeder.utils.BreederRequestUtils.getParmeterIfExists;

public class GatherBreedingData {
    JSONParser parser = new JSONParser();

    public String gatherParentOneData(JSONObject breedingObject, Context context)  throws IOException, ParseException {
        String parentOneId = getParmeterIfExists(breedingObject, PARENT_ONE_ID_KEY, "");
        String childId = getParmeterIfExists(breedingObject, CHILD_ORGANISM_ID_KEY, "");

        return (!"".equals(parentOneId)) ? gatherOrganismData(parentOneId, childId, context) : "Parent One ID Missing";
    }

    public String gatherParentTwoData(JSONObject breedingObject, Context context)  throws IOException, ParseException {
        String parentTwoId = getParmeterIfExists(breedingObject, PARENT_TWO_ID_KEY, "");
        String childId = getParmeterIfExists(breedingObject, CHILD_ORGANISM_ID_KEY, "");

        return (!"".equals(parentTwoId)) ? gatherOrganismData(parentTwoId, childId, context) : "Parent Two ID Missing";
    }

    private String gatherOrganismData(String parentId, String childId, Context context) throws IOException, ParseException {
        LambdaLogger logger = context.getLogger();
        logger.log("Gathing Organism Data for id: " + parentId);

        JSONObject breedingData = new JSONObject();
        try {
            String parentDataString = getRequest("http://" + ORGANISM_API_HOST + ORGANISM_API_STAGE_DEV + ORGANISM_API_GET_ORGANISM_ENDPOINT + parentId);
            JSONObject parentOrganism = (JSONObject) parser.parse(parentDataString);

            breedingData.put(PARENT_ID_KEY, parentId);
            breedingData.put(PARENT_DATA_KEY, parentOrganism);
            breedingData.put(CHILD_ORGANISM_ID_KEY, childId);
        } catch (Exception e) {
            logger.log("Exception: " + e.toString());
            throw e;
        }

        return breedingData.toJSONString();
    }

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public String getRequest(String reqUrl) throws IOException {
        GenericUrl url = new GenericUrl(reqUrl);
        HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
        HttpResponse response = request.execute();
        System.out.println(response.getStatusCode());

        StringBuilder sb = new StringBuilder();
        InputStream is = response.getContent();
        int ch;
        while ((ch = is.read()) != -1) {
            //System.out.print((char) ch);
            sb.append(ch);
        }
        response.disconnect();

        return sb.toString();
    }
}
