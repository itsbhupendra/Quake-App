
package com.example.quake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EarthQuakeAdapter mAdapter;
    private  static  final String sURL= "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=50";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuakeAsyncTask task=new QuakeAsyncTask();
        task.execute(sURL);
//        ArrayList<Earthquake> earthquakes=task.execute();
//        ArrayList<Earthquake> earthquakes =QueryUtils.extractEarthquakes();

//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//        // Create a new {@link ArrayAdapter} of earthquakes
//         mAdapter = new EarthQuakeAdapter(this,new ArrayList<Earthquake>());
////
////        ListView listView=(ListView) findViewById(R.id.list);
////        listView.setAdapter(adapter);
//
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(mAdapter);
//
//        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                Earthquake earthquake=mAdapter.getItem(position);
//                Uri earthQuakeUri=Uri.parse(earthquake.getmUrl());
//                Intent intent=new Intent(Intent.ACTION_VIEW,earthQuakeUri);
//                startActivity(intent);
//            }
//        });

    }

   public void updateUI(ArrayList<Earthquake> earthquakes){
       ListView earthquakeListView = (ListView) findViewById(R.id.list);

       // Create a new {@link ArrayAdapter} of earthquakes
       ArrayAdapter<Earthquake> adapter = new EarthQuakeAdapter(this,earthquakes);

       ListView listView=(ListView) findViewById(R.id.list);
       listView.setAdapter(adapter);
       earthquakeListView.setAdapter(adapter);
       earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Earthquake earthquake=adapter.getItem(position);
                Uri earthQuakeUri=Uri.parse(earthquake.getmUrl());
                Intent intent=new Intent(Intent.ACTION_VIEW,earthQuakeUri);
                startActivity(intent);
            }
        });
   }
    private class QuakeAsyncTask extends AsyncTask<String,Void,ArrayList<Earthquake>>{

        @Override
        protected ArrayList<Earthquake> doInBackground(String... strings) {

            if(strings.length<1 || strings[0]==null)
                return null;

            // Create URL from given String Query
            URL url= QueryUtils.createURL(sURL);

            // Create JSONResponse to save the JSON for further parsing
            String JSONResponse=null;

            try {
                JSONResponse= QueryUtils.makeHTTPReq(url);
            }

            catch (IOException exception){
                Log.e(LOG_TAG, "Error with creating AsyncTask", exception);
            }

            ArrayList<Earthquake> earthquakes= QueryUtils.extractEarthquakes(JSONResponse);

            return earthquakes;
        }

        // Tasks when the above asynctask gets completed

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            if(mAdapter!=null)
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (earthquakes != null && !earthquakes.isEmpty()) {
//                mAdapter.addAll(earthquakes);
//            }
//            updateUI(earthquakes);
        }
    }
}