package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196schedulingapp.Adapters.TermViewAdapter;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;

import java.util.List;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        TermRepo termRepo = new TermRepo(getApplication());

        List<Term> allTerms= termRepo.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        final TermViewAdapter termAdapter = new TermViewAdapter(this);

        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

    }

    public void floatingActionButton(View view) {
        Intent intent = new Intent(TermList.this, TermDetails.class);
        startActivity(intent);
    }

}