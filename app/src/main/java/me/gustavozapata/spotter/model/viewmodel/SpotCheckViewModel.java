package me.gustavozapata.spotter.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Date;
import java.util.List;

import me.gustavozapata.spotter.model.SpotCheck;
import me.gustavozapata.spotter.model.SpotterRepo;

//This is similar to the controller from the MVC design pattern - it connects the database (repo, dao) to the view
public class SpotCheckViewModel extends AndroidViewModel {

    private SpotterRepo repo;
    private LiveData<List<SpotCheck>> allSpotChecks;

    public LiveData<List<SpotCheck>> searchByFields;
    public LiveData<List<SpotCheck>> searchByDate;
    public MutableLiveData<String> filterLiveData = new MutableLiveData<>();
    public MutableLiveData<Date> filterLiveDataDate = new MutableLiveData<>();

    public SpotCheckViewModel(@NonNull Application application) {
        super(application);
        repo = new SpotterRepo(application);
        allSpotChecks = repo.getAllSpotChecks();

        searchByFields = Transformations.switchMap(filterLiveData,
                new Function<String, LiveData<List<SpotCheck>>>() {
                    @Override
                    public LiveData<List<SpotCheck>> apply(String term) {
                        return repo.searchByAllFields(term);
                    }
                });

        searchByDate = Transformations.switchMap(filterLiveDataDate,
                new Function<Date, LiveData<List<SpotCheck>>>() {
                    @Override
                    public LiveData<List<SpotCheck>> apply(Date date) {
                        return repo.searchByDate(date);
                    }
                });
    }

    public LiveData<List<SpotCheck>> getAllSpotChecks() {
        return allSpotChecks;
    }

    public void insert(SpotCheck spotCheck) {
        repo.insert(spotCheck);
    }

    public void update(SpotCheck spotCheck){
        repo.update(spotCheck);
    }

    public void delete(SpotCheck spotCheck){
        repo.delete(spotCheck);
    }
}
