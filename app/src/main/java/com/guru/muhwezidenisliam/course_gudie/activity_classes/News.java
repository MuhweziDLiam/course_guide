package com.guru.muhwezidenisliam.course_gudie.activity_classes;

/**
 * Created by Muhwezi Denis Liam on 7/2/2017.
 */

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.guru.muhwezidenisliam.course_gudie.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class News extends AppCompatActivity implements AsyncResponse {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @BindView(R.id.news_icon)
    ImageView news_icon;

    @BindView(R.id.news_title)
    TextView news_title;

    @BindView(R.id.news_content)
    TextView news_content;

    Bundle bd;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bd = getIntent().getExtras();

        position = bd.getInt("position");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("News Page");

        PostResponseAsyncTask task = new PostResponseAsyncTask(this);
        task.execute("http://welshuganda.com/fypcg/retrieve_post_id.php?id="+(position+1));

      //  dynamicToolbarColor();
        toolbarTextAppernce();
    }



    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    @Override
    public void processFinish(String s) {

        Log.d("string",s);

        try {
            JSONObject jsonResponse = new JSONObject(s);
            JSONArray jsonArray = jsonResponse.getJSONArray("result");

            for(int index =0; index<jsonArray.length();index++)
            {



                JSONObject jsonObject = jsonArray.getJSONObject(index);


                String post_image = jsonObject.getString("post_image");
                String post_title = jsonObject.getString("post_title");
                //String post_date = jsonObject.getString("post_date");
                String post_content = jsonObject.getString("post_content");

                news_title.setText(post_title);

                news_content.setText(post_content);

                Picasso.with(this).load("http://welshuganda.com/fypcg/courseguide/images/"+post_image)
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(news_icon);



            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}