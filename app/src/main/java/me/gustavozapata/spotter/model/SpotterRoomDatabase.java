package me.gustavozapata.spotter.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import me.gustavozapata.spotter.utils.Converters;

//It inherits the RoomDatabase and allows us to create the database for our app
@Database(entities = {SpotCheck.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class SpotterRoomDatabase extends RoomDatabase {

    public abstract SpotterDao spotterDao();

    private static SpotterRoomDatabase INSTANCE;

    //the singleton design pattern is applied here (where only one instance of the object exists)
    public static SpotterRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (SpotterRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        SpotterRoomDatabase.class, "SPOTTER_DATABASE")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
