package me.gustavozapata.spotter.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class SpotCheck {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String numberPlate;

    @NonNull
    private String date;

    @NonNull
    private String result;

    private String location;
    private String carMake;
    private String carModel;
    private String notes;
    private boolean sent;


    public SpotCheck(@NonNull String numberPlate, @NonNull String date, String location, String carMake, String carModel, @NonNull String result, String notes) {
        this.id = UUID.randomUUID().toString();
        this.numberPlate = numberPlate;
        this.date = date;
        this.location = location;
        this.carMake = carMake;
        this.carModel = carModel;
        this.result = result;
        this.notes = notes;
        this.sent = false;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getNumberPlate() {
        return numberPlate;
    }

    @NonNull
    public String getResult() {
        return result;
    }

    public String getLocation() {
        return location;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
