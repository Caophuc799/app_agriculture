package vn.edu.hcmut.agricultureapp.myAccount;

/**
 * Created by vutuananh on 5/1/2018.
 */

public class Account {
    public String uid="";
    public String full_name="";
    public String email_address="";
    public String phone_number="";
    public String image_uri="";
    public boolean isFarmer = false;

    public Account(String userId, String fullName, String email, String phoneNumber, String imageUri,boolean isFarmer){
        uid =userId;
        full_name = fullName;
        email_address = email;
        phone_number = phoneNumber;
        image_uri = imageUri;
        this.isFarmer  = isFarmer;
    }

    public Account(String fullName, String email, String phoneNumber, String imageUri,boolean isFarmer){
        full_name = fullName;
        email_address = email;
        phone_number = phoneNumber;
        image_uri = imageUri;
        this.isFarmer = isFarmer;
    }

//    public String getUid() {
//        return uid;
//    }
//
//    public String getFullName() {
//        return full_name;
//    }
//
//    public String getEmailAddress() {
//        return email_address;
//    }
//
//    public String getPhoneNumber() {
//        return phone_number;
//    }
//
//    public String getImageUri() {
//        return image_uri;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public void setFullName(String full_name) {
//        this.full_name = full_name;
//    }
//
//    public void setEmailAddress(String email_address) {
//        this.email_address = email_address;
//    }
//
//    public void setPhoneNumber(String phone_number) {
//        this.phone_number = phone_number;
//    }
//
//    public void setImageUri(String image_uri) {
//        this.image_uri = image_uri;
//    }
}
