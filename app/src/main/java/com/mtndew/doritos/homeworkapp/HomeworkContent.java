package com.mtndew.doritos.homeworkapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//custom homework with additional fields (priority and notes)
public class HomeworkContent implements Serializable {

    public static List<Homework> HOMEWORKS = new ArrayList<>();
    public static Map<String, Homework> HOMEWORK_MAP = new HashMap<>();
    public static Integer currentId = 1;

    static {
    }

    public static void addItem(Homework item) {
        HOMEWORKS.add(item);
        HOMEWORK_MAP.put(item.mId, item);
        currentId ++;
    }

    public static class Homework implements Serializable {
        public String mName;
        public String mSubject;
        public Boolean mDone;
        public GregorianCalendar mDueDate;
        public GregorianCalendar mRemindDate;
        public Integer mPriority;
        public String mNotes;
        public String mId;

        public Homework(String mName, String mSubject, Boolean mDone, GregorianCalendar mDueDate, GregorianCalendar mRemindDate, String mNotes, Integer mPriority, String mId) {
            this.mName = mName;
            this.mSubject = mSubject;
            this.mDone = mDone;
            this.mDueDate = mDueDate;
            this.mRemindDate = mRemindDate;
            this.mPriority = mPriority;
            this.mNotes = mNotes;
            this.mId = mId;
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

        public Boolean getmDone() {
            return mDone;
        }

        public void setmDone(Boolean mDone) {
            this.mDone = mDone;
        }

        public GregorianCalendar getmDueDate() {
            return mDueDate;
        }

        public void setmDueDate(GregorianCalendar mDueDate) {
            this.mDueDate = mDueDate;
        }

        public GregorianCalendar getmRemindDate() {
            return mRemindDate;
        }

        public void setmRemindDate(GregorianCalendar mRemindDate) {
            this.mRemindDate = mRemindDate;
        }

        public String getmNotes() {
            return mNotes;
        }

        public void setmNotes(String mNotes) {
            this.mNotes = mNotes;
        }

        public Integer getmPriority() {
            return mPriority;
        }

        public void setmPriority(Integer mPriority) {
            this.mPriority = mPriority;
        }

        public String getmId() {
            return mId;
        }

        public void setmId(String mId) {
            this.mId = mId;
        }
    }
}
