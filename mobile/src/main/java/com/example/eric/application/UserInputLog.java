package com.example.eric.application;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by e.lessen on 16.11.2015.
 */
public class UserInputLog implements Parcelable{
    private long user_id=0;
    private int versuch;
    private String modalitaet;
    private String alarmtyp;
    private String clickedButtonType;
    private String popuptime;
    private String clicktime;

    //Namen der Modalität
    public final String MONITOR = "Monitor";
    public final String BRILLE = "Brille";
    public final String WATCH = "Watch";

    UserInputLog(){

    }

    UserInputLog(Parcel in){
        Log.i("UILog Operation","Parcel constructor");
        user_id = in.readLong();
        versuch = in.readInt();
        modalitaet = in.readString();
        alarmtyp = in.readString();
        clickedButtonType = in.readString();
        popuptime = in.readString();
        clicktime = in.readString();
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getVersuch() {
        return versuch;
    }

    public void setVersuch(int versuch) {
        this.versuch = versuch;
    }

    public String getModalitaet() {
        return modalitaet;
    }

    public void setModalitaet(String modalitaet) {
        this.modalitaet = "'" + modalitaet + "'";
    }

    public String getAlarmtyp() {
        return alarmtyp;
    }

    public void setAlarmtyp(String alarmtyp) {
        this.alarmtyp = alarmtyp;
    }

    public String getPopuptime() {
        return popuptime;
    }

    public void setPopuptime(String popuptime) {
        this.popuptime = "'" + popuptime + "'";
    }

    public String getClicktime() {
        return clicktime;
    }

    public void setClicktime(String clicktime) {
        this.clicktime = "'" + clicktime + "'";
    }

    public String getClickedButtonType() {
        return clickedButtonType;
    }

    public void setClickedButtonType(String korrekturbutton) {
        this.clickedButtonType = "'" + korrekturbutton + "'";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i("UILog Operation","writeToParcel");
        dest.writeLong(user_id);
        dest.writeString(modalitaet);
        dest.writeInt(versuch);
        dest.writeString(alarmtyp);
        dest.writeString(clickedButtonType);
        dest.writeString(popuptime);
        dest.writeString(clicktime);
    }

    //To pass Log Information, make UserInput parcelable
    public static final Parcelable.Creator<UserInputLog> CREATOR =
            new Parcelable.Creator<UserInputLog>(){

                @Override
                public UserInputLog createFromParcel(Parcel source) {
                    Log.i("UILog Operation","Create From Parcel");
                    return new UserInputLog(source);
                }

                @Override
                public UserInputLog[] newArray(int size){
                    Log.i("UILog Operation","newArray");
                    return new UserInputLog[size];
                }
            };
}
