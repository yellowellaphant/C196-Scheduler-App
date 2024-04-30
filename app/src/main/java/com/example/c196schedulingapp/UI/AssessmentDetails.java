package com.example.c196schedulingapp.UI;

import static com.example.c196schedulingapp.Helper.GenerateID.generateUniqueID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.c196schedulingapp.Adapters.AssessmentViewAdapter;
import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Helper.Receiver;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Helper.ParseDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetails extends AppCompatActivity {

    String assessmentName;
    String startDate;
    String endDate;
    int courseID;
    int assessmentID;

    EditText editName;
    EditText editStart;
    EditText editEDate;

    RadioButton radioButtonOA;
    RadioButton radioButtonPA;
    RadioGroup radioGroup;
    String radioIDSelection;

    public static int numAlert;
    AssessmentRepo assessmentRepo;
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d("onCreate", "setting fields");

        assessmentName = getIntent().getStringExtra("assessmentName");
        editName = findViewById(R.id.assessmentName);
        editName.setText(assessmentName);

        startDate = getIntent().getStringExtra("startDate");
        editStart = findViewById(R.id.assessmentStart);
        editStart.setText(startDate);

        endDate = getIntent().getStringExtra("endDate");
        editEDate = findViewById(R.id.assessmentEnd);
        editEDate.setText(endDate);

        assessmentID = getIntent().getIntExtra("assessmentID",-1);
        assessmentRepo = new AssessmentRepo(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);

        radioGroup = findViewById(R.id.radioGroup);
        radioIDSelection= getIntent().getStringExtra("assessmentType");
        radioButtonOA = findViewById(R.id.radioButton2);
        radioButtonPA = findViewById(R.id.radioButton1);

        if (radioIDSelection!=null) {
            if (radioIDSelection.equalsIgnoreCase("Performance Assessment")) {
                radioButtonPA.setChecked(true);
            } else if (radioIDSelection.equalsIgnoreCase("Objective Assessment")) {
                radioButtonOA.setChecked(true);
            } else
                radioButtonOA.setChecked(true);
        }


        if (courseID==-1) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) { courseID = extras.getInt("key2");
             }
        }

        date1 = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        date2 = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        };
        editStart.setOnClickListener(v -> new DatePickerDialog(AssessmentDetails.this, date1, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        editEDate.setOnClickListener(v -> new DatePickerDialog(AssessmentDetails.this, date2, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if (itemId == R.id.notifyStart) {
            String dateFromString = editStart.getText().toString();
            long trigger = ParseDate.dateParse(dateFromString).getTime();
            Intent intentAStart = new Intent(AssessmentDetails.this, Receiver.class);
            intentAStart.putExtra("key", "Alert! Assessment: " + assessmentName + " Starts: " + ParseDate.dateParse(editStart.getText().toString()));
            PendingIntent senderAStart = PendingIntent.getBroadcast(AssessmentDetails.this, ++numAlert, intentAStart, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderAStart);
            return true;
        } else if (itemId == R.id.notifyEnd) {
            String dateFromString2 = editEDate.getText().toString();
            long trigger2 = ParseDate.dateParse(dateFromString2).getTime();
            Intent intentAEnd = new Intent(AssessmentDetails.this, Receiver.class);
            intentAEnd.putExtra("key", "Alert! Assessment: " + assessmentName + " Ends: " + ParseDate.dateParse(editEDate.getText().toString()));
            PendingIntent senderAEnd = PendingIntent.getBroadcast(AssessmentDetails.this, ++numAlert, intentAEnd, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderAEnd);
            return true;
        } else if (itemId == R.id.delete) {
            for (Assessment assessment : assessmentRepo.getAllAssessments()) {
                if (assessment.getAssessmentID() == assessmentID) {
                    assessmentRepo.delete(assessment);
                    Toast.makeText(this, "Assessment Successfully Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(getApplicationContext(), TermList.class);
                    startActivity(intent3);
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_menu,menu);
        return true;
    }

    public void onCancel(View view) {
        this.finish();
    }

    public void saveAssessment(View view) {

       if (editEDate.getText().toString().trim().isEmpty() || editStart.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "All required fields must be completed";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
           Log.d("saveAssessment", "empty fields");
        }
       else {
           String screenName = editName.getText().toString();
           Date screenDate = ParseDate.dateParse(editStart.getText().toString());
           Date screenDate2 = ParseDate.dateParse(editEDate.getText().toString());

           Log.d("saveAssessment", "all fields completed");

           int newAssessmentID = generateUniqueAssessmentID();

           if (assessmentName == null) {
               assessmentID = assessmentRepo.getAllAssessments().size();
               /*Assessment newAssessment = new Assessment(++assessmentID, courseID,screenName,screenDate, screenDate2, radioIDSelection);
               assessmentRepo.insert(newAssessment);*/
               Assessment newAssessment = new Assessment(newAssessmentID, courseID,screenName,screenDate, screenDate2, radioIDSelection);
               assessmentRepo.insert(newAssessment);

               Log.d("saveAssessment", "save new assessment");

           } else {
               Assessment oldAssessment = new Assessment(getIntent().getIntExtra("assessmentID", -1),courseID, screenName,screenDate, screenDate2,radioIDSelection);
               assessmentRepo.update(oldAssessment);

               Log.d("saveAssessment", "modify assessment save");
           }
           RecyclerView recyclerView = findViewById(R.id.recyclerAssessmentView);
           List<Assessment> allAssessment = new ArrayList<>();
           for (Assessment assessment : assessmentRepo.getAllAssessments()) {
               if (assessment.getCourseID() == courseID)
                   allAssessment.add(assessment);
           }
           final AssessmentViewAdapter assessmentAdapter = new AssessmentViewAdapter(this);
           recyclerView.setAdapter(assessmentAdapter);
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           assessmentAdapter.setAssessments(allAssessment);
       }
        this.finish();
        Log.d("saveAssessment", "method finished");
    }

    private int generateUniqueAssessmentID() {

        return assessmentRepo.getAllAssessments().size() + 1;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStart.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void checkButton(View view) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton oncSelected = findViewById(radioID);
        radioIDSelection = "" + oncSelected.getText();
    }
}