package com.example.pokeapitarea;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.pokeapitarea.utilities.NetworkUtils;


import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String myDataset;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    EditText mPokemonNumber;
    Button mSearchButton;
    TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bindView();

        mSearchButton.setOnClickListener(view -> {
            String pokemonNumber = mPokemonNumber.getText().toString().trim();
            if (pokemonNumber.isEmpty()) {
                mResultText.setText(R.string.text_nothing_to_show);
            } else {

                new FetchPokemonTask().execute(pokemonNumber);
            }
        });

        initRecycler();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    void bindView() {
        mPokemonNumber = findViewById(R.id.et_pokemon_number);
        mSearchButton = findViewById(R.id.bt_search_pokemon);
        mResultText = findViewById(R.id.tv_result);
    }

    protected void initRecycler(String pokemonInfo) {
        recyclerView = (RecyclerView) findViewById(R.id.rv_pokemon_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

    }


    private class FetchPokemonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... pokemonNumbers) {

            if (pokemonNumbers.length == 0) {
                return null;
            }

            String ID = pokemonNumbers[0];

            URL pokeAPI = NetworkUtils.buildUrl(ID);

            try {
                String result = NetworkUtils.getResponseFromHttpUrl(pokeAPI);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "";

            }
        }

        @Override
        protected void onPostExecute(String pokemonInfo) {

            initRecycler(pokemonInfo);
        }
    }
}