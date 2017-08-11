package com.guru.muhwezidenisliam.course_gudie.activity_classes;


import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.guru.muhwezidenisliam.course_gudie.R;
import com.guru.muhwezidenisliam.course_gudie.autolabel.MyRecyclerAdapter;
import com.guru.muhwezidenisliam.course_gudie.autolabel.Person;
import com.guru.muhwezidenisliam.course_gudie.chat__head.ChatHeadService;
import com.guru.muhwezidenisliam.course_gudie.chat__head.Utils;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;

import java.util.List;

import cn.refactor.library.SmoothCheckBox;


/**
 * Created by Muhwezi Denis Liam on 4/21/2017.
 */

public class A_level extends AppCompatActivity {

    ArrayList<String> super_grades = new ArrayList<>();

    ArrayList<String> under_grades = new ArrayList<>();

    int failure = 0;

    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG = 5678;

    String [] next_arch;

    private final String KEY_INSTANCE_STATE_PEOPLE = "statePeople";


    Knn_classifier knn_classifier;

    Knn_classifier_one knn_classifier_one;

    Knn_classifier_two knn_classifier_two;

    int count_hashmap = 0;

    HashMap<String, String> subject_grade = new HashMap<>();

    String grade, grades, delete_value;

    int grade_value, grade_values;

    Double grade_count=0.0;

    ArrayList<HashMap<String, String>> arrayList_subject_grade = new ArrayList<>();


    String text;

    Double weight_count, O_level;

    private AutoLabelUI mAutoLabel;
    private List<Person> mPersonList;
    private MyRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    FloatingActionButton fab;

    Bundle o_level_bundle;

    public static A_level newInstance() {
        return new A_level();
    }

