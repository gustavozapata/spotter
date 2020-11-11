package me.gustavozapata.spotter;

public class SpotCheckRow {
    public String date;
    public String location;
    public String plateNumber;
    public String carMake;
    public String carModel;
    public String result;

    public SpotCheckRow(String date, String location, String plateNumber, String carMake, String carModel, String result) {
        this.date = date;
        this.location = location;
        this.plateNumber = plateNumber;
        this.carMake = carMake;
        this.carModel = carModel;
        this.result = result;
    }
}