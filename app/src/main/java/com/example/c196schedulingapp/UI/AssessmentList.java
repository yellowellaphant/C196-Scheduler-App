package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196schedulingapp.Adapters.AssessmentViewAdapter;
import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.R;

import java.util.List;

public class AssessmentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        AssessmentRepo assessmentRepo = new AssessmentRepo(getApplication());

        List<Assessment> allAssessments = assessmentRepo.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);

        final AssessmentViewAdapter assessmentAdapter = new AssessmentViewAdapter(this);

        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);

    }

    public void assessmentAddButton(View view) {
        Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
        startActivity(intent);
    }


}