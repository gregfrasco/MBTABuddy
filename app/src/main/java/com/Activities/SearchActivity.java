package com.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import mbta.Line;
import mbta.Lines;
import mbta.Station;
import mbta.mbtabuddy.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Set up the list
        Intent intent = getIntent();
        final HashMap<String, String> matchStation = (HashMap<String, String>) intent.getExtras().get("matches");
        ArrayList<String> matchStrings = new ArrayList<String>(matchStation.keySet());

        ArrayAdapter<String> matches =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matchStrings);
        ListView stationList = (ListView)findViewById(R.id.stationList);
        stationList.setAdapter(matches);

        stationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stationName = parent.getItemAtPosition(position).toString();
                String stationId = matchStation.get(stationName);

                for(Lines lines: Lines.values()){
                    Line line = new Line(lines);
                    for(Station station : line.getStations()){
                        if(station.getStationID().equals(stationId))
                        {
                            Intent intent = new Intent(SearchActivity.this, StationActivity.class);
                            intent.putExtra("ID", stationId);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        //Back button setup
        Button backButton = (Button) findViewById(R.id.backSearch);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
