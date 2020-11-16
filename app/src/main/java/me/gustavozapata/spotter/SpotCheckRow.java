package me.gustavozapata.spotter;

public class SpotCheckRow {
    public String date;
    public String location;
    public String numberPlate;
    public String carMake;
    public String carModel;
    public String result;

    public SpotCheckRow(String date, String location, String numberPlate, String carMake, String carModel, String result) {
        this.date = date;
        this.location = location;
        this.numberPlate = numberPlate;
        this.carMake = carMake;
        this.carModel = carModel;
        this.result = result;
    }
}