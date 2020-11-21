package me.gustavozapata.spotter.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class SpotterRepo {
    private SpotterDao spotterDao;
    private LiveData<List<SpotCheck>> allSpotChecks;

    public SpotterRepo(Application application) {
        SpotterRoomDatabase db = SpotterRoomDatabase.getDatabase(application);
        spotterDao = db.spotterDao();
        allSpotChecks = spotterDao.getAllSpotChecks();
    }

    public LiveData<List<SpotCheck>> getAllSpotChecks(){
        return allSpotChecks;
    }

    public void insert(SpotCheck spotCheck){
        new InsertAsyncTask(spotterDao).execute(spotCheck);
    }

    public void update(SpotCheck spotCheck){
        new UpdateAsyncTask(spotterDao).execute(spotCheck);
    }

    public void delete(SpotCheck spotCheck){
        new DeleteAsyncTask(spotterDao).execute(spotCheck);
    }

    //Search
    public LiveData<List<SpotCheck>> searchByAllFields(String term){
        return spotterDao.searchByAllFields(term);
    }
    public LiveData<List<SpotCheck>> searchByDate(Date date){
        return spotterDao.searchByDate(date);
    }

    //Insert
    private static class InsertAsyncTask extends AsyncTask<SpotCheck, Void, Void> {
        private SpotterDao asyncTaskDao;

        InsertAsyncTask(SpotterDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SpotCheck... spotChecks) {
            asyncTaskDao.insert(spotChecks[0]);
            return null;
        }
    }

    //Update
    private static class UpdateAsyncTask extends AsyncTask<SpotCheck, Void, Void> {
        private SpotterDao asyncTaskDao;
        UpdateAsyncTask(SpotterDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(SpotCheck... spotChecks) {
            asyncTaskDao.update(spotChecks[0]);
            return null;
        }
    }

    //Delete
    private static class DeleteAsyncTask extends AsyncTask<SpotCheck, Void, Void> {
        private SpotterDao asyncTaskDao;
        DeleteAsyncTask(SpotterDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(SpotCheck... spotChecks) {
            asyncTaskDao.delete(spotChecks[0]);
            return null;
        }
    }
}
