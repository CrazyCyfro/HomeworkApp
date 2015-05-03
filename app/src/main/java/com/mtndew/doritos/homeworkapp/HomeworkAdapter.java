package com.mtndew.doritos.homeworkapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class HomeworkAdapter extends ArrayAdapter<HomeworkContent.Homework> {
    private List<HomeworkContent.Homework> mHomeworkList;
    private Context context;
    private SimpleDateFormat simpleDateFormat;


    public HomeworkAdapter(Context context, int resource, List<HomeworkContent.Homework> mHomeworkList) {
        super(context, resource, mHomeworkList);
        this.mHomeworkList = mHomeworkList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_homework_item, null);
        }

        TextView homeworkName = (TextView)convertView.findViewById(R.id.homework_name);
        homeworkName.setText(mHomeworkList.get(position).getmName());

        //formats due date in the list
        simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setCalendar(mHomeworkList.get(position).getmDueDate());
        TextView homeworkDate = (TextView)convertView.findViewById(R.id.homework_date);
        homeworkDate.setText(simpleDateFormat.getDateInstance().format(mHomeworkList.get(position).getmDueDate().getTime()));

        //displays an icon if homework is not done
        ImageView homeworkIcon = (ImageView)convertView.findViewById(R.id.homework_icon);
        if (!mHomeworkList.get(position).getmDone()) {
            homeworkIcon.setVisibility(View.VISIBLE);
        } else {
            homeworkIcon.setVisibility(View.INVISIBLE);
        }

        //colour codes the homework according to whether it's overdue, done, and it's priority
        if (mHomeworkList.get(position).getmDone()) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.done));
        } else if (mHomeworkList.get(position).getmDueDate().compareTo(new GregorianCalendar()) == -1){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.overdue));
        } else {
            if (mHomeworkList.get(position).getmPriority() == 1) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.low_priority));
            } else if (mHomeworkList.get(position).getmPriority() == 2) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.med_priority));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.high_priority));
            }
        }

        return convertView;
    }
}
