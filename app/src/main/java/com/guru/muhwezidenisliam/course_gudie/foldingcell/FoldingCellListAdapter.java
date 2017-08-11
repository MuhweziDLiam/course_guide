package com.guru.muhwezidenisliam.course_gudie.foldingcell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.TextView;


import com.guru.muhwezidenisliam.course_gudie.R;
import com.guru.muhwezidenisliam.course_gudie.activity_classes.Directions;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import im.delight.android.location.SimpleLocation;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Item>{

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    private SimpleLocation simpleLocation;



    Double distance;

    String accomodate;

    Context context;

    String[] location_names;

//            = {"Kann Hostel","Miriam Hostel","Mitchel Hall"};

    HashMap<String,Double> values  = new HashMap<>();

    Double[][] LatLongAcc; /*=  {{0.336273,32.564927},{0.338628,32.567627},{0.333525,32.569685}};*/

    Double fa_Latitude, fa_Longitude, hostel_latitude, hostel_longitude;




    public FoldingCellListAdapter(Context context, List<Item> objects,Double[][] LatLongAcc, String[] location_names) {
        super(context, 0, objects);

        this.context = context;
        this.LatLongAcc = LatLongAcc;
        this.location_names = location_names;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // get item for selected view
        final Item item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.faculty_image = (ImageView) cell.findViewById(R.id.faculty_image);
            viewHolder.faculty_image_free = (ImageView) cell.findViewById(R.id.faculty_image_free);
            viewHolder.course_name = (TextView) cell.findViewById(R.id.course_name);
            viewHolder.course_name_free = (TextView) cell.findViewById(R.id.course_name_free);
            viewHolder.faculty_name = (TextView) cell.findViewById(R.id.faculty_name);
            viewHolder.faculty_name_free = (TextView) cell.findViewById(R.id.faculty_name_free);
            viewHolder.course_duration = (TextView) cell.findViewById(R.id.course_duration);
            viewHolder.course_duration_free = (TextView) cell.findViewById(R.id.course_duration_free);
            viewHolder.course_tuition = (TextView) cell.findViewById(R.id.course_tuition);
            viewHolder.course_tuition_free = (TextView) cell.findViewById(R.id.course_tuition_free);
            viewHolder.course_description = (TextView) cell.findViewById(R.id.course_description);
            viewHolder.course_certification = (TextView) cell.findViewById(R.id.course_certification_free);
            viewHolder.nearest_hostel = (TextView) cell.findViewById(R.id.accommodation);
            viewHolder.location = (ImageView) cell.findViewById(R.id.view_location_map);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        simpleLocation = new SimpleLocation(context);

        if (!simpleLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(context);
        }

        // bind data from selected element to view through view holder

        Picasso.with(context).load("http://welshuganda.com/fypcg/courseguide/images/"+item.getFaculty_image())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.faculty_image);

        viewHolder.course_tuition.setText(item.getCourse_tuition());
        viewHolder.course_name.setText(item.getCourse_name());
        viewHolder.course_duration.setText(item.getCourse_duration()+" years");
        viewHolder.faculty_name.setText(item.getFaculty_name());
        viewHolder.course_description.setText(item.getCourse_description());
        viewHolder.nearest_hostel.setText(item.getNearest_hostel());
        viewHolder.course_certification.setText(item.getCourse_certification());

        Picasso.with(context).load("http://welshuganda.com/fypcg/courseguide/images/"+item.getFaculty_image_free())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.faculty_image_free);

        viewHolder.course_tuition_free.setText(item.getCourse_tuition_free());
        viewHolder.course_name_free.setText(item.getCourse_name_free());
        viewHolder.course_duration_free.setText(item.getCourse_duration_free()+" years");
        viewHolder.faculty_name_free.setText(item.getFaculty_name_free());



        fa_Latitude = Double.parseDouble(item.getLatitude());

        fa_Longitude = Double.parseDouble(item.getLongitude());

        Log.d("size",""+location_names.length);

        for(int row = 0; row<location_names.length; row++)
        {
            for(int column = 0; column<1; column++)
            {

                hostel_latitude = LatLongAcc[row][column];
                hostel_longitude = LatLongAcc[row][column+1];

                Log.d("lat_long", "lat =>= "+hostel_latitude+ "long =>= "+hostel_longitude);
            }
            distance =
                    simpleLocation.calculateDistance(fa_Latitude,fa_Longitude
                            ,hostel_latitude,hostel_longitude);

            values.put(location_names[row],distance);

            Log.d("names",location_names[row]);

        }

        Map<String,Double> sortedMap = sortByValue(values);

        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            accomodate = key;
            break;
        }



        viewHolder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Directions.class);
                intent.putExtra("latitude",item.getLatitude());
                intent.putExtra("longitude",item.getLongitude());
                context.startActivity(intent);
            }
        });

        viewHolder.nearest_hostel.setText(accomodate+" hostel");

        return cell;
    }

    private static Map<String,Double> sortByValue (Map<String,Double> unsorted)
    {
        List<Map.Entry<String,Double>> new_list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(new_list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        Map<String,Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String,Double> entry: new_list)
        {
            sorted.put(entry.getKey(),entry.getValue());
        }

        return sorted;
    }



    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView faculty_image, faculty_image_free,location;
        TextView faculty_name,faculty_name_free;
        TextView course_duration,course_duration_free;
        TextView course_tuition,course_tuition_free;
        TextView course_name,course_name_free;
        TextView course_description,course_certification;
        TextView nearest_hostel;
    }

}
