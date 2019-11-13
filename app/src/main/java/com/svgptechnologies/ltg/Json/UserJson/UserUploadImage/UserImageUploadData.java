package com.svgptechnologies.ltg.Json.UserJson.UserUploadImage;

public class UserImageUploadData {

    String mobile;
    String image;

    public UserImageUploadData(String mobile, String image) {
        this.mobile = mobile;
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
