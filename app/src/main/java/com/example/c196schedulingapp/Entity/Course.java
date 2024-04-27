package com.example.c196schedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.EnumMap;

@Entity(tableName = "course_table")

public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private int termID;
    private String courseTitle;
    private Date startDate;
    private Date endDate;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String optionalNotes;

    public Course(int courseID, String courseTitle, int termID, String status, String instructorName, String instructorPhone, String instructorEmail, Date startDate, Date endDate, String optionalNotes) {
        this.termID = termID;
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName=instructorName;
        this.instructorPhone=instructorPhone;
        this.instructorEmail=instructorEmail;
        this.optionalNotes=optionalNotes;

    }

    public int getTermID() {
        return termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    //override to string method
    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                '}';
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getOptionalNotes() {
        return optionalNotes;
    }

    public void setOptionalNotes(String optionalNotes) {
        this.optionalNotes = optionalNotes;
    }
}
