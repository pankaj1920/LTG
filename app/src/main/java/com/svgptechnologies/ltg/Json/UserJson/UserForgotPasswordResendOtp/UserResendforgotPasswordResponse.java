package com.svgptechnologies.ltg.Json.UserJson.UserForgotPasswordResendOtp;

public class UserResendforgotPasswordResponse {

    UserResendforgotPasswordData data;

    public UserResendforgotPasswordResponse(UserResendforgotPasswordData data) {
        this.data = data;
    }

    public UserResendforgotPasswordData getData() {
        return data;
    }

    public void setData(UserResendforgotPasswordData data) {
        this.data = data;
    }
}
