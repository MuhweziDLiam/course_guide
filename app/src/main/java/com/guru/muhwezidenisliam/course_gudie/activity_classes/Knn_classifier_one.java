package com.guru.muhwezidenisliam.course_gudie.activity_classes;

/**
 * Created by Muhwezi Denis Liam on 7/12/2017.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Muhwezi Denis Liam on 6/29/2017.
 */

public class Knn_classifier_one {

    private Double weight_count;

    private ArrayList<String> super_grades;

    private ArrayList<String> under_grades;

    public Knn_classifier_one(ArrayList<String> super_grades, ArrayList<String> under_grades, Double weight)
    {
        this.weight_count = weight;
        this.super_grades = super_grades;
        this.under_grades = under_grades;
    }

    private String[] course_name = {"CIV","ELE","CSC","BSW","BIT","STE","MEA"};
    private String[] course_name_one = {"CSC","BSW","BIT"};
    private String[] course_name_two = {"CIV","ELE","TEL","MEA"};
    private String[] course_name_bc = {"MAM","PHA","BEH","BBT","FST","EDS","BFS"};
    private String[] course_name_bc_one = {"BBT"};
    private String[] course_name_bc_two = {"BEH"};
    private String[] course_name_math = {"BBS"};
    private String[] course_name_econ = {"COE","ADM"};
    private String[] course_name_me = {"BQE","COE"};
    private String[] course_name_all = {"BJC","SOC","ASS","BCO","BAC"};




    private Double [][] classifier_maths_physics =
            {
                    { 49.4, 48.7, 48.4, 47.7, 47.7, 47.7, 47.6, 47.4, 47.4, 47.2, 47.2, 47.1, 46.7, 46.6, 46.6, 46.6, 46.5, 46.5, 46.4, 46.4, 46.4, 46.3, 46.3, 46.3, 46.3, 46.2, 46.2, 46.2, 46.2, 46.2, 46.1, 46.0 ,46.0, 45.8, 45.8, 45.8, 45.8, 45.8, 45.8, 45.7 },
                    { 50.7, 48.4, 47.6, 47.3, 46.7, 46.2, 46.1, 46.1, 46.0, 46.0, 45.9, 45.8, 45.6, 45.6, 45.6, 45.6, 45.5, 45.5, 45.5, 45.4, 45.4, 45.4, 45.4, 45.4, 45.3, 45.3, 45.2, 45.2, 45.1, 45.0, 45.0, 45.0, 44.9, 44.8, 44.8, 44.7, 44.7, 44.7, 44.7, 44.7 },
                    { 45.7, 43.3, 43.2, 42.9, 42.8, 42.6, 42.5, 42.3, 41.8, 41.5, 41.4, 41.3, 41.2, 40.9, 40.8, 40.7, 40.7, 40.5, 40.3, 40.0, 28.0, 28.0, 27.6, 27.3, 27.2, 27.0, 27.0, 26.9, 26.8, 26.4, 26.2, 26.1, 25.7, 25.7, 25.5, 25.3, 25.3, 25.2, 25.2, 24.7 },
                    { 44.2, 43.2, 43.0, 42.7, 42.4, 42.1, 42.1, 42.0, 42.0, 41.8, 41.5, 41.3, 41.2, 40.4, 40.4, 40.3, 40.2, 40.2, 40.2, 40.1, 35.6, 35.6, 35.6, 35.6, 35.5, 35.4, 35.4, 35.3, 35.3, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.1, 35.1, 35.0, 35.0 },
                    { 35.6, 35.6, 35.6, 35.6, 35.5, 35.4, 35.4, 35.3, 35.3, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.1, 35.1, 35.0, 35.0, 26.9, 26.7, 26.7, 26.0, 25.9, 25.7, 25.6, 25.3, 24.3, 24.2, 24.1, 23.9, 23.9, 22.9, 22.7, 22.6, 22.3, 22.3, 22.3, 22.2  },
                    { 45.6, 41.1, 40.4, 40.0, 39.2, 38.6, 37.5, 37.3, 37.2, 36.3, 35.7, 35.6, 35.5, 34.9, 34.7, 34.6, 34.5, 34.5, 34.5, 34.4, 28.8, 28.8, 28.7, 28.7, 28.6, 28.4, 28.3, 27.8, 27.8, 27.6, 27.6, 27.4, 27.3, 27.3, 27.0, 27.0, 26.9, 26.8, 26.3, 25.7 },
                    { 45.3, 44.7, 44.2, 44.1, 43.4, 43.2, 43.2, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.5, 42.4, 42.4, 42.2, 42.0, 41.8, 41.7, 41.6, 41.6, 41.6, 41.6, 41.5, 41.4, 41.4, 41.2, 41.1, 41.0, 41.0, 41.0, 41.0, 41.0, 41.0, 41.0 }
            };

