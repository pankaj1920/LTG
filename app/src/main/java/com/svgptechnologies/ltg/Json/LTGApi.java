package com.svgptechnologies.ltg.Json;

import com.svgptechnologies.ltg.Json.CustomerCare.CustomerCareResponse;
import com.svgptechnologies.ltg.Json.DriverJson.CallClickCount.CallClickCountResponse;
import com.svgptechnologies.ltg.Json.DriverJson.CancleBooking.CancleBookingResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverForgetPassword.DriverForgotChangePasswordResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverForgorPasswordResendOTP.DriverForgorPasswordResendOTPResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverNamePassDialog.DriveridPassDialogResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverRegistration.DriverRegistrationResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverResendOTP.DriverResendOTPResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverUpdatePassword.DriverUpdatePasswordResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverVerifyOtpResponse.DriverVerifyOtpRespone;
import com.svgptechnologies.ltg.Json.DriverJson.GetDriverDetails.GetDriverDetailsResponse;
import com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation.GetUserLocationResponse;
import com.svgptechnologies.ltg.Json.DriverJson.PostDriverCurrentLocation.PostDriverCurrentLocationResponse;
import com.svgptechnologies.ltg.Json.DriverJson.SelectServiceType.SelectServiceTypeResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail.UpdateAllSettinOTPVerification.UpdateSettingOtpResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail.UpdateDriverAllDetailResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail.UpdateDriverAllSettingImage.UpdateDriverAllSettingImageResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAvilability.UpdateDriverAviabilityResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverEmail.DriverUpdateEmailResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverName.UpdateDriverNameResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UploadDriverImage.DriverImageUploadResponse;
import com.svgptechnologies.ltg.Json.UserJson.GetUserDetail.GetUserDetailResponse;
import com.svgptechnologies.ltg.Json.UserJson.PostUserCurrentLocation.PostUSerCurrentLocationResponse;
import com.svgptechnologies.ltg.Json.UserJson.SearchDriver.SearchDriverResponse;
import com.svgptechnologies.ltg.Json.UserJson.SendUserLocationToDriver.SendUserLocationToDriverResponse;
import com.svgptechnologies.ltg.Json.UserJson.USerVerifyOtpResponse.UserVerifyOtpResponse;
import com.svgptechnologies.ltg.Json.UserJson.UpdateUserEmail.UpdateUserEmailResponse;
import com.svgptechnologies.ltg.Json.UserJson.UpdateUserName.UpdateUserNameResponse;
import com.svgptechnologies.ltg.Json.UserJson.UpdateUserPassword.UpdateUserPasswordResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserForgetPassword.UserForgotChangePasswordResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserForgotPasswordResendOtp.UserResendforgotPasswordResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserUploadImage.UserImageUploadResponse;
import com.svgptechnologies.ltg.Json.UserJson.VechileCategory.VechileCategoryResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LTGApi {


    //Getting UserDetail
    @GET("user_details.php")
    Call<GetUserDetailResponse> getUserDetail(
            @Query("mobile") String mobile
    );

    //this is for user login
    @FormUrlEncoded
    @POST("user_login.php")
    Call<UserLoginResponse> UserLogin(
            @Field("username") String useername,
            @Field("password") String password,
            @Field("token") String token);


    @GET("driver_details.php")
    Call<GetDriverDetailsResponse> getDriverDetail(
            @Query("driver_id") String driver_id
    );


    //this is for user registration
    @FormUrlEncoded
    @POST("otp_user.php")
    Call<ResponseBody> UserRigestration(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("cpassword") String cpassword
    );


    //This is for User Otp Verification
    @FormUrlEncoded
    @POST("verify.php")
    Call<UserVerifyOtpResponse> verifyOtp(
            @Query("mobile") String mobile,
            @Field("otp") String otp,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("user_resend_otp.php")
    Call<UserVerifyOtpResponse> resendUserOTp(
            @Field("mobile") String mobile
    );


    //Update User Name
    @FormUrlEncoded
    @POST("update_user_name.php")
    Call<UpdateUserNameResponse> updateUserName(
            @Query("mobile") String mobile,
            @Field("name") String name
    );

    //update user Email
    @FormUrlEncoded
    @POST("update_user_email.php")
    Call<UpdateUserEmailResponse> updateUserEmail(
            @Query("mobile") String mobile,
            @Field("email") String email
    );

    //update user Password
    @FormUrlEncoded
    @POST("update_user_password.php")
    Call<UpdateUserPasswordResponse> updateUserPassword(
            @Query("mobile") String mobile,
            @Field("password") String password
    );


    //Upload User Image
    @Multipart
    @POST("update_user_image.php")
    Call<UserImageUploadResponse> uploadUserImage(
            @Query("mobile") String mobile,
            @Part MultipartBody.Part image);


    // here both Json object for CallClickCount and SendRequestCount are same
    // so we used CallClickCountResponse for both callcount.php and requestcount.php
    @FormUrlEncoded
    @POST("callcount.php")
    Call<CallClickCountResponse> countCallBtnClick(
            @Query("did") String did,
            @Field("count") String count
    );


    // here both Json object for CallClickCount and SendRequestCount are same
    // so we used CallClickCountResponse for both callcount.php and requestcount.php
    @FormUrlEncoded
    @POST("requestcount.php")
    Call<CallClickCountResponse> countSendRequestBtnClick(
            @Query("did") String did,
            @Field("count") String count
    );


    @FormUrlEncoded
    @POST("send_user_location.php")
    Call<SendUserLocationToDriverResponse> sendUserLocationToDriver(
            @Query("did") String did,
            @Field("uid") String uid
    );


    @FormUrlEncoded
    @POST("post_user_location.php")
    Call<PostUSerCurrentLocationResponse> postUserLocation(
            @Query("uid") String uid,
            @Field("address") String address,
            @Field("pincode") String pincode,
            @Field("lat") String lat,
            @Field("lang") String lang,
            @Field("latlang") String latlang
    );


    //User ForgetPassword Otp Generator
    @FormUrlEncoded
    @POST("user_forgotpassword.php")
    Call<ResponseBody> userForgotPassOtp(
            @Field("mobile") String mobile
    );


    @POST("user_resend_forgotpassword.php")
    Call<UserResendforgotPasswordResponse> userForgotPasswordResendOtp(
            @Query("mobile") String mobile
    );


    //User Change Forgot Password
    @FormUrlEncoded
    @POST("user_changepass.php")
    Call<UserForgotChangePasswordResponse> userFogetChangePassword(
            @Query("mobile") String Qmobile,
            @Field("otp") String otp,
            @Field("password") String password,
            @Field("cpassword") String cpassword,
            @Field("mobile") String mobile
    );


    @GET("vehicle_category.php")
    Call<VechileCategoryResponse> getVechileCategory();


    @GET("search_driver.php")
    Call<SearchDriverResponse> searchDriver(
            @Query("pincode") String pincode,
            @Query("vehicle") String vechile,
            @Query("status") String status
    );


    @FormUrlEncoded
    @POST("customercare.php")
    Call<CustomerCareResponse> getCustomerRequest(
            @Field("name") String name,
            @Field("language") String language,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("message") String message
    );


    //this is for driver login
    @FormUrlEncoded
    @POST("driver_login.php")
    Call<DriverLoginResponse> DriverLogin(
            @Field("username") String useername,
            @Field("password") String password,
            @Field("token") String token);


    //this is for DriverRegistration
    @FormUrlEncoded
    @POST("otp_driver.php")
    Call<DriverRegistrationResponse> DriverRigestration(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password
    );


    //This is for Driver Otp Verification
    @FormUrlEncoded
    @POST("driver_verify.php")
    Call<DriverVerifyOtpRespone> DriverVerifyOtp(
            @Query("driver_id") String driverId,
            @Field("otp") String otp,
            @Field("token") String token);


    // Update Driver Name
    @FormUrlEncoded
    @POST("update_driver_name.php")
    Call<UpdateDriverNameResponse> updateDriverName(
            @Query("driver_id") String driver_id,
            @Field("name") String name
    );

    //Update Driver Password
    @FormUrlEncoded
    @POST("update_driver_password.php")
    Call<DriverUpdatePasswordResponse> updateDriverPassword(
            @Query("driver_id") String driver_id,
            @Field("password") String password
    );

    //update Driver Email
    @FormUrlEncoded
    @POST("update_driver_email.php")
    Call<DriverUpdateEmailResponse> updateDriverEmail(
            @Query("driver_id") String driver_Id,
            @Field("email") String email
    );


    //Driver ForgetPassword Otp Generator
    @FormUrlEncoded
    @POST("driver_forgotpassword.php")
    Call<ResponseBody> driverForgotPassOtp(
            @Field("driver_id") String driverId
    );


    //Driver Resend ForgetPassword Otp

    @POST("driver_resend_forgotpass.php")
    Call<DriverForgorPasswordResendOTPResponse> driverResendForgotPassOtp(
            @Query("driver_id") String driverId
    );


    //User Change Forgot Password
    @FormUrlEncoded
    @POST("driver_changepass.php")
    Call<DriverForgotChangePasswordResponse> driverFogetChangePassword(
            @Query("driver_id") String driver_id,
            @Field("otp") String otp,
            @Field("password") String password,
            @Field("cpassword") String cpassword
    );


    //User Change Forgot Password
    @FormUrlEncoded
    @POST("update_driver_availability.php")
    Call<UpdateDriverAviabilityResponse> updateDriverAvilability(
            @Query("driver_id") String driverId,
            @Field("avail") String checked

    );


    //Upload User Image
    @Multipart
    @POST("update_driver_image.php")
    Call<DriverImageUploadResponse> uploadDriverImage(
            @Query("driver_id") String driver_id,
            @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("post_driver_location.php")
    Call<PostDriverCurrentLocationResponse> postDriverLocation(
            @Query("did") String did,
            @Field("address") String address,
            @Field("pincode") String pincode,
            @Field("lat") String lat,
            @Field("lang") String lang,
            @Field("latlang") String latlang
    );


    @FormUrlEncoded
    @POST("driver_resend_otp.php")
    Call<DriverResendOTPResponse> resendOtpToDriver(
            @Field("vehicle_no") String vechile_Num
    );

    // update Driver All Detail at once
    @FormUrlEncoded
    @POST("update_driver_details_otp.php")
    Call<UpdateDriverAllDetailResponse> updateDriverAllDetail(
            @Query("driver_id") String driverId,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("dmobile") String dmobile,
            @Field("address") String address
    );

    @Multipart
    @POST("update_driver_detail_image.php")
    Call<UpdateDriverAllSettingImageResponse> uploadDriveAllSettingImage(
            @Query("driver_id") String driver_id,
            @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("update_driver_details.php")
    Call<UpdateSettingOtpResponse> updateDriverAllSetting(
            @Query("driver_id") String driverId,
            @Field("otp") String otp
    );

    @GET("driver_login_details.php")
    Call<DriveridPassDialogResponse> getDriverIdPass(
            @Query("driver_id") String driverId
    );


    @GET("vehicle_list.php")
    Call<SelectServiceTypeResponse> getServiceList();

    @GET("get_user_location.php")
    Call<GetUserLocationResponse> getUserLocation(
            @Query("did") String driverId
    );

    @FormUrlEncoded
    @POST("cancelled_trip.php")
    Call<CancleBookingResponse> cancleBooking(
            @Query("did") String did,
            @Field("trip_status") String trip_status
    );

}
