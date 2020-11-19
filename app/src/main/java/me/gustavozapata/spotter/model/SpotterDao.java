package me.gustavozapata.spotter.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SpotterDao {

    @Query("SELECT * FROM SpotCheck ORDER BY date DESC")
    LiveData<List<SpotCheck>> getAllSpotChecks();

    @Insert
    void insert(SpotCheck spotCheck);

    @Update
    void update(SpotCheck spotCheck);

    @Delete
    void delete(SpotCheck spotCheck);

    @Query("SELECT * from SpotCheck WHERE numberPlate LIKE '%' || :plate || '%' ORDER BY numberPlate ASC")
    LiveData<List<SpotCheck>> searchNumberPlates(String plate);
}
