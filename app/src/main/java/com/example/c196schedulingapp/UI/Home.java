package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196schedulingapp.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClickTerms(View view) {
        Intent intent = new Intent(Home.this, TermList.class);
        startActivity(intent);
    }

    public void onClickCourses(View view) {
        Intent intent = new Intent(Home.this, CourseList.class);
        startActivity(intent);
    }

    public void onClickAssessments(View view) {
        Intent intent = new Intent(Home.this, AssessmentList.class);
        startActivity(intent);
    }


}