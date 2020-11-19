package me.gustavozapata.spotter.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import me.gustavozapata.spotter.model.SpotCheck;
import me.gustavozapata.spotter.model.SpotterRepo;

public class SpotCheckViewModel extends AndroidViewModel {

    private SpotterRepo repo;
    private LiveData<List<SpotCheck>> allSpotChecks;

    public LiveData<List<SpotCheck>> searchByPlates;
    public MutableLiveData<String> filterLiveData = new MutableLiveData<>();

    public SpotCheckViewModel(@NonNull Application application) {
        super(application);
        repo = new SpotterRepo(application);
        allSpotChecks = repo.getAllSpotChecks();

        searchByPlates = Transformations.switchMap(filterLiveData,
                new Function<String, LiveData<List<SpotCheck>>>() {
                    @Override
                    public LiveData<List<SpotCheck>> apply(String plate) {
                        return repo.searchNumberPlates(plate);
                    }
                });
    }

    public LiveData<List<SpotCheck>> getAllSpotChecks() {
        return allSpotChecks;
    }

    public void insert(SpotCheck spotCheck) {
        repo.insert(spotCheck);
    }

    public void delete(SpotCheck spotCheck){
        repo.delete(spotCheck);
    }
}
