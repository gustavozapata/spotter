package me.gustavozapata.spotter.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.gustavozapata.spotter.model.SpotCheck;
import me.gustavozapata.spotter.model.SpotterRepo;

public class SpotCheckViewModel extends AndroidViewModel {

    private SpotterRepo repo;
    private LiveData<List<SpotCheck>> allSpotChecks;

    public SpotCheckViewModel(@NonNull Application application) {
        super(application);
        repo = new SpotterRepo(application);
        allSpotChecks = repo.getAllSpotChecks();
    }

    public LiveData<List<SpotCheck>> getAllSpotChecks() {
        return allSpotChecks;
    }

    public void insert(SpotCheck spotCheck) {
        repo.insert(spotCheck);
    }


    public LiveData<SpotCheck> getSpotCheck(String id) {
        return repo.getSpotCheck(id);
    }
//    public SpotCheck getOne(String id){
//        return repo.getOne(id);
//    }
    public void update(SpotCheck spotCheck){
        repo.update(spotCheck);
    }
    public void delete(SpotCheck spotCheck){
        repo.delete(spotCheck);
    }
    public void deleteAll(){
        repo.deleteAll();
    }
}
