package com.example.cardmaker.models;

public class UploadModel {
    String userEmail;
    String userName;
    String userKey;
    String imageUri;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UploadModel(){

    }
    public UploadModel(String userName,String userEmail,String userKey,String imageUri){
        this.userEmail=userEmail;
        this.userKey=userKey;
        this.imageUri=imageUri;
        this.userName=userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
