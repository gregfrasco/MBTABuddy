package com.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import mbta.Line;
import mbta.Lines;
import mbta.Station;
import mbta.mbtabuddy.R;

public class SearchActivity extends AppCompatActivity {

    ListView stationList;//Results list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Get our search term/string
        Intent intent = getIntent();

        //Get our ListView for results
        stationList = (ListView) findViewById(R.id.stationList);

        //If we were sent a search string
        if (intent.hasExtra("searchString")) {
            //Get the search Terms
            String searchString = (String) intent.getExtras().get("searchString");

            //Plop the search string into the search bar
            EditText searchBar = (EditText) findViewById(R.id.searchBar);
            searchBar.setText(searchString);

            //Perform the search and listView data
            new SearchStationsAsync(getBaseContext(), searchString).execute();
        }

        //Set up search button
        Button searchButton = (Button) findViewById(R.id.search_for_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the entered data
                EditText searchBar = (EditText) findViewById(R.id.searchBar);
                String searchTerms = searchBar.getText().toString();

                //Perform the search
                new SearchStationsAsync(getBaseContext(), searchTerms).execute();
            }
        });
    }

    class SearchStationsAsync extends AsyncTask<Void, Void, Void> {

        Context context;
        HashMap<String, String> matchStation;
        String searchString;

        public SearchStationsAsync(Context cont, String searchTerms){
            searchString = searchTerms;
            context = cont;
        }

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, String> results = new HashMap<>();

            //Search for the Input station
            for (Line line : Lines.getInstance().values()) {
                for (Station station : line.getStations()) {
                    if (station.getStationName().toLowerCase().contains(searchString.toLowerCase())) {
                        //Add to the map
                        results.put(station.getStationName(), station.getStationID());
                    }
                }
            }

            matchStation = results;
            return null;
        }

        @Override protected void onPostExecute(Void params)
        {
            //Now create an array list of just the station names to show to user
            ArrayList<String> matchStrings = new ArrayList<String>(matchStation.keySet());

            //Create the adapter with that list and give it to the ListView as adapter
            SearchResultListItemAdapter matches =
                    new SearchResultListItemAdapter(context, R.layout.search_item_station, matchStrings, matchStation);

            stationList.setAdapter(matches);

            //When user clicks a station on the results list, we open the StationActivity
            //with that station
            stationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String stationName = parent.getItemAtPosition(position).toString();
                    String stationId = matchStation.get(stationName);
                    for (Line line : Lines.getInstance().values()) {
                        for (Station station : line.getStations()) {
                            if (station.getStationID().equals(stationId)) {
                                Intent intent = new Intent(SearchActivity.this, StationActivity.class);
                                intent.putExtra("ID", stationId);
                                startActivity(intent);
                            }
                        }
                    }
                }
            });
        }
    }
}
