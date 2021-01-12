package com.example.quake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthQuakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthQuakeAdapter(Activity Context, ArrayList<Earthquake> earthquakes){
        super(Context,0,earthquakes);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        View listitem=convertView;
        if(listitem==null){
            listitem= LayoutInflater.from(getContext()).inflate(R.layout.list_layout,parent,false);
        }

        Earthquake earthquake=getItem(position);

        TextView textView1=(TextView) listitem.findViewById(R.id.textView1);

        Double magni=earthquake.getmMagnitude();

        DecimalFormat decimalFormat=new DecimalFormat("0.0");

        String MagnitudeToDisplay= decimalFormat.format(magni);


        textView1.setText(MagnitudeToDisplay);

        String temp= earthquake.getmLocation();

        GradientDrawable magnitudeCircle = (GradientDrawable) textView1.getBackground();
//
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getmMagnitude());
//
//        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
//        int index= temp.indexOf()
//        String[] parts=temp.split(" ",3);
//        String parts1=parts[0];
//        String parts2=parts[1];
//        String parts3=parts[2];
//        String parts3=parts[2];
//        String parts4=parts[3];




        String temp2= temp.substring(0,7);
        TextView textView2=(TextView) listitem.findViewById(R.id.textView2);

//        textView2.setText(parts2);
        textView2.setText(earthquake.getmLocation());
//

//
        Date dateObject= new Date(earthquake.getmtimeInMilliseconds());

        SimpleDateFormat dateFormatter=new SimpleDateFormat("MMM DD, yyyy");

        String dateToDisplay=dateFormatter.format(dateObject);



//        String formattedDate = formatDate

        TextView textView3=(TextView) listitem.findViewById(R.id.textView3);

        textView3.setText(dateToDisplay);

        SimpleDateFormat timeFormatter=new SimpleDateFormat("h:mm a");

        String timeToDisplay=timeFormatter.format(dateObject);

        TextView textView4=(TextView) listitem.findViewById(R.id.textView4);

        textView4.setText(timeToDisplay);

        return listitem;



    }
}
