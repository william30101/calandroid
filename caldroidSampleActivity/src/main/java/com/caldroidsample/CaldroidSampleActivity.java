package com.caldroidsample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class CaldroidSampleActivity extends AppCompatActivity {
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private static int ADD_CAL = 100;
    private static int MODIFY_CAL = 101;
    List<data> allDataList = new ArrayList<>();

    private void setCustomResourceForDates() {

        //抓db資料
        DataDAO dao = new DataDAODBImpl(CaldroidSampleActivity.this);
        allDataList = dao.getAllData();

        for(data d : allDataList)
        {
            Log.d("test data",d.date + "," + d.title + "," + d.content);

            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            ParsePosition pos = new ParsePosition(0);
            Date eventDate = sDateFormat.parse(d.date, pos);
            fillColorInCal(eventDate);
        }


       // Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
//        cal.add(Calendar.DATE, -7);
//        Date blueDate = cal.getTime();
//
//        // Max date is next 7 days
//        cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 7);
//        Date greenDate = cal.getTime();

//        if (caldroidFragment != null) {
//            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
//            ColorDrawable green = new ColorDrawable(Color.GREEN);
////            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
////            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
////            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
////            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
//            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
//            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            //args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
          //  args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();

                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String clickdate = sDateFormat.format(date);
                Log.d("test",clickdate);
                boolean foundFlag = false;
                for (int i=0;i <allDataList.size() ; i++){
                    if ( allDataList.get(i).date.contentEquals(clickdate)){
                        foundFlag = true;
                    }
                }

                if (foundFlag){
                    //Exist date object , jump Edit Page
                    Intent it = new Intent(CaldroidSampleActivity.this,DetailActivity.class);

                    it.putExtra("date2",clickdate);
                    //startActivity(it);
                    startActivityForResult(it, MODIFY_CAL);
                }else {
                    //New date Record
                    Intent it = new Intent(CaldroidSampleActivity.this, AddActivity.class);

                    it.putExtra("date", clickdate);
                    startActivityForResult(it, ADD_CAL);
                }

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();

//
//                Calendar cal = Calendar.getInstance();
//                ColorDrawable white = new ColorDrawable(Color.WHITE);
//
//                caldroidFragment.setBackgroundDrawableForDate(white, date);
//                caldroidFragment.refreshView();
//
//
//                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                String clickdate2 = sDateFormat.format(date);
//                Intent it = new Intent(CaldroidSampleActivity.this,DetailActivity.class);
//
//                it.putExtra("date2",clickdate2);
//                startActivity(it);
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);


    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

    private void fillColorInCal(Date date){

        ColorDrawable green = new ColorDrawable(Color.GREEN);

        caldroidFragment.setBackgroundDrawableForDate(green, date);
        caldroidFragment.refreshView();
    }

    private void removeColorInCal(Date date){

        ColorDrawable white = new ColorDrawable(Color.WHITE);

        caldroidFragment.setBackgroundDrawableForDate(white, date);
        caldroidFragment.refreshView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CAL) {
            if(resultCode == RESULT_OK){
                String retDate=data.getStringExtra("date");
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                ParsePosition pos = new ParsePosition(0);
                Date stringDate = sDateFormat.parse(retDate, pos);

                //Add to list
                data newRecord = new data(retDate);
                allDataList.add(newRecord);

                fillColorInCal(stringDate);
            }else{
                // Do Nothing now.
            }

        }else if (requestCode == MODIFY_CAL){
            if(resultCode == RESULT_OK){



                String retDate=data.getStringExtra("del_date");
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                if (retDate != null){
                    ParsePosition pos = new ParsePosition(0);
                    Date stringDate = sDateFormat.parse(retDate, pos);


                    // Remove from list
                    for (int i=0; i< allDataList.size(); i++){
                        if (allDataList.get(i).date.contentEquals(retDate)){
                            allDataList.remove(i);
                        }
                    }

                    removeColorInCal(stringDate);
                }


                //fillColorInCal(stringDate);
            }else{
                // Do Nothing now.
            }
        }
    }
}
