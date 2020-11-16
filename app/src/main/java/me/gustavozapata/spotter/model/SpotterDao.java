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


    @Query("SELECT * FROM SpotCheck WHERE id = :id")
    LiveData<SpotCheck> getSpotCheck(String id);

    @Query("SELECT * FROM SpotCheck WHERE id = :id")
    SpotCheck getOne(String id);

    @Update
    void update(SpotCheck spotCheck);

    @Delete
    void delete(SpotCheck spotCheck);

    @Query("DELETE FROM SpotCheck")
    void deleteAll();
}
