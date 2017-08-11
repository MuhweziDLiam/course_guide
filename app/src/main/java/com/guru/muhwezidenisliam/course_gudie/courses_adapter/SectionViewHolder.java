package com.guru.muhwezidenisliam.course_gudie.courses_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.guru.muhwezidenisliam.course_gudie.R;


/**
 * Created by shanky on 11/12/2016.
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    public SectionViewHolder(View itemView) {
        super(itemView);
         name = (TextView) itemView.findViewById(R.id.section_header);
    }
}
