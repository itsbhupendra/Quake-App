package com.example.quake;
import android.text.TextUtils;
import  android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */




    private QueryUtils() {
    }

    // Function to return URL by taking string query as input
    public static URL createURL(String sURL){
        URL url=null;
        try{
            url=new URL(sURL);
        }
        catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }

        return url;
    }


    public static String makeHTTPReq(URL url) throws IOException{
        if(url==null)
            return  null;

        String JSONResponse=null;

        HttpURLConnection httpURLConnection= null;
        InputStream inputStream=null;

        try{
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(100000);
            httpURLConnection.setConnectTimeout(1500000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // After connecting to the server check if the connection has been secured successfully or not

            if(httpURLConnection.getResponseCode()==200){
                inputStream=httpURLConnection.getInputStream();

                // call function readFromStream to extract JSON from inputStream

                JSONResponse = readFromStream(inputStream);
            }
        }

        catch (IOException exception){
            Log.e(LOG_TAG, "Error with creating JSON", exception);
        }

        finally {

            // After we get our data from the server, connection should be disconnected
            if(httpURLConnection!=null)
                httpURLConnection.disconnect();

            // inputStream must be cleared so that it can be used next time again
            if(inputStream!=null)
                inputStream.close();
        }

        return JSONResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException{

        StringBuilder output=new StringBuilder();

        if(inputStream!=null){

            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while(line!=null){
                output.append(line);
                line= bufferedReader.readLine();
            }

        }

        return  output.toString();
    }
    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes( String SAMPLE_JSON_RESPONSE) {

        if (TextUtils.isEmpty(SAMPLE_JSON_RESPONSE)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject root=new JSONObject(SAMPLE_JSON_RESPONSE);

            JSONArray features= root.getJSONArray("features");

            for(int i=0;i<features.length();i++){

                JSONObject ithobj= features.getJSONObject(i);
                JSONObject properties=ithobj.getJSONObject("properties");

                String magnitude= properties.getString("mag");

                String place = properties.getString("place");
                String url = properties.getString("url");

                String date= properties.getString("time");

                long timeInMilliSecs= Long.parseLong(date,10);

                Double mMagnitude=properties.getDouble("mag");

//                Date dateobj=new Date(timeInMilliSecs);
//
//                SimpleDateFormat dateFormatter=new SimpleDateFormat("MMM DD, yyyy");
//
//                String dateToDisplay=dateFormatter.format(dateobj);

                earthquakes.add(new Earthquake(mMagnitude,place,timeInMilliSecs,url));
            }

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}
//public class QueryUtils {
//}
