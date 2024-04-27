package com.example.c196schedulingapp.Database;

import android.app.Application;

import com.example.c196schedulingapp.DAO.TermDAO;
import com.example.c196schedulingapp.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TermRepo {
    private TermDAO mTermDAO;
    private List<Term> mAllTerms;
    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public TermRepo(Application application){
        DatabaseBuilder db= DatabaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()-> mAllTerms=mTermDAO.getAllTerms());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public void insert(Term term){
        databaseExecutor.execute(()-> mTermDAO.insert(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void update(Term term){
        databaseExecutor.execute(()-> mTermDAO.update(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void delete(Term term){
        databaseExecutor.execute(()-> mTermDAO.delete(term));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
