package com.guru.muhwezidenisliam.course_gudie.activity_classes;

/**
 * Created by Muhwezi Denis Liam on 3/14/2017.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.guru.muhwezidenisliam.course_gudie.R;


/**
 * Created by Muhwezi Denis Liam on 8/6/2016.
 */
public class About extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.about);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