    private Double [][] classifier_maths_physics_one =
            {
                    { 45.7, 43.3, 43.2, 42.9, 42.8, 42.6, 42.5, 42.3, 41.8, 41.5, 41.4, 41.3, 41.2, 40.9, 40.8, 40.7, 40.7, 40.5, 40.3, 40.0, 28.0, 28.0, 27.6, 27.3, 27.2, 27.0, 27.0, 26.9, 26.8, 26.4, 26.2, 26.1, 25.7, 25.7, 25.5, 25.3, 25.3, 25.2, 25.2, 24.7 },
                    { 44.2, 43.2, 43.0, 42.7, 42.4, 42.1, 42.1, 42.0, 42.0, 41.8, 41.5, 41.3, 41.2, 40.4, 40.4, 40.3, 40.2, 40.2, 40.2, 40.1, 35.6, 35.6, 35.6, 35.6, 35.5, 35.4, 35.4, 35.3, 35.3, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.1, 35.1, 35.0, 35.0 },
                    { 35.6, 35.6, 35.6, 35.6, 35.5, 35.4, 35.4, 35.3, 35.3, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.2, 35.1, 35.1, 35.0, 35.0, 26.9, 26.7, 26.7, 26.0, 25.9, 25.7, 25.6, 25.3, 24.3, 24.2, 24.1, 23.9, 23.9, 22.9, 22.7, 22.6, 22.3, 22.3, 22.3, 22.2  }

            };

    private Double [][] classifier_maths_physics_two =
            {
                    { 49.4, 48.7, 48.4, 47.7, 47.7, 47.7, 47.6, 47.4, 47.4, 47.2, 47.2, 47.1, 46.7, 46.6, 46.6, 46.6, 46.5, 46.5, 46.4, 46.4, 46.4, 46.3, 46.3, 46.3, 46.3, 46.2, 46.2, 46.2, 46.2, 46.2, 46.1, 46.0 ,46.0, 45.8, 45.8, 45.8, 45.8, 45.8, 45.8, 45.7 },
                    { 50.7, 48.4, 47.6, 47.3, 46.7, 46.2, 46.1, 46.1, 46.0, 46.0, 45.9, 45.8, 45.6, 45.6, 45.6, 45.6, 45.5, 45.5, 45.5, 45.4, 45.4, 45.4, 45.4, 45.4, 45.3, 45.3, 45.2, 45.2, 45.1, 45.0, 45.0, 45.0, 44.9, 44.8, 44.8, 44.7, 44.7, 44.7, 44.7, 44.7 },
                    { 45.6, 41.1, 40.4, 40.0, 39.2, 38.6, 37.5, 37.3, 37.2, 36.3, 35.7, 35.6, 35.5, 34.9, 34.7, 34.6, 34.5, 34.5, 34.5, 34.4, 28.8, 28.8, 28.7, 28.7, 28.6, 28.4, 28.3, 27.8, 27.8, 27.6, 27.6, 27.4, 27.3, 27.3, 27.0, 27.0, 26.9, 26.8, 26.3, 25.7 },
                    { 45.3, 44.7, 44.2, 44.1, 43.4, 43.2, 43.2, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.8, 42.5, 42.4, 42.4, 42.2, 42.0, 41.8, 41.7, 41.6, 41.6, 41.6, 41.6, 41.5, 41.4, 41.4, 41.2, 41.1, 41.0, 41.0, 41.0, 41.0, 41.0, 41.0, 41.0 }

            };


