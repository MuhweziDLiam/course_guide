package com.guru.muhwezidenisliam.course_gudie.foldingcell;

import android.bluetooth.le.ScanRecord;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;


/**
 * Simple POJO model for example
 */
public class Item {

    String faculty_image, faculty_image_free;
    String faculty_name,faculty_name_free;
    String course_duration,course_duration_free;
    String course_tuition,course_tuition_free;
    String course_name,course_name_free;
    String course_description,course_certification;
    String nearest_hostel,latitude,longitude;


    public Item() {
    }

    public Item(String faculty_image, String faculty_image_free, String faculty_name, String faculty_name_free, String course_duration, String course_duration_free, String course_tuition,String course_tuition_free, String course_name,
                String course_name_free, String course_description, String course_certification, String nearest_hostel,String latitude,String longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.faculty_image = faculty_image;
        this.faculty_name = faculty_name;
        this.faculty_name_free = faculty_name_free;
        this.faculty_image_free = faculty_image_free;
        this.course_duration = course_duration;
        this.course_duration_free = course_duration_free;
        this.course_tuition = course_tuition;
        this.course_tuition_free = course_tuition_free;
        this.course_description= course_description;
        this.course_name = course_name;
        this.course_name_free = course_name_free;
        this.course_certification = course_certification;
        this.nearest_hostel = nearest_hostel;
    }

    //getters
    public String getLatitude() {return  latitude ;}

    public  String getLongitude() {return  longitude;}

    public String getFaculty_name()
    {
        return  faculty_name;
    }

    public String getCourse_duration()
    {
        return course_duration;
    }

    public String getCourse_tuition()
    {
        return course_tuition;
    }

    public String getCourse_name()
    {
        return  course_name;
    }

    public String getFaculty_name_free()
    {
        return  faculty_name_free;
    }

    public String getCourse_duration_free()
    {
        return course_duration_free;
    }

    public String getCourse_tuition_free()
    {
        return course_tuition_free;
    }

    public String getCourse_name_free()
    {
        return  course_name_free;
    }

    public String getCourse_description()
    {
        return course_description;
    }

    public String getCourse_certification()
    {
        return  course_certification;
    }

    public String getNearest_hostel()
    {
        return nearest_hostel;
    }

    public String getFaculty_image() { return faculty_image; }

    public String getFaculty_image_free() { return faculty_image_free; }

    //setters
//    public void setFaculty_name(String faculty_name)
//    {
//        this.faculty_name = faculty_name;
//    }
//
//    public void setFaculty_image (Uri faculty_image) { this.faculty_image = faculty_image ;}
//
//    public void setCourse_duration (String course_duration){ this.course_duration= course_duration; }
//
//    public void setCourse_tuition (String course_tuition) {this.course_tuition = course_tuition;}
//
//    public void setCourse_name (String course_name) {this.course_name = course_name;}
//
//    public void setNearest_hostel (String nearest_hostel){this.nearest_hostel = nearest_hostel;}
//
//    public void setCourse_description (String course_description){this.course_description = course_description ;}
//
//    public void setCourse_certification (String course_certification){this.course_certification =course_certification;}
//
//    public void setFaculty_name_free(String faculty_name_free) {this.faculty_name_free = faculty_name_free;}
//
//    public void setFaculty_image_free(Uri faculty_image_free) { this.faculty_image_free = faculty_image_free ;}
//
//    public void setCourse_duration_free(String course_duration_free){ this.course_duration_free= course_duration_free; }
//
//    public void setCourse_tuition_free(String course_tuition_free) {this.course_tuition_free = course_tuition_free;}
//
//    public void setCourse_name_free(String course_name_free) {this.course_name_free = course_name_free;}


}
