package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView sandwichImage = findViewById(R.id.sandwich_image);

        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = new Sandwich();
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImage);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView mainName = findViewById(R.id.mainName);
        mainName.setText(sandwich.getMainName());

        TextView origin_tv = findViewById(R.id.origin_tv);
        TextView origin_label = findViewById(R.id.origin_label);

        String origin = sandwich.getPlaceOfOrigin();

        if (!origin.isEmpty()) {
            origin_tv.setText(origin);
        } else {
            origin_tv.setVisibility(View.GONE);
            origin_label.setVisibility(View.GONE);
        }

        TextView description_tv = findViewById(R.id.description_tv);
        description_tv.setText(sandwich.getDescription());

        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        ingredients_tv.setText(sandwich.getIngredients().toString().replace("[", "").replace("]", ""));

        TextView also_known_tv = findViewById(R.id.also_known_tv);
        TextView also_known_label = findViewById(R.id.also_known_as_label);

        List alsoKnownAs = sandwich.getAlsoKnownAs();
        if (!alsoKnownAs.isEmpty()) {
            also_known_tv.setText(alsoKnownAs.toString().replace("[", "").replace("]", ""));
        } else {
            also_known_tv.setVisibility(View.GONE);
            also_known_label.setVisibility(View.GONE);
        }
    }
}