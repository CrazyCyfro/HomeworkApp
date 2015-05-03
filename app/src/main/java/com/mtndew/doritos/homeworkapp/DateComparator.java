package com.mtndew.doritos.homeworkapp;


import java.util.Comparator;

//date comparator to sort homeworks by date
public class DateComparator implements Comparator<HomeworkContent.Homework> {
    @Override
    public int compare(HomeworkContent.Homework hw1, HomeworkContent.Homework hw2) {
        return hw1.getmDueDate().compareTo(hw2.getmDueDate());
    }
}
