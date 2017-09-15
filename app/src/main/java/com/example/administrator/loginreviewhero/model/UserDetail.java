package com.example.administrator.loginreviewhero.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserDetail implements Parcelable{
    @SerializedName("member_id")
    private String memberId;
    @SerializedName("member_username")
    private String memberUsername;
    @SerializedName("member_password")
    private String memberPassword;
    @SerializedName("member_name")
    private String memberName;
    @SerializedName("member_email")
    private String memberEmail;
    @SerializedName("member_tel")
    private String memberTel;
    @SerializedName("member_img")
    private String memberImg;
    @SerializedName("online_status")
    private String onlineStatus;
    @SerializedName("admin_status")
    private String adminStatus;

    public String getMemberId() {
        return memberId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memberId);
        dest.writeString(memberUsername);
        dest.writeString(memberPassword);
        dest.writeString(memberName);
        dest.writeString(memberEmail);
        dest.writeString(memberTel);
        dest.writeString(memberImg);
        dest.writeString(onlineStatus);
        dest.writeString(adminStatus);
    }

    protected UserDetail(Parcel in) {
        memberId = in.readString();
        memberUsername = in.readString();
        memberPassword = in.readString();
        memberName = in.readString();
        memberEmail = in.readString();
        memberTel = in.readString();
        memberImg = in.readString();
        onlineStatus = in.readString();
        adminStatus = in.readString();
    }

    public static final Creator<UserDetail> CREATOR = new Creator<UserDetail>() {
        @Override
        public UserDetail createFromParcel(Parcel in) {
            return new UserDetail(in);
        }

        @Override
        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };
}