    private Double [][] classifier_bio_chem =
            {
                    { 49.2, 48.2, 47.7, 47.6, 47.5, 47.4, 47.4, 47.3, 47.3, 47.3, 47.2, 47.0, 47.0, 46.9, 46.8, 46.8, 46.7, 46.5, 46.5, 46.5, 46.4, 46.4, 46.3, 46.3, 46.3, 46.1, 45.8, 45.8, 45.7, 45.7, 45.6, 45.5, 45.5, 45.5, 45.3, 45.3, 45.3, 45.3, 45.3, 45.3  },
                    { 52.0, 52.0, 52.0, 52.0, 52.0, 52.0, 49.7, 49.2, 49.2, 49.2, 49.2, 49.2, 49.2, 46.9, 46.3, 46.3, 46.3, 46.3, 46.3, 46.3, 45.2, 45.2, 45.2, 45.2, 45.2, 45.0, 45.0, 45.0, 45.0, 44.8, 44.8, 44.8, 44.8, 44.8, 44.8, 44.8, 44.7, 44.7, 44.7, 44.7, },
                    { 43.4, 42.8, 42.4, 41.7, 41.6, 41.4, 41.2, 40.9, 40.6, 40.2, 40.2, 39.7, 39.6, 39.5, 39.4, 39.4, 39.0, 38.7, 38.6, 38.5, 38.5, 38.5, 38.0, 37.7, 37.4, 37.3, 37.3, 37.2, 37.2, 37.2, 37.2, 37.2, 37.0, 36.9, 36.9, 36.5, 36.4, 36.4, 36.3, 36.3  },
                    { 42.9, 42.0, 40.2, 40.0, 39.8, 39.6, 39.3, 39.2, 39.1, 38.7, 38.6, 38.6, 38.5, 38.5, 38.1, 37.7, 37.6, 37.3, 37.3, 37.2, 36.6, 36.6, 36.5, 36.4, 36.4, 36.2, 36.1, 36.1, 36.0, 36.0, 36.0, 36.0, 36.0, 35.9, 35.8, 35.7, 35.6, 35.4, 35.3, 35.3  },
                    { 43.9, 43.9, 43.8, 43.8, 43.7, 43.7, 43.6, 43.6, 43.6, 43.6, 43.6, 43.5, 43.4, 43.3, 43.3, 43.3, 43.2, 43.2, 43.2, 43.2, 43.2, 43.1, 43.1, 43.0, 43.0, 43.0, 43.0, 42.9, 42.8, 42.8, 42.5, 41.9, 41.8, 41.5, 41.5, 41.5, 41.5, 41.5, 41.5, 41.5  },
                    { 39.6, 38.7, 38.3, 37.7, 36.4, 36.3, 35.8, 35.7, 35.3, 34.9, 34.7, 33.7, 33.6, 33.5, 33.4, 33.2, 33.0, 32.9, 32.6, 32.1, 24.9, 24.8, 24.7, 24.2, 24.1, 23.7, 23.7, 23.4, 22.4, 22.1, 22.0, 21.2, 20.8, 20.8, 20.4, 19.4, 19.0, 18.2, 18.1, 17.9 },
                    { 38.2, 36.8, 36.0, 33.0, 32.1, 31.5, 30.9, 30.9, 29.2, 28.2, 28.0, 28.0, 27.5, 27.3, 27.1, 27.0, 26.8, 26.4, 26.2, 26.1, 26.2, 26.1, 25.0, 24.2, 24.0, 23.7, 23.7, 23.0, 22.9, 22.1, 21.4, 21.3, 21.2, 21.0, 20.7, 20.0, 20.0, 19.6, 19.5, 19.0  }    };

