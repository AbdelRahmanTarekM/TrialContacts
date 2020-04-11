package com.example.sherif.trialcontacts;

/**
 * Created by Sherif on 6/26/2018.
 */

public class Contact {

    private String contactName;
    private String contactNumber;
    private boolean isPhone;

    public Contact( String contactName, String contactNumber, boolean isPhone) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.isPhone = isPhone;
    }

    public Contact() {
        //empty constructor for GSON
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(boolean phone) {
        isPhone = phone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
