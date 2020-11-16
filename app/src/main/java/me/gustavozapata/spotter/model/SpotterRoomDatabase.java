package me.gustavozapata.spotter.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SpotCheck.class}, version = 3)
public abstract class SpotterRoomDatabase extends RoomDatabase {

    public abstract SpotterDao spotterDao();

    private static SpotterRoomDatabase INSTANCE;

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
