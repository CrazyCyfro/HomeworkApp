package com.mtndew.doritos.homeworkapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
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

    public static final String NOTIF_MESSAGE = "notification message";
    public static final String NOTIF_ID = "notification id";
    public static final String HOMEWORK_ID = "homework id";


    private HomeworkContent.Homework mHomework;

    private ToggleButton mDoneButton;
    private Button mDeleteButton;
    private Button mExportButton;
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

    private AlertDialog mAlertDialog;

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
            mDeleteButton = (Button)rootView.findViewById(R.id.delete_button);
            mDoneButton = (ToggleButton)rootView.findViewById(R.id.done_button);
            mExportButton = (Button)rootView.findViewById(R.id.export_button);
            mSaveButton = (Button)rootView.findViewById(R.id.save_button);
            mHomeworkNameEditText = (EditText)rootView.findViewById(R.id.name_edittext);
            mSubjectEditText = (EditText)rootView.findViewById(R.id.subject_edittext);
            mDueDateDateButton = (Button)rootView.findViewById(R.id.due_date_date_button);
            mDueDateTimeButton = (Button)rootView.findViewById(R.id.due_date_time_button);
            mRemindDateDateButton = (Button)rootView.findViewById(R.id.reminder_date_date_button);
            mRemindDateTimeButton = (Button)rootView.findViewById(R.id.reminder_date_time_button);
            mPriorityRadioGroup = (RadioGroup)rootView.findViewById(R.id.priority_radiogroup);
            mNotesEditText = (EditText)rootView.findViewById(R.id.notes_edittext);

            //temporary calendars to store date selected by date/time pickers
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


        //exports homework as text
        mExportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent implicitIntent = new Intent(Intent.ACTION_SEND);
                implicitIntent.setType("text/plain");
                implicitIntent.putExtra(Intent.EXTRA_TEXT, formatHomework(mHomework));
                startActivity(Intent.createChooser(implicitIntent,getText(R.string.export_to)));
            }
        });

        //shows an alertdialog if the user wishes to delete homework
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAlertDialog = deleteHomeworkDialog();
                mAlertDialog.show();
            }
        });


        return rootView;
    }

    //formats date/time to be shown in the date/time buttons
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

    //return homework as text
    public String formatHomework(HomeworkContent.Homework homework) {
        return  homework.getmName()
                +"\nSubject: "
                +homework.getmSubject()
                +"\nDue date: "
                +formatDate(homework.getmDueDate())+" "
                +formatTime(homework.getmDueDate())
                +"\nReminder: "
                +formatDate(homework.getmRemindDate())+" "
                +formatTime(homework.getmRemindDate())
                +"\nNotes: "
                +homework.getmNotes();
    }

    public void saveHomework() {
        mHomework.setmName(mHomeworkNameEditText.getText().toString());
        mHomework.setmSubject(mSubjectEditText.getText().toString());
        mHomework.setmDone(mDoneButton.isChecked());
        mHomework.setmDueDate(mTempDueDate);
        mHomework.setmRemindDate(mTempRemindDate);
        mHomework.setmNotes(mNotesEditText.getText().toString());

        if (mHomework.getmDone()) {
            //if homework is done,
            //priority is set as 0 (although user still sees it as 1) for sorting and colour code purposes
            mHomework.setmPriority(0);
        } else if (mPriorityRadioGroup.getCheckedRadioButtonId() == R.id.low_priority_button) {
            mHomework.setmPriority(1);
        } else if (mPriorityRadioGroup.getCheckedRadioButtonId() == R.id.med_priority_button) {
            mHomework.setmPriority(2);
        } else {
            mHomework.setmPriority(3);
        }

        if (!mHomework.getmDone()) {
            remind();
        }

        updateHomeworkList();
    }

    private AlertDialog deleteHomeworkDialog() {
        return new AlertDialog.Builder(getActivity())
                .setTitle(getText(R.string.delete)+" "+mHomework.getmName())
                .setMessage(getText(R.string.confirm_delete))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HomeworkContent.HOMEWORKS.remove(mHomework);
                                if (mTwoPane != null) {
                                    getFragmentManager().beginTransaction()
                                            .remove(getFragmentManager()
                                                    .findFragmentById(R.id.homework_detail_container)).commit();
                                }
                                NotificationManager mNotificationManager =
                                        (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                mNotificationManager.cancel(Integer.valueOf(mHomework.getmId())*10);
                                mNotificationManager.cancel(Integer.valueOf(mHomework.getmId())*100);
                                updateHomeworkList();
                            }
                        })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();
    }

    //updates the adapter of the list of homeworks
    public void updateHomeworkList() {
        //getActivity() is different depending on whether it is twoPane or not,
        //hence, different functions are called, but both aim to just call notifyDataSetChanged
        if(mTwoPane == null) {
            HomeworkDetailActivity mHDA = (HomeworkDetailActivity) getActivity();
            mHDA.navigateUp();
        } else if (mTwoPane) {
            HomeworkListActivity mHLA = (HomeworkListActivity) getActivity();
            mHLA.updateAdapter();
        }
    }

    //sends the notification for both reminder and due date notifs
    public void remind() {
        //thanks Navneeth, you are a life saver
        AlarmManager dueDateAlarm = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        AlarmManager remindDateAlarm = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent dueDateIntent = new Intent(getActivity(), AlarmReceiver.class);
        dueDateIntent.putExtra(NOTIF_MESSAGE, mHomework.getmName()+" is due!");
        dueDateIntent.putExtra(HOMEWORK_ID, mHomework.getmId());
        dueDateIntent.putExtra(NOTIF_ID, Integer.valueOf(mHomework.getmId())*10);

        Intent remindDateIntent = new Intent(getActivity(), AlarmReceiver.class);
        remindDateIntent.putExtra(NOTIF_MESSAGE, mHomework.getmName()+" reminder!");
        remindDateIntent.putExtra(HOMEWORK_ID, mHomework.getmId());
        remindDateIntent.putExtra(NOTIF_ID, Integer.valueOf(mHomework.getmId())*100);

        PendingIntent dueDatePendingIntent = PendingIntent.getBroadcast(getActivity(),Integer.valueOf(mHomework.getmId())*10,dueDateIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        dueDateAlarm.set(AlarmManager.RTC_WAKEUP,mHomework.getmDueDate().getTimeInMillis(),dueDatePendingIntent);

        PendingIntent remindDatePendingIntent = PendingIntent.getBroadcast(getActivity(),Integer.valueOf(mHomework.getmId())*100,remindDateIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remindDateAlarm.set(AlarmManager.RTC_WAKEUP,mHomework.getmRemindDate().getTimeInMillis(),remindDatePendingIntent);

    }

}
