package com.guru.muhwezidenisliam.course_gudie.activity_classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.guru.muhwezidenisliam.course_gudie.R;
import com.guru.muhwezidenisliam.course_gudie.foldingcell.FoldingCellListAdapter;
import com.guru.muhwezidenisliam.course_gudie.foldingcell.Item;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.ramotion.foldingcell.FoldingCell;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Example of using Folding Cell with ListView and ListAdapter
 */
public class Find_Courses extends AppCompatActivity implements AsyncResponse{

    Bundle bd;

   String list_course;

    String [] course_array;

    String[] location_names;

    Double[][] LatLongAcc;

    ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);
        // get our list view
         theListView = (ListView) findViewById(R.id.mainListView);

        // prepare elements to display


        bd = getIntent().getExtras();

        course_array = bd.getStringArray("courses_weight");

     //   position = bd.getInt("courses_position");

        list_course = single_string(course_array);



        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)

        PostResponseAsyncTask task_t = new PostResponseAsyncTask(this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                Log.d("string",s);

                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    JSONArray jsonArray = jsonResponse.getJSONArray("result");

                    location_names = new String[jsonArray.length()];

                    LatLongAcc =  new Double[jsonArray.length()][2];

                    for(int index =0; index<jsonArray.length();index++) {


                        JSONObject jsonObject = jsonArray.getJSONObject(index);

                        String hostel_name = jsonObject.getString("hostel_name");
                        String hostel_lat = jsonObject.getString("hostel_lat");
                        String hostel_long = jsonObject.getString("hostel_long");

                        location_names[index] = hostel_name;

                            for( int in=0; in<1; in++)
                            {
                                LatLongAcc[index][in] = Double.parseDouble(hostel_lat);
                                LatLongAcc[index][in+1] = Double.parseDouble(hostel_long);
                            }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task_t.execute("http://welshuganda.com/fypcg/retrieve_hostels.php");

        PostResponseAsyncTask task = new PostResponseAsyncTask(this);
        task.execute("http://welshuganda.com/fypcg/retrieve_courses.php?list_course="+list_course);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String single_string(String[] strs) {

        String joined = Arrays.toString( strs );

        String joinedMinusBrackets = joined.substring( 1, joined.length() - 1);

        joinedMinusBrackets = joinedMinusBrackets.replace(" ","");

        /*StringBuilder returned = new StringBuilder();

        for(Character c: joinedMinusBrackets.toCharArray())
        {

            if(!Character.isWhitespace(c))
            {
                returned.append(c);
            }
        }*/

        // String.split()
       /* String[] resplit = joinedMinusBrackets.split( ", ");

        for ( String s : resplit ) {
            System.out.println( s );
        }
        (returned.toString())
         */


        return joinedMinusBrackets;

    }

    @Override
    public void processFinish(String s) {

        Log.d("string",s);

        try {
            JSONObject jsonResponse = new JSONObject(s);
            JSONArray jsonArray = jsonResponse.getJSONArray("result");
          //  boolean success = jsonResponse.getBoolean("success");

         //   if (success) {

            Drawable cit = getResources().getDrawable( R.drawable.cit );

            Bitmap faculty_profile = BitmapFactory.decodeResource(getResources(),R.drawable.cit);

            ArrayList<Item> items = new ArrayList<>();

                for(int index =0; index<jsonArray.length();index++)
                {



                    JSONObject jsonObject = jsonArray.getJSONObject(index);


                    //String faculty_image = jsonObject.getString("course_image");
                    String faculty_name = jsonObject.getString("course_faculty");
                    String course_duration = jsonObject.getString("course_duration");
                    String course_certification = jsonObject.getString("course_certification");
                    String course_tuition = jsonObject.getString("course_tuition");
                    String course_name = jsonObject.getString("course_name");
                    String course_description = jsonObject.getString("course_description");
                    String course_location_latitude =  jsonObject.getString("course_location_latitude");
                    String course_location_longitude = jsonObject.getString("course_location_longitude");
                    String faculty_image = jsonObject.getString("course_image");


                 //   ByteArrayInputStream imageStream = new ByteArrayInputStream(Base64.decode(faculty_image.getBytes(), Base64.DEFAULT));
                  //  Bitmap faculty_profile = BitmapFactory.decodeStream(imageStream);

                    Log.d("output_string",faculty_name+" "+course_duration+ " "+course_certification+ " "+
                            course_tuition+ " "+ course_name+" "+ course_name+ " "+ course_description);

                    items.add(new Item(faculty_image,faculty_image,faculty_name,faculty_name,course_duration,course_duration,
                    course_tuition,course_tuition,course_name,course_name,course_description,course_certification,"Home is best",course_location_latitude,course_location_longitude));

            //    }

            }

            final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items,LatLongAcc,location_names);

            // set elements to adapter
            theListView.setAdapter(adapter);

            // set on click event listener to list view
            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    // toggle clicked cell state
                    ((FoldingCell) view).toggle(false);
                    // register in adapter that state for selected cell is toggled
                    adapter.registerToggle(pos);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