    public A_level() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.a_level_form);

        o_level_bundle = getIntent().getExtras();

        O_level = o_level_bundle.getDouble("O_level_results");



        findViews();
        setListeners();
        setRecyclerView();

        if (savedInstanceState != null) {
            List<Person> people = savedInstanceState.getParcelableArrayList(KEY_INSTANCE_STATE_PEOPLE);
            if (people != null) {
                mPersonList = people;
                adapter.setPeople(people);
                recyclerView.setAdapter(adapter);
            }
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && fab.isShown())
                {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                grade_count = 0.0;

                if (count_hashmap != 5) {

                    failure+=1;

                    Toast.makeText(A_level.this, "Please enter a total of 5 subjects", Toast.LENGTH_SHORT).show();
                } else {
                    for (String key : subject_grade.keySet()) {

                        grade = String.valueOf(subject_grade.get(key));

                        switch (grade.charAt(0)) {
                            case 'A':
                                grade_value = 6;
                                break;
                            case 'B':
                                grade_value = 5;
                                break;
                            case 'C':
                                grade_value = 4;
                                break;
                            case 'D':
                                grade_value = 3;
                                break;
                            case 'E':
                                grade_value = 2;
                                break;
                            case '0':
                                grade_value = 1;
                                break;
                            case 'F':
                                grade_value = 0;
                                break;
                            case 'P':
                                grade_value = 1;
                                break;
                            case 'X':
                                grade_value = 0;
                                break;
                        }

                        if (key.charAt(key.length() - 1) == 'E')
                        {
                            grade_count += (grade_value * 3.0);
                            super_grades.add(key.substring(0,key.length()-1));

                        } else if (key.charAt(key.length() - 1) == 'R')
                        {
                            grade_count += (grade_value * 2.0);
                            under_grades.add(key.substring(0,key.length()-1));
                        } else {
                            grade_count += 1;
                        }


                }
                weight_count = (O_level + grade_count);

                    knn_classifier = new Knn_classifier(super_grades,under_grades,weight_count);

                    knn_classifier_one = new Knn_classifier_one(super_grades,under_grades,weight_count);

                    knn_classifier_two = new Knn_classifier_two(super_grades,under_grades,weight_count);

                    knn_classifier_one.classify();

                    knn_classifier_two.classify();

                    next_arch = knn_classifier.classify();

                    for(String string: next_arch)
                    Log.d("check",string);

                AlertDialog.Builder dialog_Builder = new AlertDialog.Builder(A_level.this);
                LayoutInflater inflate = getLayoutInflater();
                final View dialog_View = inflate.inflate(R.layout.results_dialog, null);
                dialog_Builder.setView(dialog_View);

                final TextView results_answer = (TextView) dialog_View.findViewById(R.id.results_answer);

                String text_now = "Total weight is: " + String.valueOf(weight_count);
                results_answer.setText(text_now);

                dialog_Builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog build = dialog_Builder.create();
                build.show();

                    Intent find_my_courses = new Intent(A_level.this,Find_Courses.class);
                    find_my_courses.putExtra("student_weight",weight_count);
                    find_my_courses.putExtra("courses_weight",next_arch);
                    finish();
                    startActivity(find_my_courses);
            }
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void itemListClicked(final int position) {
        final Person person = mPersonList.get(position);
        final boolean[] isSelected = {person.isSelected()};
        final boolean[] success = new boolean[1];

        Log.d("selected",String.valueOf(person.isSelected()));

        if (isSelected[0]) {

            success[0] = mAutoLabel.removeLabel(position);
            remove_it_click(person);
        }

        else {

            add_(person,position,success);
        }
        
        if (success[0]) {
            adapter.setItemSelected(position, !isSelected[0]);
        }
    }

    private void setListeners() {
        mAutoLabel.setOnLabelsCompletedListener(new AutoLabelUI.OnLabelsCompletedListener() {
            @Override
            public void onLabelsCompleted() {
               // Snackbar.make(recyclerView, "Completed!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAutoLabel.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {

                remove_it(position);
                adapter.setItemSelected(position, false);
            }
        });

        mAutoLabel.setOnLabelsEmptyListener(new AutoLabelUI.OnLabelsEmptyListener() {
            @Override
            public void onLabelsEmpty() {
             //   Snackbar.make(recyclerView, "EMPTY!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAutoLabel.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(View v) {

            }
        });

    }

    private void findViews() {

        mAutoLabel = (AutoLabelUI) findViewById(R.id.label_view);
        mAutoLabel.setBackgroundResource(R.drawable.round_corner_background);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab =(FloatingActionButton) findViewById(R.id.fab_itemo);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        mPersonList = new ArrayList<>();

        //Populate list
        List<String> names = Arrays.asList(getResources().getStringArray(R.array.names_A));
        List<String> ages = Arrays.asList(getResources().getStringArray(R.array.status_A));
        TypedArray photos = getResources().obtainTypedArray(R.array.photos);

        for (int i = 0; i < names.size(); i++) {
            mPersonList.add(new Person(names.get(i), ages.get(i), photos.getResourceId(i, -1), false));
        }

        photos.recycle();

        adapter = new MyRecyclerAdapter(mPersonList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                itemListClicked(position);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_INSTANCE_STATE_PEOPLE,
                (ArrayList<? extends Parcelable>) adapter.getPeople());

    }

    public void remove_it(int position)
    {
        if (arrayList_subject_grade.size()!=0)
        {
            final Person persons = mPersonList.get(position);

            if (arrayList_subject_grade.get(0).get(persons.getName() + "E") != null) {
                grades = String.valueOf(arrayList_subject_grade.get(0).get(persons.getName() + "E"));
                subject_grade.remove(persons.getName() + "E");
                delete_value = "E";
            } else if (arrayList_subject_grade.get(0).get(persons.getName() + "R") != null) {
                grades = String.valueOf(arrayList_subject_grade.get(0).get(persons.getName() + "R"));
                subject_grade.remove(persons.getName() + "R");
                delete_value = "R";
            } else {
                grades = String.valueOf(arrayList_subject_grade.get(0).get(persons.getName() + "D"));
                subject_grade.remove(persons.getName() + "D");
                delete_value = "D";
            }

            count_hashmap -= 1;

            switch (grades.charAt(0)) {
                case 'A':
                    grade_values = 6;
                    break;
                case 'B':
                    grade_values = 5;
                    break;
                case 'C':
                    grade_values = 4;
                    break;
                case 'D':
                    grade_values = 3;
                    break;
                case 'E':
                    grade_values = 2;
                    break;
                case '0':
                    grade_values = 1;
                    break;
                case 'F':
                    grade_values = 0;
                    break;
                case 'P':
                    grade_values = 0;
                    break;
                case 'X':
                    grade_values = -1;
                    break;
            }


            if (delete_value.equals("E")) {
                grade_count = grade_count - (grade_values * 3.0);
            } else if (delete_value.equals("R")) {
                grade_count -= (grade_values * 2.0);
            } else {
                grade_count -= 1;
            }

        }
    }

    public  void remove_it_click(Person person)
    {
        if (arrayList_subject_grade.size()!=0)
        {

            if (arrayList_subject_grade.get(0).get(person.getName() + "E") != null) {
                grades = String.valueOf(arrayList_subject_grade.get(0).get(person.getName() + "E"));
                subject_grade.remove(person.getName() + "E");
                delete_value = "E";
            } else if (arrayList_subject_grade.get(0).get(person.getName() + "R") != null) {
                grades = String.valueOf(arrayList_subject_grade.get(0).get(person.getName() + "R"));
                subject_grade.remove(person.getName() + "R");
                delete_value = "R";
            } else {
                grades = String.valueOf(arrayList_subject_grade.get(0).get(person.getName() + "D"));
                subject_grade.remove(person.getName() + "D");
                delete_value = "D";
            }

            count_hashmap -= 1;

            switch (grades.charAt(0)) {
                case 'A':
                    grade_values = 6;
                    break;
                case 'B':
                    grade_values = 5;
                    break;
                case 'C':
                    grade_values = 4;
                    break;
                case 'D':
                    grade_values = 3;
                    break;
                case 'E':
                    grade_values = 2;
                    break;
                case 'O':
                    grade_values = 1;
                    break;
                case 'F':
                    grade_values = 0;
                    break;
                case 'P':
                    grade_values = 1;
                    break;
                case 'X':
                    grade_values = 1;
                    break;

            }


            if (delete_value.equals("E")) {
                grade_count = grade_count - (grade_values * 3.0);
            } else if (delete_value.equals("R")) {
                grade_count -= (grade_values * 2.0);
            } else {
                grade_count -= grade_values;
            }
            }

    }

    public void add_(final Person person, final int position, final boolean[] success)
    {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.grade_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        final SmoothCheckBox essential =(SmoothCheckBox) dialogView.findViewById(R.id.essential);

        final  SmoothCheckBox relevant = (SmoothCheckBox) dialogView.findViewById(R.id.relevant);

        final SmoothCheckBox desirable = (SmoothCheckBox) dialogView.findViewById(R.id.desirable);

        essential.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox smoothCheckBox, boolean b) {
                if(b)
                {
                    relevant.setEnabled(false);
                    desirable.setEnabled(false);
                }
                else
                {
                    relevant.setEnabled(true);
                    desirable.setEnabled(true);
                }
            }
        });

        relevant.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox smoothCheckBox, boolean b) {

                if(b)
                {
                    essential.setEnabled(false);
                    desirable.setEnabled(false);
                }
                else
                {
                    essential.setEnabled(true);
                    desirable.setEnabled(true);
                }
            }
        });

        desirable.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox smoothCheckBox, boolean b) {

                if(b)
                {
                    relevant.setEnabled(false);
                    essential.setEnabled(false);
                }
                else
                {
                    relevant.setEnabled(true);
                    essential.setEnabled(true);
                }
            }
        });

        dialogBuilder.setTitle("Grade dialog");
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                text = edt.getText().toString();

                if (text.isEmpty()) {
                    failure+=1;
                    Toast.makeText(A_level.this,"Please enter a grade",Toast.LENGTH_SHORT).show();
                    success[0] = mAutoLabel.removeLabel(position);
                    adapter.setItemSelected(position, false);
                    return;
                }

                String correct_grade = "ABCDEOF";
                String correct_grade_desirable = "PX";




                if(correct_grade.contains(text) && essential.isChecked()) {
                    subject_grade.put(person.getName() + "E", text);

                    count_hashmap += 1;
                    arrayList_subject_grade.add(subject_grade);

                } else if(correct_grade.contains(text) && relevant.isChecked())
                {
                    subject_grade.put(person.getName() + "R", text);
                    count_hashmap += 1;
                    arrayList_subject_grade.add(subject_grade);

                }
                else if(!correct_grade.contains(text) && essential.isChecked())
                {
                    failure+=1;
                    //   edt.setError("Please enter a valid grade(A,B,C,D,E,O,F)");
                    Toast.makeText(A_level.this,"Please enter a valid grade(A,B,C,D,E,O,F)",Toast.LENGTH_LONG).show();
                    success[0] = mAutoLabel.removeLabel(position);
                    adapter.setItemSelected(position, false);

                }
                else if(!correct_grade.contains(text) && relevant.isChecked())
                {
                    //   edt.setError("Please enter a valid grade(A,B,C,D,E,O,F)");
                    failure+=1;
                    Toast.makeText(A_level.this,"Please enter a valid grade(A,B,C,D,E,O,F)",Toast.LENGTH_LONG).show();
                    success[0] = mAutoLabel.removeLabel(position);
                    adapter.setItemSelected(position, false);

                }
                else if(correct_grade_desirable.contains(text) && desirable.isChecked())
                {
                    subject_grade.put(person.getName() + "D", text);

                    count_hashmap += 1;
                    arrayList_subject_grade.add(subject_grade);
                }
                else
                {
                    //  edt.setError("Please enter a valid grade(P for Pass or X for fail");
                    failure+=1;
                    Toast.makeText(A_level.this,"Please enter a valid grade(P for Pass or X for fail)",Toast.LENGTH_LONG).show();
                    success[0] = mAutoLabel.removeLabel(position);
                    adapter.setItemSelected(position, false);
                }


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                success[0] = mAutoLabel.removeLabel(position);
                adapter.setItemSelected(position, false);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

        success[0] = mAutoLabel.addLabel(person.getName(), position);
    }


    private void startChatHead(){
        startService(new Intent(A_level.this, ChatHeadService.class));
    }
    private void showChatHeadMsg(){
        java.util.Date now = new java.util.Date();
        String str = "test by henry  " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        Intent it = new Intent(A_level.this, ChatHeadService.class);
        it.putExtra(Utils.EXTRA_MSG, str);
        startService(it);
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(A_level.this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(A_level.this)) {
                needPermissionDialog(requestCode);
            }else{
                startChatHead();
            }

        }else if(requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG){
            if (!Utils.canDrawOverlays(A_level.this)) {
                needPermissionDialog(requestCode);
            }else{
                showChatHeadMsg();
            }

        }

    }

}
