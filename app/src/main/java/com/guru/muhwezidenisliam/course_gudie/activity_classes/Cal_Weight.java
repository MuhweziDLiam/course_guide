package com.guru.muhwezidenisliam.course_gudie.activity_classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.Toast;


import com.guru.muhwezidenisliam.course_gudie.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.ghyeok.stickyswitch.widget.StickySwitch;

/**
 * Created by Muhwezi Denis Liam on 3/27/2017.
 */

public class Cal_Weight extends AppCompatActivity {

    FloatingActionButton next_olevel;

    String direction_switch;

    Boolean toggle_switch=false;

    @BindView(R.id.student_name)
    EditText student_name;

    @BindView(R.id.student_email)
    EditText student_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.results_form);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        next_olevel = (FloatingActionButton) findViewById(R.id.fab_item);

        next_olevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {
                    failed();
                    return;
                }

                Log.d("value od string",direction_switch);
                Intent o_level = new Intent(Cal_Weight.this,O_level.class);
                o_level.putExtra("direction",direction_switch);
                startActivity(o_level);
            }
        });

        StickySwitch stickySwitch = (StickySwitch) findViewById(R.id.sticky_switch);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {
                Log.d("direction", "Now Selected : " + direction.name());

                direction_switch = direction.name();

                toggle_switch = true;
            }

        });

    }

    public void failed() {

        student_name.setText("");

        student_email.setText("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validate() {
        boolean valid = true;

        String name = student_name.getText().toString();

        String email = student_email.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            student_name.setError("Please fill this field");
            valid = false;
        } else {
            student_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            student_email.setError("Please enter valid email");
            valid = false;
        }
        else {
            student_email.setError(null);
        }

        if(!toggle_switch)
        {
            Toast.makeText(this,"Please toggle gender switch",Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }


}
