package com.developer.tourismapp;

public class myModel {
    String travel_name;
    String travel_mode;
    String mode_number;
    String price_travel;

    public String getMode_id() {
        return mode_id;
    }

    public void setMode_id(String mode_id) {
        this.mode_id = mode_id;
    }

    public String getMode_company() {
        return mode_company;
    }

    public void setMode_company(String mode_company) {
        this.mode_company = mode_company;
    }

    String mode_id;
    String mode_company;

    public myModel() {
    }

    public myModel(String travel_name, String travel_mode, String mode_number, String price_travel, String mode_company, String mode_id) {
        this.travel_name = travel_name;
        this.travel_mode = travel_mode;
        this.mode_number = mode_number;
        this.price_travel = price_travel;
        this.mode_company = mode_company;
        this.mode_id = mode_id;
    }

    public String getTravel_name() {
        return travel_name;
    }

    public void setTravel_name(String travel_name) {
        this.travel_name = travel_name;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getMode_number() {
        return mode_number;
    }

    public void setMode_number(String mode_number) {
        this.mode_number = mode_number;
    }

    public String getPrice_travel() {
        return price_travel;
    }

    public void setPrice_travel(String price_travel) {
        this.price_travel = price_travel;
    }
}
