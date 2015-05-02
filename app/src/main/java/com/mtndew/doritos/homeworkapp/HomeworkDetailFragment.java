package com.mtndew.doritos.homeworkapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.ToggleButton;


import java.text.SimpleDateFormat;
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
    public static final String IS_TWOPANE = "is two pane";

    private HomeworkContent.Homework mHomework;

    private ToggleButton mDoneButton;
    private Button mSaveButton;
    private EditText mHomeworkNameEditText;
    private EditText mSubjectEditText;
    private Button mDueDateDateButton;
    private Button mDueDateTimeButton;
    private Button mRemindDateDateButton;
    private Button mRemindDateTimeButton;
    private RadioGroup mPriorityRadioGroup;
    private EditText mNotesEditText;

    private GregorianCalendar mTempDueDate;
    private GregorianCalendar mTempRemindDate;

    private HomeworkListFragment mHLF;

    private Boolean mTwoPane;
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

        if (getArguments().containsKey(IS_TWOPANE)) {
            mTwoPane = getArguments().getBoolean(IS_TWOPANE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homework_detail, container, false);


        if (mHomework != null) {
            mDoneButton = (ToggleButton)rootView.findViewById(R.id.done_button);
            mSaveButton = (Button)rootView.findViewById(R.id.save_button);
            mHomeworkNameEditText = (EditText)rootView.findViewById(R.id.name_edittext);
            mSubjectEditText = (EditText)rootView.findViewById(R.id.subject_edittext);
            mDueDateDateButton = (Button)rootView.findViewById(R.id.due_date_date_button);
            mDueDateTimeButton = (Button)rootView.findViewById(R.id.due_date_time_button);
            mRemindDateDateButton = (Button)rootView.findViewById(R.id.reminder_date_date_button);
            mRemindDateTimeButton = (Button)rootView.findViewById(R.id.reminder_date_time_button);
            mPriorityRadioGroup = (RadioGroup)rootView.findViewById(R.id.priority_radiogroup);
            mNotesEditText = (EditText)rootView.findViewById(R.id.notes_edittext);

            mTempDueDate = new GregorianCalendar(mHomework.getmDueDate().get(Calendar.YEAR),
                                                 mHomework.getmDueDate().get(Calendar.MONTH),
                                                 mHomework.getmDueDate().get(Calendar.DAY_OF_MONTH),
                                                 mHomework.getmDueDate().get(Calendar.HOUR_OF_DAY),
                                                 mHomework.getmDueDate().get(Calendar.MINUTE));

            mTempRemindDate = new GregorianCalendar(mHomework.getmRemindDate().get(Calendar.YEAR),
                                                    mHomework.getmRemindDate().get(Calendar.MONTH),
                                                    mHomework.getmRemindDate().get(Calendar.DAY_OF_MONTH),
                                                    mHomework.getmRemindDate().get(Calendar.HOUR_OF_DAY),
                                                    mHomework.getmRemindDate().get(Calendar.MINUTE));

            //Display all the fields for the homework
            mDoneButton.setChecked(mHomework.getmDone());
            mHomeworkNameEditText.setText(mHomework.getmName());
            mSubjectEditText.setText(mHomework.getmSubject());
            mDueDateDateButton.setText(formatDate(mHomework.getmDueDate()));
            mDueDateTimeButton.setText(formatTime(mHomework.getmDueDate()));
            mRemindDateDateButton.setText(formatDate(mHomework.getmRemindDate()));
            mRemindDateTimeButton.setText(formatTime(mHomework.getmRemindDate()));
            if (mHomework.getmPriority() == 2) {
                mPriorityRadioGroup.check(R.id.med_priority_button);
            } else if (mHomework.getmPriority() == 3) {
                mPriorityRadioGroup.check(R.id.high_priority_button);
            }
            mNotesEditText.setText(mHomework.getmNotes());
        }

        //Instantiate the date/time pickers
        //dueDateDatePicker
        final DatePickerDialog dueDateDatePickerDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mTempDueDate.set(year, monthOfYear, dayOfMonth);
                mDueDateDateButton.setText(formatDate(mTempDueDate));
            }
        },mTempDueDate.get(Calendar.YEAR),mTempDueDate.get(Calendar.MONTH),mTempDueDate.get(Calendar.DAY_OF_MONTH));

        mDueDateDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dueDateDatePickerDialog.show();
            }
        });

        //dueDateTimePicker
        final TimePickerDialog dueDateTimePickerDialog = new TimePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mTempDueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mTempDueDate.set(Calendar.MINUTE, minute);
                mDueDateTimeButton.setText(formatTime(mTempDueDate));
            }
        },mTempDueDate.get(Calendar.HOUR_OF_DAY),mTempDueDate.get(Calendar.MINUTE),false);

        mDueDateTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dueDateTimePickerDialog.show();
            }
        });

        //remindDateDatePicker
        final DatePickerDialog remindDateDatePickerDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mTempRemindDate.set(year, monthOfYear, dayOfMonth);
                mRemindDateDateButton.setText(formatDate(mTempRemindDate));
            }
        },mTempRemindDate.get(Calendar.YEAR),mTempRemindDate.get(Calendar.MONTH),mTempRemindDate.get(Calendar.DAY_OF_MONTH));

        mRemindDateDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                remindDateDatePickerDialog.show();
            }
        });

        //remindDateTimePicker
        final TimePickerDialog remindDateTimePickerDialog = new TimePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mTempRemindDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mTempRemindDate.set(Calendar.MINUTE, minute);
                mRemindDateTimeButton.setText(formatTime(mTempRemindDate));
            }
        },mTempRemindDate.get(Calendar.HOUR_OF_DAY),mTempRemindDate.get(Calendar.MINUTE),false);

        mRemindDateTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                remindDateTimePickerDialog.show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveHomework();
            }
        });

        return rootView;
    }

    public static String formatDate(GregorianCalendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setCalendar(calendar);
        String formattedDate = simpleDateFormat.getDateInstance().format(calendar.getTime());
        return formattedDate;
    }

    public String formatTime(GregorianCalendar calendar) {
        DateFormat dateFormat = new DateFormat();
        String formattedTime = dateFormat.getTimeFormat(getActivity()).format(calendar.getTime());
        return formattedTime;
    }

    public void saveHomework() {
        mHomework.setmName(mHomeworkNameEditText.getText().toString());
        mHomework.setmSubject(mSubjectEditText.getText().toString());
        mHomework.setmDone(mDoneButton.isChecked());
        mHomework.setmDueDate(mTempDueDate);
        mHomework.setmRemindDate(mTempRemindDate);
        mHomework.setmNotes(mNotesEditText.getText().toString());

        if (mPriorityRadioGroup.getCheckedRadioButtonId() == R.id.low_priority_button) {
            mHomework.setmPriority(1);
        } else if (mPriorityRadioGroup.getCheckedRadioButtonId() == R.id.med_priority_button) {
            mHomework.setmPriority(2);
        } else {
            mHomework.setmPriority(3);
        }

        //thanks Navneeth, you are a life saver
        AlarmManager alarm = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,alarmIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarm.set(AlarmManager.RTC_WAKEUP,mHomework.getmRemindDate().getTimeInMillis(),pendingIntent);

        if(mTwoPane == null) {
            HomeworkDetailActivity mHDA = (HomeworkDetailActivity) getActivity();
            mHDA.navigateUp();
        } else if (mTwoPane) {
            HomeworkListActivity mHLA = (HomeworkListActivity) getActivity();
            mHLA.updateAdapter();
        }
    }

}
