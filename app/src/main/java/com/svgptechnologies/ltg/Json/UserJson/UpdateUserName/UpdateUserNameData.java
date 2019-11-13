package com.svgptechnologies.ltg.Json.UserJson.UpdateUserName;

public class UpdateUserNameData {

    String mobile;
    String name;

    public UpdateUserNameData(String mobile, String name) {
        this.mobile = mobile;
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
