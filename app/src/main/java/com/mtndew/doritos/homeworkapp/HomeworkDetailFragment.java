package com.mtndew.doritos.homeworkapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A fragment representing a single Homework detail screen.
 * This fragment is either contained in a {@link HomeworkListActivity}
 * in two-pane mode (on tablets) or a {@link HomeworkDetailActivity}
 * on handsets.
 */
public class HomeworkDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private HomeworkContent.Homework mHomework;

    private EditText mHomeworkNameEditText;
    private EditText mSubjectEditText;
    private Button mDueDateDateButton;
    private Button mDueDateTimeButton;
    private Button mRemindDateDateButton;
    private Button mRemindDateTimeButton;
    private EditText mNotesEditText;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeworkDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mHomework = HomeworkContent.HOMEWORK_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homework_detail, container, false);

        if (mHomework != null) {
            mHomeworkNameEditText = (EditText)rootView.findViewById(R.id.name_edittext);
            mSubjectEditText = (EditText)rootView.findViewById(R.id.subject_edittext);
            mDueDateDateButton = (Button)rootView.findViewById(R.id.due_date_date_button);
            mDueDateTimeButton = (Button)rootView.findViewById(R.id.due_date_time_button);
            mRemindDateDateButton = (Button)rootView.findViewById(R.id.reminder_date_date_button);
            mRemindDateTimeButton = (Button)rootView.findViewById(R.id.reminder_date_time_button);
            mNotesEditText = (EditText)rootView.findViewById(R.id.notes_edittext);

            mHomeworkNameEditText.setText(mHomework.getmName());
            mSubjectEditText.setText(mHomework.getmSubject());
            mDueDateDateButton.setText(String.valueOf(mHomework.getmDueDate().get(Calendar.DATE)) + "/" + String.valueOf(mHomework.getmDueDate().get(Calendar.MONTH))+"/"+String.valueOf(mHomework.getmDueDate().get(Calendar.YEAR)));
            mDueDateTimeButton.setText(String.valueOf(mHomework.getmDueDate().get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(mHomework.getmDueDate().get(Calendar.MINUTE)));
            mRemindDateDateButton.setText(String.valueOf(mHomework.getmRemindDate().get(Calendar.DATE)) + "/" + String.valueOf(mHomework.getmRemindDate().get(Calendar.MONTH))+"/"+String.valueOf(mHomework.getmRemindDate().get(Calendar.YEAR)));
            mRemindDateTimeButton.setText(String.valueOf(mHomework.getmRemindDate().get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(mHomework.getmRemindDate().get(Calendar.MINUTE)));
            mNotesEditText.setText(mHomework.getmNotes());
        }

        final DatePickerDialog dueDateDatePickerDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                HomeworkContent.HOMEWORKS.get(Integer.valueOf(getArguments().getString(ARG_ITEM_ID))).getmDueDate().set(year,monthOfYear,dayOfMonth);
            }
        },mHomework.getmDueDate().get(Calendar.YEAR),mHomework.getmDueDate().get(Calendar.MONTH),mHomework.getmDueDate().get(Calendar.DAY_OF_MONTH));

        mDueDateDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dueDateDatePickerDialog.show();
            }
        });


        return rootView;
    }


}