    private Double [][] classifier_bio_chem_one =
            {
                    { 42.9, 42.0, 40.2, 40.0, 39.8, 39.6, 39.3, 39.2, 39.1, 38.7, 38.6, 38.6, 38.5, 38.5, 38.1, 37.7, 37.6, 37.3, 37.3, 37.2, 36.6, 36.6, 36.5, 36.4, 36.4, 36.2, 36.1, 36.1, 36.0, 36.0, 36.0, 36.0, 36.0, 35.9, 35.8, 35.7, 35.6, 35.4, 35.3, 35.3 },
            };

    private Double [][] classifier_bio_chem_two =
            {
                    { 43.4, 42.8, 42.4, 41.7, 41.6, 41.4, 41.2, 40.9, 40.6, 40.2, 40.2, 39.7, 39.6, 39.5, 39.4, 39.4, 39.0, 38.7, 38.6, 38.5, 38.5, 38.5, 38.0, 37.7, 37.4, 37.3, 37.3, 37.2, 37.2, 37.2, 37.2, 37.2, 37.0, 36.9, 36.9, 36.5, 36.4, 36.4, 36.3, 36.3  }
            };

    private Double[][] classifier_maths =
            {
                    { 29.0, 28.8, 28.5, 27.8, 27.4, 26.8, 26.7, 26.5, 25.8, 25.7, 25.7, 25.7, 25.7, 25.6, 25.6, 25.6, 25.6, 25.6, 25.4, 25.4, 19.2, 19.2, 19.2, 19.1, 19.1, 19.1, 19.1, 19.1, 19.1, 19.1, 19.1, 19.1, 19.1, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0 }            };

    private Double [][] classifier_econ =
            {
                    { 48.5, 48.3, 48.0, 46.3, 46.2, 45.9, 45.8, 45.2, 44.5, 44.4, 44.4, 44.3, 44.1, 44.0, 43.9, 43.7, 43.7, 43.7, 43.7, 43.7, 39.5, 39.5, 39.5, 39.5, 39.4, 39.4, 39.3, 39.3, 39.3, 39.2, 39.2, 39.1, 39.1, 39.1, 39.1, 39.0, 38.9, 38.9, 38.9, 38.9 }            };


    private Double [][] classifier_math_econ =
            {
                    { 45.8, 45.4, 45.4, 45.1, 44.6, 44.6, 44.4, 44.4, 44.1, 44.0, 43.8, 43.7, 43.6, 43.5, 43.5, 43.4, 43.1, 42.8, 42.7, 42.7, 39.4, 39.3, 39.2, 39.2, 39.2, 39.2, 39.1, 39.0, 39.0, 38.9, 38.9, 38.8, 38.7, 38.7, 38.6, 38.4, 38.3, 38.3, 38.2, 38.2 },
                    { 45.3, 43.6, 42.9, 42.0, 41.7, 41.5, 41.4, 40.9, 40.9, 40.7, 40.4, 40.1, 39.9, 39.8, 39.6, 39.4, 39.4, 39.2, 39.0, 39.0, 29.9, 29.9, 29.8, 29.7, 29.6, 29.4, 29.4, 29.4, 29.4, 29.3, 29.2, 29.2, 29.2, 29.2, 29.2, 29.1, 29.1, 29.0, 29.0, 29.0 }            };

