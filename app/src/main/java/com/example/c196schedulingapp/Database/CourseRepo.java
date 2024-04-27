package com.example.c196schedulingapp.Database;

import android.app.Application;

import com.example.c196schedulingapp.DAO.CourseDAO;
import com.example.c196schedulingapp.Entity.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepo {
    private CourseDAO mCourseDAO;
    private List<Course> mAllCourses;
    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public CourseRepo(Application application){
        DatabaseBuilder db= DatabaseBuilder.getDatabase(application);
        mCourseDAO = db.courseDAO();
    }

    public List<Course> getAllCourses(){
        databaseExecutor.execute(()-> mAllCourses=mCourseDAO.getAllCourses());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course){
        databaseExecutor.execute(()-> mCourseDAO.insert(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Course course){
        databaseExecutor.execute(()-> mCourseDAO.update(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Course course){
        databaseExecutor.execute(()-> mCourseDAO.delete(course));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
