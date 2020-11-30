package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_NAME = "name" ;
    private static final String JSON_MAIN_NAME = "mainName" ;
    private static final String JSON_ALSO_KNOWN_AS = "alsoKnownAs" ;
    private static final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin" ;
    private static final String JSON_DESCRIPTION = "description" ;
    private static final String JSON_IMAGE = "image" ;
    private static final String JSON_INGREDIENTS = "ingredients" ;

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJSON = new JSONObject(json);
        JSONObject nameObject = sandwichJSON.getJSONObject(JSON_NAME);
        String name =  nameObject.getString(JSON_MAIN_NAME);
        JSONArray alsoKnownAsArray =  nameObject.getJSONArray(JSON_ALSO_KNOWN_AS);

        List<String> alsoKnownAs = new ArrayList<>();
        if (alsoKnownAsArray != null) {
            int len = alsoKnownAsArray.length();
            for (int i=0;i<len;i++){
                alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
            }
        }

        String placeOfOrigin =  sandwichJSON.getString(JSON_PLACE_OF_ORIGIN);
        String description =  sandwichJSON.getString(JSON_DESCRIPTION);
        String image =  sandwichJSON.getString(JSON_IMAGE);

        JSONArray ingredientsArray = sandwichJSON.getJSONArray(JSON_INGREDIENTS);
        List<String> ingredients = new ArrayList<>();
        for(int i = 0; i < ingredientsArray.length(); i++){
            ingredients.add(ingredientsArray.getString(i));
        }
        return new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