    private Double[][] classifier_all =
            {
                    { 50.4, 50.0, 49.4, 49.1, 49.1, 48.8, 48.8, 48.8, 48.7, 48.7, 48.6, 48.5, 48.5, 48.4, 48.4, 47.3, 47.3, 47.0, 46.9, 46.7, 45.2, 45.1, 45.1, 45.0, 45.0, 45.0, 45.0, 44.9, 44.8, 44.7, 44.7, 44.6, 44.1, 44.0, 44.0, 44.0, 43.9, 43.9, 43.9, 43.9 },
                    { 51.8, 49.8, 49.7, 49.6, 49.1, 49.1, 48.7, 48.7, 48.6, 48.6, 48.5, 48.5, 48.3, 48.1, 48.1, 48.0, 47.8, 47.7, 47.7, 47.3, 42.1, 42.1, 42.0, 42.0, 42.0, 41.9, 41.9, 41.8, 41.5, 41.4, 41.4, 41.3, 41.2, 41.1, 41.1, 41.1, 41.0, 41.0, 40.9, 40.8  },
                    { 43.8, 41.4, 41.1, 41.0, 34.5, 34.4, 34.2, 34.1, 33.8, 32.9, 32.9, 32.6, 32.5, 32.3, 31.9, 31.7, 31.5, 31.4, 31.3, 31.3, 18.5, 18.3, 18.3, 18.1, 18.0, 18.0, 17.9, 17.9, 17.8, 17.5, 17.5, 16.6, 16.4, 16.4, 16.4, 16.3, 16.3, 16.1, 15.6, 14.0  },
                    { 51.6, 51.4, 49.0, 48.7, 48.7, 48.6, 48.3, 48.2, 48.0, 48.0, 47.8, 47.5, 47.4, 47.4, 47.2, 47.2, 47.1, 47.0, 47.0, 46.9, 38.8, 38.7, 38.7, 38.7, 38.6, 38.6, 38.6, 38.6, 38.6, 38.6, 38.6, 38.6, 38.5, 38.5, 38.5, 38.5, 38.3, 38.2, 38.2, 38.2 }            };




    public String[] classify()
    {

        String [] classified = {};
        if(super_grades.size()==2)
        {
            if(super_grades.contains("Physics") && super_grades.contains("Maths")
                    && (under_grades.contains("Chemistry") || under_grades.contains("Economics") || under_grades.contains("Geography")))
            {

                classified = list_courses(classifier_maths_physics,weight_count,course_name,40,7);

            }

            else if(super_grades.contains("Physics") && super_grades.contains("Maths")
                    && under_grades.contains("Biology"))
            {

                classified = list_courses(classifier_maths_physics_one,weight_count,course_name_one,40,3);

            }
            else if(super_grades.contains("Physics") && super_grades.contains("Maths")
                    && under_grades.contains("Entrepreneurship"))
            {

                classified = list_courses(classifier_maths_physics_two,weight_count,course_name_two,40,4);

            }
            else if (super_grades.contains("Biology")&& super_grades.contains("Chemistry")
                    && (under_grades.contains("Maths") || under_grades.contains("Physics")))
            {
                classified = list_courses(classifier_bio_chem,weight_count,course_name_bc,40,7);

            }
            else if (super_grades.contains("Biology")&& (super_grades.contains("Chemistry") || super_grades.contains("Agriculture") )
                    && (under_grades.contains("Agriculture")))
            {
                classified = list_courses(classifier_bio_chem_one,weight_count,course_name_bc_one,40,1);

            }
            else if (super_grades.contains("Biology")&& super_grades.contains("Chemistry")
                    && (under_grades.contains("Geography")))
            {
                classified = list_courses(classifier_bio_chem_two,weight_count,course_name_bc_two,40,1);

            }
            else if (super_grades.contains("Economics") && super_grades.contains("Maths")
                    && (under_grades.contains("Geography") || under_grades.contains("Maths") || under_grades.contains("Physics")))
            {
                classified = list_courses(classifier_math_econ,weight_count,course_name_me,40,2);
            }
            else if (super_grades.contains("Math") && (super_grades.contains("Economics")
                    || super_grades.contains("Geography") || super_grades.contains("Chemistry")
                    || super_grades.contains("Physics") || super_grades.contains("Entrepreneurship")) &&
                    (under_grades.contains("Geography") || under_grades.contains("Maths")
                            || under_grades.contains("Physics") ||
                            under_grades.contains("Entrepreneurship") || under_grades.contains("Economics")))
            {
                classified = list_courses(classifier_maths,weight_count,course_name_math,40,1);
            }

            else {
                classified = list_courses(classifier_all,weight_count,course_name_all,40,5);
            }


        }else if(super_grades.size()==1)
        {
            if( super_grades.contains("Economics") &&
                    (( under_grades.contains("Maths") || under_grades.contains("Geography")
                            || under_grades.contains("Entrepreneurship")) && ( under_grades.contains("Maths") || under_grades.contains("Geography")
                            || under_grades.contains("Entrepreneurship")) )) {

                classified = list_courses(classifier_econ,weight_count,course_name_econ,40,2);
            }
            else {
                classified = list_courses(classifier_all,weight_count,course_name_all,40,5);
            }

/*                  else if ( super_grades.contains("Maths") )
                  {

                      classified = list_courses(classifier_econ,weight_count,course_name_econ,40,2);
                  }*/


        }

        return classified;
    }



