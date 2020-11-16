package me.gustavozapata.spotter.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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

    public LiveData<SpotCheck> getSpotCheck(String id){
        return spotterDao.getSpotCheck(id);
    }
    public SpotCheck getOne(String id){
        new GetOneAsyncTask(spotterDao).execute(id);
        return spotterDao.getOne(id);
    }
    public void update(SpotCheck spotCheck){
        new UpdateAsyncTask(spotterDao).execute(spotCheck);
    }
    public void delete(SpotCheck spotCheck){
        new DeleteAsyncTask(spotterDao).execute(spotCheck);
    }
    public void deleteAll(){
        new DeleteAllAsyncTask(spotterDao).execute();
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

    //Get one
    private static class GetOneAsyncTask extends AsyncTask<String, Void, Void> {
        private SpotterDao asyncTaskDao;
        GetOneAsyncTask(SpotterDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(String... id) {
            asyncTaskDao.getOne(id[0]);
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
    //Delete All
    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private SpotterDao asyncTaskDao;
        DeleteAllAsyncTask(SpotterDao dao){
            asyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }
}
