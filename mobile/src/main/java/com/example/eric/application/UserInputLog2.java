package com.example.eric.application;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Description of the class:
 * user_log information class.
 * This class keeps track of the participant's information.
 * The ID is saved and also the interactions.
 * An instance of this class is passed between the activities.
 */
public class UserInputLog2 implements Parcelable{

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class variables                                                               //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    private long user_id=0;
    private int versuch;
    private String modalitaet;
    private String prozess_id;
    private String processBlendInTime;
    private String confirmationtime;

    //Namen der Modalität
    public final String MONITOR = "Monitor";
    public final String WATCH = "Watch";
    public final String BRILLE = "Brille";

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // constructors, getters, setters                                                //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    UserInputLog2(){

    }

    UserInputLog2(Parcel in){
        Log.i("UILog Operation","Parcel constructor");
        user_id = in.readLong();
        versuch = in.readInt();
        modalitaet = in.readString();
        prozess_id = in.readString();
        processBlendInTime = in.readString();
        confirmationtime = in.readString();
    }

    public String getConfirmationtime() {
        return confirmationtime;
    }

    public String getModalitaet() {
        return modalitaet;
    }

    public String getProcessBlendInTime() {
        return processBlendInTime;
    }

    public String getProzess_id() {
        return prozess_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public int getVersuch() {
        return versuch;
    }

    public void setConfirmationtime(String ConfirmationTime) {
        this.confirmationtime = ConfirmationTime;
    }

    public void setModalitaet(String modalitaet) {
        this.modalitaet = modalitaet;
    }

    public void setProzessBlendInTime(String popuptime) {
        this.processBlendInTime = popuptime;
    }

    public void setProzess_id(String prozess_id) {
        this.prozess_id = prozess_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setVersuch(int versuch) {
        this.versuch = versuch;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //                                                                               //
    // class functions to provide the essential class functionality                  //
    //                                                                               //
    ///////////////////////////////////////////////////////////////////////////////////

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i("UILog2 Operation", "writeToParcel");
        dest.writeLong(user_id);
        dest.writeInt(versuch);
        dest.writeString(modalitaet);
        dest.writeString(prozess_id);
        dest.writeString(processBlendInTime);
        dest.writeString(confirmationtime);
    }

    //To pass Log Information, make UserInput parcelable
    public static final Parcelable.Creator<UserInputLog2> CREATOR =
            new Parcelable.Creator<UserInputLog2>(){

                @Override
                public UserInputLog2 createFromParcel(Parcel source) {
                    Log.i("UILog2 Operation","Create From Parcel");
                    return new UserInputLog2(source);
                }

                @Override
                public UserInputLog2[] newArray(int size){
                    Log.i("UILog2 Operation","newArray");
                    return new UserInputLog2[size];
                }
            };
}
