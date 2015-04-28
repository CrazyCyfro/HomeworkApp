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

        simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setCalendar(mHomeworkList.get(position).getmDueDate());
        TextView homeworkDate = (TextView)convertView.findViewById(R.id.homework_date);
        homeworkDate.setText(simpleDateFormat.getDateInstance().format(mHomeworkList.get(position).getmDueDate().getTime()));

        ImageView homeworkIcon = (ImageView)convertView.findViewById(R.id.homework_icon);
        if (!mHomeworkList.get(position).getmDone()) {
            homeworkIcon.setVisibility(View.VISIBLE);
        } else {
            homeworkIcon.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
