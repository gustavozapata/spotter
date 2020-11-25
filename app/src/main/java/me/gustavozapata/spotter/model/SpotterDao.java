package me.gustavozapata.spotter.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

//this interface defines the methods that will modify the database and it takes the actual SQL queries
@Dao
public interface SpotterDao {

    @Query("SELECT * FROM SpotCheck")
    LiveData<List<SpotCheck>> getAllSpotChecks();

    @Insert
    void insert(SpotCheck spotCheck);

    @Update
    void update(SpotCheck spotCheck);

    @Delete
    void delete(SpotCheck spotCheck);

    @Query("SELECT * from SpotCheck WHERE numberPlate LIKE '%' || :term || '%'" +
            "OR carMake LIKE '%' || :term || '%' " +
            "OR carModel LIKE '%' || :term || '%' " +
            "OR result LIKE '%' || :term || '%' " +
            "OR notes LIKE '%' || :term || '%' " +
            "OR location LIKE '%' || :term || '%' ")
    LiveData<List<SpotCheck>> searchByAllFields(String term);

    @Query("SELECT * FROM SpotCheck WHERE date = :date")
    LiveData<List<SpotCheck>> searchByDate(Date date);
}
