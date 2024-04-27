package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196schedulingapp.Adapters.CourseViewAdapter;
import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.R;

import java.util.List;

public class CourseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        CourseRepo courseRepo = new CourseRepo(getApplication());

        List<Course> allCourses = courseRepo.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);

        final CourseViewAdapter courseAdapter = new CourseViewAdapter(this);

        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourse(allCourses);

    }

    public void courseAddButton(View view) {
        Intent intent = new Intent(CourseList.this, CourseDetails.class);
        startActivity(intent);
    }
}