package com.guru.muhwezidenisliam.course_gudie.activity_classes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.github.florent37.materialviewpager.MaterialViewPager;
import com.guru.muhwezidenisliam.course_gudie.R;

import com.guru.muhwezidenisliam.course_gudie.model.OrderStatus;
import com.guru.muhwezidenisliam.course_gudie.model.Orientation;
import com.guru.muhwezidenisliam.course_gudie.model.TimeLineModel;

import com.guru.muhwezidenisliam.course_gudie.timeline_view.TimeLineAdapter;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.ramotion.expandingcollection.ECPagerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Home extends AppCompatActivity implements AsyncResponse {
    //save our header or result
    private AccountHeader headerResult = null;

    private ECPagerView ecPagerView;

    Random random = new Random();

    ImageView header_iamge;

    HashMap<String, Bitmap> file_maps = new HashMap<>();
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    private MaterialViewPager mViewPager;
    private Toolbar toolbar;


    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation;
    private boolean mWithLinePadding;


    private CollapsingToolbarLayout collapsingToolbarLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        header_iamge = (ImageView) findViewById(R.id.header_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));


        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

       // setTitle("Course Guide");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);


        PostResponseAsyncTask task = new PostResponseAsyncTask(this);
        task.execute("http://welshuganda.com/fypcg/retrieve_posts.php");




        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Muhwezi Denis").withEmail("mddiazize@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        //final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(R.drawable.ic_action_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(R.drawable.ic_action_list_2).withIdentifier(2),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withIcon(R.drawable.ic_action_search).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_section_header).withIcon(R.drawable.ic_action_calculator).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Help").withIcon(R.drawable.ic_action_help).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_advanced_drawer).withIcon(R.drawable.ic_action_info).withIdentifier(5)
          ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {


                            switch (position)
                            {
                                case 1:
                                    Intent home_intent = new Intent(Home.this, Home.class);
                                    startActivity(home_intent);
                                    finish();
                                    overridePendingTransition(0,0);

                                    break;

                                case 2:
                                    Intent university_courses_intent = new Intent(Home.this, Courses.class);
                                    startActivity(university_courses_intent);
                                    overridePendingTransition(0,0);

                                    break;


//                                case 3:
//                                    Intent findcourse_intent = new Intent(Home.this, Find_Courses.class);
//                                    startActivity(findcourse_intent);
//                                    overridePendingTransition(0,0);
//
//                                    break;

                                case 3:
                                    Intent find_intent = new Intent(Home.this, Cal_Weight.class);
                                    startActivity(find_intent);

                                    overridePendingTransition(0,0);
                                    break;

                                case 4:
                                    break;

                                case 5:
                                    Intent about_intent = new Intent(Home.this, About.class);
                                    startActivity(about_intent);
                                    overridePendingTransition(0,0);

                                    break;

                                default:
                              //      Toast.makeText(Demo_Activity.this, ((Nameable) drawerItem).getName().getText(Demo_Activity.this), Toast.LENGTH_SHORT).show();


                            }
                            Toast.makeText(Home.this, ((Nameable) drawerItem).getName().getText(Home.this), Toast.LENGTH_SHORT).show();
                        }
                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, R.color.trans));

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        view.setLayoutParams(params);
        view.setPadding(0,200,0,0);

        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });


        /**
         * NOTE THIS IS A HIGHLY CUSTOM ANIMATION. USE CAREFULLY.
         * this animate the height of the profile to the height of the AccountHeader and
         * animates the height of the drawerItems to the normal drawerItems so the difference between Mini and normal Drawer is eliminated
         **/
        /*
        final double headerHeight = DrawerUIUtils.getOptimalDrawerWidth(this) * 9d / 16d;
        final double originalProfileHeight = UIUtils.convertDpToPixel(72, this);
        final double headerDifference = headerHeight - originalProfileHeight;
        final double originalItemHeight = UIUtils.convertDpToPixel(64, this);
        final double normalItemHeight = UIUtils.convertDpToPixel(48, this);
        final double itemDifference = originalItemHeight - normalItemHeight;
        crossfadeDrawerLayout.withCrossfadeListener(new CrossfadeDrawerLayout.CrossfadeListener() {
            @Override
            public void onCrossfade(View containerView, float currentSlidePercentage, int slideOffset) {
                for (int i = 0; i < miniResult.getAdapter().getItemCount(); i++) {
                    IDrawerItem drawerItem = miniResult.getAdapter().getItem(i);
                    if (drawerItem instanceof MiniProfileDrawerItem) {
                        MiniProfileDrawerItem mpdi = (MiniProfileDrawerItem) drawerItem;
                        mpdi.withCustomHeightPx((int) (originalProfileHeight + (headerDifference * currentSlidePercentage / 100)));
                    } else if (drawerItem instanceof MiniDrawerItem) {
                        MiniDrawerItem mdi = (MiniDrawerItem) drawerItem;
                        mdi.withCustomHeightPx((int) (originalItemHeight - (itemDifference * currentSlidePercentage / 100)));
                    }
                }

                miniResult.getAdapter().notifyDataSetChanged();
            }
        });
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);

        if(mOrientation!=null)
            outState.putSerializable("EXTRA_ORIENTATION", mOrientation);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private LinearLayoutManager getLinearLayoutManager() {
        if (mOrientation == Orientation.HORIZONTAL) {
            return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("EXTRA_ORIENTATION")) {
                mOrientation = (Orientation) savedInstanceState.getSerializable("EXTRA_ORIENTATION");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
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


                String post_title = jsonObject.getString("post_title");
                //String post_date = jsonObject.getString("post_date");
                String post_content = jsonObject.getString("post_content");


                //ByteArrayInputStream imageStream = new ByteArrayInputStream(Base64.decode(post_image.getBytes(), Base64.DEFAULT));
                //Bitmap faculty_profile = BitmapFactory.decodeStream(imageStream);

                header_iamge.setImageDrawable(getResources().getDrawable(R.drawable.cit));

                //file_maps.put(post_title, faculty_profile);

                Log.d("now",post_title+post_content);

                mDataList.add(new TimeLineModel(post_title,post_content,"today", OrderStatus.ACTIVE));

                mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
                mRecyclerView.setAdapter(mTimeLineAdapter);


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}