package com.mtndew.doritos.homeworkapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * An activity representing a list of Homeworks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link HomeworkDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HomeworkListFragment} and the item details
 * (if present) is a {@link HomeworkDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link HomeworkListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class HomeworkListActivity extends FragmentActivity
        implements HomeworkListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private Button mAddHomeworkButton;
    private Spinner mSortSpinner;

    private HomeworkListFragment mHLF;

    //check if any data has been saved before
    private Boolean savedBefore = false;

    public static final int HOMEWORK_REQUEST = 1;

    public static final String SAVED_HOMEWORKS = "saved homeworks";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_list);

        //finds homeworklistfragment
        mHLF = (HomeworkListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.homework_list);

        try {
            FileInputStream fis = openFileInput(SAVED_HOMEWORKS);
            ObjectInputStream is = new ObjectInputStream(fis);
            //savedBefore will be false if opening for the first time,
            //true if saves have been added before
            savedBefore = is.readBoolean();


            //check if there have been any saves beforehand (
            if (!savedBefore) {
                //add data if it's the first time opening the app
                HomeworkContent.addItem(new HomeworkContent.Homework("Homework 3", "CEP", false, new GregorianCalendar(), new GregorianCalendar(), "", 1, HomeworkContent.currentId.toString()));
                HomeworkContent.addItem(new HomeworkContent.Homework("Topkek", "Memes", false, new GregorianCalendar(), new GregorianCalendar(), "", 1, HomeworkContent.currentId.toString()));
                HomeworkContent.addItem(new HomeworkContent.Homework("Ganja","Dank",false,new GregorianCalendar(), new GregorianCalendar(), "", 1, HomeworkContent.currentId.toString()));
            } else {
                //load homeworks and add them to homeworkcontent
                ArrayList<HomeworkContent.Homework> savedHomeworks = (ArrayList< HomeworkContent.Homework>)is.readObject();
                if (HomeworkContent.HOMEWORKS.size() == 0) {

                    for (HomeworkContent.Homework tempHomework : savedHomeworks) {
                        HomeworkContent.addItem(tempHomework);
                    }
                }
            }

            is.close();
            updateAdapter();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (StreamCorruptedException sc) {
            sc.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

        if (findViewById(R.id.homework_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            mHLF.setActivateOnItemClick(true);
        }

        Intent intent = getIntent();
        if (intent.getStringExtra(HomeworkDetailFragment.HOMEWORK_ID) != null) {
            String intentId = intent.getStringExtra(HomeworkDetailFragment.HOMEWORK_ID);
            openDetail(intentId);
        }

        mAddHomeworkButton = (Button)this.findViewById(R.id.add_homework_button);
        mSortSpinner = (Spinner)this.findViewById(R.id.sort_spinner);

        mAddHomeworkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //adds a new homework item directly to the list for user to edit
                HomeworkContent.addItem(new HomeworkContent.Homework("New Homework","",false,new GregorianCalendar(),new GregorianCalendar(),"",1,HomeworkContent.currentId.toString()));

                updateAdapter();
            }
        });

        //sets up the sorting spinner with items from categories.xml
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(spinnerAdapter);

        //sorts accordingly when item is selected
        mSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Due date")) {
                    Collections.sort(HomeworkContent.HOMEWORKS, new DateComparator());

                } else if (parent.getItemAtPosition(position).equals("Name")) {
                    Collections.sort(HomeworkContent.HOMEWORKS, new Comparator<HomeworkContent.Homework>() {
                        public int compare(HomeworkContent.Homework hw1, HomeworkContent.Homework hw2) {
                            return hw1.getmName().compareToIgnoreCase(hw2.getmName());
                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("Priority")) {
                    Collections.sort(HomeworkContent.HOMEWORKS, new Comparator<HomeworkContent.Homework>() {
                        public int compare(HomeworkContent.Homework hw1, HomeworkContent.Homework hw2) {
                            return hw2.getmPriority().compareTo(hw1.getmPriority());
                        }
                    });
                }
                updateAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //only called when on a phone, after save button is clicked, update adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HOMEWORK_REQUEST) {
            if (resultCode == RESULT_OK) {
                updateAdapter();
            }
        }
    }

    /**
     * Callback method from {@link HomeworkListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        openDetail(id);
    }

    public void openDetail (String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(HomeworkDetailFragment.ARG_ITEM_ID, id);
            arguments.putBoolean(HomeworkDetailFragment.IS_TWOPANE, true);
            HomeworkDetailFragment fragment = new HomeworkDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homework_detail_container, fragment)
                    .commit();

        } else {
            //start activityForResult so we can do something after detailActivity closes
            Intent detailIntent = new Intent(this, HomeworkDetailActivity.class);
            detailIntent.putExtra(HomeworkDetailFragment.ARG_ITEM_ID, id);
            startActivityForResult(detailIntent, HOMEWORK_REQUEST);
        }
    }


    //saved data before app closes
    @Override
    protected void onStop() {
        super.onStop();

        try {
            FileOutputStream fos = openFileOutput(SAVED_HOMEWORKS, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //save homeworks and make savedBefore true
            oos.writeBoolean(true);
            oos.writeObject(HomeworkContent.HOMEWORKS);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    //calls notifyDataSetChanged for HomeworkListFragment's adapter
    public void updateAdapter() {
        mHLF.getAdapter().notifyDataSetChanged();
    }
}
