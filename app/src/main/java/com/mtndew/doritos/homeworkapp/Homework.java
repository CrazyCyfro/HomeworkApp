package com.mtndew.doritos.homeworkapp;

import java.util.Date;

public class Homework {
    private String mName;
    private String mSubject;
    private Boolean mDone;
    private Date mDueDate;
    private Date mRemindDate;
    private String mNotes;

    public Homework(String mName, String mSubject, Boolean mDone, Date mDueDate, Date mRemindDate, String mNotes) {
        this.mName = mName;
        this.mSubject = mSubject;
        this.mDone = mDone;
        this.mDueDate = mDueDate;
        this.mRemindDate = mRemindDate;
        this.mNotes = mNotes;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public Boolean getmCompleted() {
        return mDone;
    }

    public void setmCompleted(Boolean mDone) {
        this.mDone = mDone;
    }

    public Date getmDueDate() {
        return mDueDate;
    }

    public void setmDueDate(Date mDueDate) {
        this.mDueDate = mDueDate;
    }

    public Date getmRemindDate() {
        return mRemindDate;
    }

    public void setmRemindDate(Date mRemindDate) {
        this.mRemindDate = mRemindDate;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }
}
