package com.guru.muhwezidenisliam.course_gudie.activity_classes;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.guru.muhwezidenisliam.course_gudie.R;
import com.guru.muhwezidenisliam.course_gudie.autolabel.MyRecyclerAdapter;
import com.guru.muhwezidenisliam.course_gudie.autolabel.Person;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Muhwezi Denis Liam on 4/21/2017.
 */

public class O_level extends AppCompatActivity {

    FloatingActionButton a_fab;

    EditText distinctions,credits,passes;

    int Distinctions,Credits,Passes,Total;

    Boolean validity=true, gender = false;

    Bundle bd;

    double final_o_level;

    public O_level() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.o_level_form);


        bd = getIntent().getExtras();

        if(bd.getString("direction").equals("RIGHT"))
        {
            gender = true;
        }

        a_fab = (FloatingActionButton) findViewById(R.id.a_level_fab);

        distinctions = (EditText) findViewById(R.id.distinctions);
        credits = (EditText) findViewById(R.id.credits);
        passes = (EditText) findViewById(R.id.passes);





        a_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(distinctions.getText().toString().isEmpty())
                {
                    distinctions.setError("Please fill in this field");
                    validity=false;
                }
                else if (credits.getText().toString().isEmpty())
                {
                    credits.setError("Please fill in this field");
                    validity=false;
                }
                else if(passes.getText().toString().equals(""))
                {
                    passes.setError("Please fill in this field");
                    validity=false;
                }
                else
                {


                    Distinctions = Integer.parseInt(distinctions.getText().toString());
                    Credits = Integer.parseInt(credits.getText().toString());
                    Passes = Integer.parseInt(passes.getText().toString());

                    Total =Distinctions+Credits+Passes;
                    if(Total>10 || Total <10 )
                    {
                        Toast.makeText(O_level.this,"Please enter a total of 10",Toast.LENGTH_SHORT).show();

                        distinctions.setText("");
                        credits.setText("");
                        passes.setText("");

                        validity = false;
                    }
                    else
                    {
                        validity = true;
                        final_o_level = (Distinctions*0.3)+(Credits*0.2)+(Passes*0.1);

                       // Toast.makeText(O_level.this, String.valueOf(final_o_level),Toast.LENGTH_SHORT).show();

                    }
                }

                if(validity) {

                    if(gender==true)
                    {
                        final_o_level+=1.5;
                    }

                    Intent go_to_next = new Intent(O_level.this,A_level.class);
                    go_to_next.putExtra("O_level_results",final_o_level);
                    startActivity(go_to_next);
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
}