    public String[] list_courses(Double[][] sample_classifier, Double weight_toUse, String[] course_array,int row, int column)
    {

        int position =0;

        HashMap<String,Integer> knn_sorter = new HashMap<>();

        HashMap<String,Double> lists = new HashMap<>();

        HashMap<String,Double> distance = new HashMap<>();

        for(int x=0;x<column;x++)
        {
            for(int y=0;y<row;y++)
            {
                distance.put(String.valueOf(((x*row)+y)),((sample_classifier[x][y]) - (weight_toUse)));

            }

        }


        Map<String,Double> sortedMap = sortByValue(distance);

        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            if(value<2)
            {
                lists.put(key,value);
            }
        }


        ArrayList<String> real_duplicates = classify_name(lists,course_array);


        for(int count= 0; count<course_array.length;count++)
        {
            int count_value = 0;

            for(String course :real_duplicates)
            {
                if(course_array[count].equals(course))
                {
                    count_value++;
                }
            }

            Log.d("value_two",course_array[count]+"==>== "+count_value );

            if(count_value!=0) {
                knn_sorter.put(course_array[count], count_value);
            }
        }


        Map<String,Integer> sortedMapInt = sortByValueInteger(knn_sorter);

        String[] next_arch = new String[sortedMapInt.size()];

        for (Map.Entry<String, Integer> entry : sortedMapInt.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            if(value!=0)
            {
                next_arch[position] = key;

                position++;
            }

        }

        return next_arch;
    }

    private ArrayList<String> classify_name (HashMap<String,Double> model,String[] name)

    {
        ArrayList<String> duplicates = new ArrayList<>();

        for (Map.Entry<String, Double> entry : model.entrySet()) {
            String key = entry.getKey();

            if (Double.parseDouble(key) >= 0 && Double.parseDouble(key) <= 40) {
                duplicates.add(name[0]);
            } else if (Double.parseDouble(key) >= 41 && Double.parseDouble(key) <= 80) {
                duplicates.add(name[1]);
            } else if (Double.parseDouble(key) >= 81 && Double.parseDouble(key) <= 120) {
                duplicates.add(name[2]);
            } else if (Double.parseDouble(key) >= 121 && Double.parseDouble(key) <= 160) {
                duplicates.add(name[3]);
            } else if (Double.parseDouble(key) >= 161 && Double.parseDouble(key) <= 200) {
                duplicates.add(name[4]);
            } else if (Double.parseDouble(key) >= 201 && Double.parseDouble(key) <= 240){
                duplicates.add(name[5]);
            } else{
                duplicates.add(name[6]);
            }


        }

        return  duplicates;
    }

    private static Map<String,Integer> sortByValueInteger (Map<String,Integer> unsorted)
    {
        List<Map.Entry<String,Integer>> new_list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(new_list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });

        Map<String,Integer> sorted = new LinkedHashMap<>();
        for (Map.Entry<String,Integer> entry: new_list)
        {
            sorted.put(entry.getKey(),entry.getValue());
        }

        return sorted;
    }


    private static Map<String,Double> sortByValue (Map<String,Double> unsorted)
    {
        List<Map.Entry<String,Double>> new_list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(new_list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        Map<String,Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String,Double> entry: new_list)
        {
            sorted.put(entry.getKey(),entry.getValue());
        }

        return sorted;
    }


}
