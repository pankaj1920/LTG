package com.svgptechnologies.ltg.Json.DriverJson.DriverNamePassDialog;

public class DriverIdPassDialogData {

    String status;
    String username;
    String password;


    public DriverIdPassDialogData(String status, String username, String password) {
        this.status = status;
        this.username = username;
        this.password = password;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
