package com.guru.muhwezidenisliam.course_gudie.activity_classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.guru.muhwezidenisliam.course_gudie.R;
import com.guru.muhwezidenisliam.course_gudie.courses_adapter.AdapterSectionRecycler;
import com.guru.muhwezidenisliam.course_gudie.courses_adapter.Child;
import com.guru.muhwezidenisliam.course_gudie.courses_adapter.SectionHeader;
import com.guru.muhwezidenisliam.course_gudie.foldingcell.FoldingCellListAdapter;
import com.guru.muhwezidenisliam.course_gudie.foldingcell.Item;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


/**
 * Created by Muhwezi Denis Liam on 5/12/2017.
 */

public class Courses extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = Courses.class.getSimpleName();





    RecyclerView recyclerView;
    AdapterSectionRecycler adapterRecycler;
    List<SectionHeader> sectionHeaders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_courses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //setLayout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        PostResponseAsyncTask task = new PostResponseAsyncTask(this);
        task.execute("http://welshuganda.com/fypcg/retrieve_university_courses.php");

    }

    @Override
    public void processFinish(String s) {

        Log.d("string",s);

        try {
            JSONObject jsonResponse = new JSONObject(s);
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            Log.d("size","  " +jsonArray.length());


            sectionHeaders = new ArrayList<>();

            for(int index =0; index<jsonArray.length();index++)
            {


                JSONObject jsonObject = jsonArray.getJSONObject(index);

                String cut_off = jsonObject.getString("course_weight");
                String course_duration = jsonObject.getString("course_duration");
                String faculty_code = jsonObject.getString("course_faculty_code");
                String faculty_name = jsonObject.getString("course_faculty");
                String course_certification = jsonObject.getString("course_certification");
                String course_tuition = jsonObject.getString("course_tuition");
                String course_name = jsonObject.getString("course_name");

                if(faculty_code.equals("COCIS")) {
                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader

                    sectionHeaders.add(new SectionHeader(childList, "College of Computing and Information Sciences (CoCIS)", 1));


                }
                else if (faculty_code.equals("CEDAT"))
                {

                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader

                    sectionHeaders.add(new SectionHeader(childList, "College Of Engineering, Design, Art and Technology (CEDAT)", 2));

                }else if(faculty_code.equals("CAES"))
                {
                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader
                    sectionHeaders.add(new SectionHeader(childList, "College Of Agricultural And Environmental Sciences (CAES)", 3));

                }
                else if(faculty_code.equals("COBAMS"))
                {

                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader
                    sectionHeaders.add(new SectionHeader(childList, "College of Business and Management Sciences (CoBAMS)", 4));

                }
                else if(faculty_code.equals("CHS"))
                {

                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader
                    sectionHeaders.add(new SectionHeader(childList, "College of Health Sciences (CHS)", 5));

                }

                else if(faculty_code.equals("C0NAS"))
                {

                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader
                    sectionHeaders.add(new SectionHeader(childList, "College of Natural Sciences (CoNAS)", 6));

                }

                else if(faculty_code.equals("SOL"))
                {

                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader
                    sectionHeaders.add(new SectionHeader(childList, "School of Law (SoL)", 7));


                }
                else
                {

                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(course_name,course_certification,cut_off,course_duration,course_tuition));

                    //Create a List of SectionHeader DataModel implements SectionHeader

                    sectionHeaders.add(new SectionHeader(childList, "College of Humanities and Social Sciences (CHUSS)", 8));


                }


            }

            adapterRecycler = new AdapterSectionRecycler(this, sectionHeaders);
            recyclerView.setAdapter(adapterRecycler);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
