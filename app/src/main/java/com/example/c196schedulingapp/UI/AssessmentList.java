package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.c196schedulingapp.Adapters.AssessmentViewAdapter;
import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class AssessmentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AssessmentRepo assessmentRepo = new AssessmentRepo(getApplication());

        List<Assessment> allAssessments = assessmentRepo.getAllAssessments();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);

        FloatingActionButton addButton = findViewById(R.id.assessmentAddButton);
        addButton.setVisibility(View.GONE);

        final AssessmentViewAdapter assessmentAdapter = new AssessmentViewAdapter(this);

        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void assessmentAddButton(View view) {
        Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
        startActivity(intent);
    }


}