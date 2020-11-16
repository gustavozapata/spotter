package me.gustavozapata.spotter.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity
public class SpotCheck {

    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    private String numberPlate;

    @NonNull
    private String date;

    private String location;

    private String carMake;
    private String carModel;

    @NonNull
    private String result;

    private String notes;

    public SpotCheck(@NonNull String numberPlate, @NonNull String date, String location, String carMake, String carModel, @NonNull String result, String notes) {
        this.id = UUID.randomUUID().toString();
        this.numberPlate = numberPlate;
        this.date = date;
        this.location = location;
        this.carMake = carMake;
        this.carModel = carModel;
        this.result = result;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getResult() {
        return result;
    }

    public String getNotes() {
        return notes;
    }
}
