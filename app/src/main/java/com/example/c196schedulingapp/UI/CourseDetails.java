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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196schedulingapp.Adapters.AssessmentViewAdapter;
import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Helper.Receiver;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Helper.ParseDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String name;
    String startDate;
    String endDate;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String status;
    String optionalNotes;

    EditText editName;
    EditText editSDate;
    EditText editEDate;
    EditText editInstructor;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    EditText editOptionalText;

    int courseID;
    int termID;
    int numAlert;
    CourseRepo courseRepo;
    AssessmentRepo assessmentRepo;

    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        status = getIntent().getStringExtra("status");
        spinner.setSelection(arrayAdapter.getPosition(status));

        name = getIntent().getStringExtra("courseTitle");
        editName = findViewById(R.id.courseName);
        editName.setText(name);

        startDate = getIntent().getStringExtra("courseStart");
        editSDate = findViewById(R.id.courseStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("courseEnd");
        editEDate = findViewById(R.id.courseEnd);
        editEDate.setText(endDate);

        instructorName = getIntent().getStringExtra("instructorName");
        editInstructor = findViewById(R.id.instructorName);
        editInstructor.setText(instructorName);

        instructorEmail = getIntent().getStringExtra("instructorEmail");
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorEmail.setText(instructorEmail);

        instructorPhone = getIntent().getStringExtra("instructorPhone");
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editInstructorPhone.setText(instructorPhone);

        optionalNotes = getIntent().getStringExtra("optionalNotes");
        editOptionalText =findViewById(R.id.optionalNotes);
        editOptionalText.setText(optionalNotes);

        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        if (termID==-1) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                termID = extras.getInt("key");
            }
        }

        if (name==null){
            FloatingActionButton button = findViewById(R.id.floatingActionButton);
            button.hide();
        }
        courseRepo = new CourseRepo(getApplication());
        assessmentRepo= new AssessmentRepo(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recyclerAssessmentView);
        List<Assessment> allAssessment = new ArrayList<>();
        for (Assessment assessment : assessmentRepo.getAllAssessments()) {
            if (assessment.getCourseID() == courseID)
                allAssessment.add(assessment);
        }

        //editOptionalText.setVisibility(View.GONE);

        final AssessmentViewAdapter assessmentAdapter = new AssessmentViewAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessment);

        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
        editSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseDetails.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CourseDetails.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.courses_menu,menu);
        return true;
    }

    /*public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.delete:
                for (Course course : courseRepo.getAllCourses()){
                    if (course.getCourseID()==courseID){
                        courseRepo.delete(course);
                        Toast.makeText(this,"Course Deleted",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TermList.class);
                        startActivity(intent);
                    }
                }
                return true;
            case R.id.share:
                // TODO fix to send correct data
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Course Name: "+ name+"\n"+ "Start Date: "+ startDate +"\n"+"End Date: "+ endDate+ "\n"+ "Status: "+status +"\n"
                        + "\n"+"Course Instructor: "+ instructorName+ "\n"+"Email: "+ instructorEmail+ "\n"+"Phone: "+ instructorPhone+ "\n"+ " Courses Notes: "+optionalNotes);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Share "+name +" Course Info");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStart:
                String dateFromString = editSDate.getText().toString();
                long trigger = ParseDate.dateParse(dateFromString).getTime();
                Intent intentCStart  = new Intent(CourseDetails.this,Receiver.class);
                intentCStart.putExtra("key","Alert! Course: "+ name+ " starts: " + ParseDate.dateParse(editSDate.getText().toString()));
                PendingIntent sender= PendingIntent.getBroadcast(CourseDetails.this, ++numAlert, intentCStart, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyEnd:
                String dateFromString2 = editEDate.getText().toString();
                long trigger2 = ParseDate.dateParse(dateFromString2).getTime();
                Intent intentCEnd  = new Intent(CourseDetails.this,Receiver.class);
                intentCEnd.putExtra("key","Alert! Course: "+ name+ " Ends: " + ParseDate.dateParse(editEDate.getText().toString()));
                PendingIntent senderCEndDate= PendingIntent.getBroadcast(CourseDetails.this, ++numAlert, intentCEnd, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager2=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderCEndDate);
                return true;
            case R.id.showNotes:
                editOptionalText.setText(optionalNotes);
                editOptionalText.setVisibility(View.VISIBLE);
                return true;
            case R.id.refresh:
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
        return super.onOptionsItemSelected(item);
    }*/

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        }
        else if (itemId == R.id.delete) {
            for (Course course : courseRepo.getAllCourses()) {
                if (course.getCourseID() == courseID) {
                    courseRepo.delete(course);
                    Toast.makeText(this, "Course Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TermList.class);
                    startActivity(intent);
                }
            }
            return true;
        }
        else if (itemId == R.id.share) {
            // TODO fix to send correct data
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            sendIntent.putExtra(Intent.EXTRA_TEXT, "Course: " + name + "\n" +
                    "Instructor: " + instructorName + "\n" + " Notes: " + optionalNotes);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Share " + name + " Course Notes");

            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        else if (itemId == R.id.notifyStart) {
            String dateFromString = editSDate.getText().toString();
            long trigger = ParseDate.dateParse(dateFromString).getTime();
            Intent intentCStart = new Intent(CourseDetails.this, Receiver.class);

            intentCStart.putExtra("key", "ALERT Course: " + name + " starts: " + ParseDate.dateParse(editSDate.getText().toString()));
            PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++numAlert, intentCStart, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        else if (itemId == R.id.notifyEnd) {
            String dateFromString2 = editEDate.getText().toString();
            long trigger2 = ParseDate.dateParse(dateFromString2).getTime();
            Intent intentCEnd = new Intent(CourseDetails.this, Receiver.class);

            intentCEnd.putExtra("key", "ALERT Course: " + name + " Ends: " + ParseDate.dateParse(editEDate.getText().toString()));
            PendingIntent senderCEndDate = PendingIntent.getBroadcast(CourseDetails.this, ++numAlert, intentCEnd, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderCEndDate);
            return true;
        }
        /*else if (itemId == R.id.showNotes) {
            editOptionalText.setText(optionalNotes);
            editOptionalText.setVisibility(View.VISIBLE);
            return true;
        }*/
        else if (itemId == R.id.refresh) {
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
        return super.onOptionsItemSelected(item);
    }



            public void addCAssessment(View view) {
        Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
        courseID= getIntent().getIntExtra("courseID", -1);
        intent.putExtra("key2", courseID);
        startActivity(intent);

    }

    public void saveCourse(View view) {
        // Check if any required fields are empty
        if (editEDate.getText().toString().trim().isEmpty() ||
                editSDate.getText().toString().trim().isEmpty() ||
                editName.getText().toString().trim().isEmpty() ||
                editInstructor.getText().toString().trim().isEmpty() ||
                editInstructorEmail.getText().toString().trim().isEmpty() ||
                editInstructorPhone.getText().toString().trim().isEmpty()) {

            // Show a toast message indicating that all required fields must be completed
            Toast.makeText(this, "All required fields must be completed", Toast.LENGTH_SHORT).show();
        } else {
            // Retrieve data from EditText fields
            String screenName = editName.getText().toString();
            Date screenDate = ParseDate.dateParse(editSDate.getText().toString());
            Date screenDate2 = ParseDate.dateParse(editEDate.getText().toString());
            String screenInstructor = editInstructor.getText().toString();
            String screenInstructorEmail = editInstructorEmail.getText().toString();
            String screenInstructorPhone = editInstructorPhone.getText().toString();
            String optionalNotes = editOptionalText.getText().toString();

            // Check if the courseID is -1 (indicating a new course)
            if (courseID == -1) {
                // Generate a new courseID
                // Note: Ensure that you properly handle the generation of unique IDs
                // Currently, you're using (assessmentRepo.getAllAssessments().size() - 1) which might not be correct
                // Instead, you can use a different method to generate unique IDs, such as using a counter or UUID
                // For simplicity, I'll use 0 as the courseID here, assuming you have a proper method to generate IDs
                courseID = 0;
                int uniqueID = generateUniqueID();
                // Create a new Course object with the provided data
                Course newCourse = new Course(
                        uniqueID,
                        screenName,
                        termID,
                        status,
                        screenInstructor,
                        screenInstructorPhone,
                        screenInstructorEmail,
                        screenDate,
                        screenDate2,
                        optionalNotes
                );

                // Insert the new course into the database
                courseRepo.insert(newCourse);
            } else {
                // Create a new Course object with the provided data
                Course updatedCourse = new Course(
                        courseID,
                        screenName,
                        termID,
                        status,
                        screenInstructor,
                        screenInstructorPhone,
                        screenInstructorEmail,
                        screenDate,
                        screenDate2,
                        optionalNotes
                );

                // Update the existing course in the database
                courseRepo.update(updatedCourse);
            }
            // Finish the current activity
            finish();
        }
    }

    /*public void saveCourse(View view) {
        if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()|| editInstructor.getText().toString().trim().isEmpty()
                || editInstructorEmail.getText().toString().trim().isEmpty()|| editInstructorPhone.getText().toString().trim().isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "All required fields must be completed";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            String screenName = editName.getText().toString();
            Date screenDate = ParseDate.dateParse(editSDate.getText().toString());
            Date screenDate2 = ParseDate.dateParse(editEDate.getText().toString());

            String screenInstructor = editInstructor.getText().toString();
            String screenInstructorEmail = editInstructorEmail.getText().toString();
            String screenInstructorPhone = editInstructorPhone.getText().toString();
            String optionNotes= editOptionalText.getText().toString();

            if (courseID == -1) {
                courseID = (assessmentRepo.getAllAssessments().size()-1);
               // courseID = 1;
                System.out.println(courseRepo.getAllCourses().size());
                Course newCourse = new Course(
                        ++courseID,
                        screenName,
                        termID,
                        status,
                        screenInstructor,
                        screenInstructorPhone,
                        screenInstructorEmail,
                        screenDate,
                        screenDate2,optionNotes);
                courseRepo.insert(newCourse);

            } else {
                System.out.println(termID);
                Course oldCourse = new Course(courseID, screenName, termID, status, screenInstructor, screenInstructorPhone, screenInstructorEmail, screenDate, screenDate2,optionNotes);
                courseRepo.update(oldCourse);
            }

        }
        System.out.println("action completed");
        this.finish();
    }*/


    public void onCancel(View view) {
        this.finish();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editSDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}