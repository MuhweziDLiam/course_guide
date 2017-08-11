package com.guru.muhwezidenisliam.course_gudie.courses_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.guru.muhwezidenisliam.course_gudie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 11/7/16.
 */

public class ChildViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView course_certification_course;
    TextView course_duration_course;
    TextView course_tuition_course;
    TextView cut_off;


    public ChildViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.child);
        course_certification_course = (TextView) itemView.findViewById(R.id.course_certification_course);
        course_duration_course = (TextView)itemView.findViewById(R.id.course_duration_course);
        course_tuition_course = (TextView)itemView.findViewById(R.id.course_tuition_course);
        cut_off = (TextView)itemView.findViewById(R.id.cut_off);

    }
}
