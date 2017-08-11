package com.guru.muhwezidenisliam.course_gudie.courses_adapter;

/**
 * Created by apple on 11/7/16.
 */
public class Child {

    String name,course_duration_course
            ,course_tuition_course,course_certification_course,cut_off;

    public Child(String name,String course_certification_course,
                 String cut_off,String course_duration_course, String course_tuition_course) {
        this.name = name;
        this.course_certification_course = course_certification_course;
        this.course_duration_course = course_duration_course;
        this.course_tuition_course = course_tuition_course;
        this.cut_off = cut_off;
    }

    public String getName() {
        return name;
    }

    public String getCourse_duration_course() {
        return  course_duration_course;
    }

    public  String getCourse_certification_course()
    {
        return course_certification_course;
    }

    public  String getCourse_tuition_course()
    {
        return course_tuition_course;
    }
    public String getCut_off()
    {
        return  cut_off;
    }


}