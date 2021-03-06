package com.example.ashkan.a531.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.AlarmClock;
import com.example.ashkan.a531.Data.Alarm;
import com.example.ashkan.a531.Data.ViewModel.AlarmClockViewModel;
import com.example.ashkan.a531.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.ashkan.a531.AlarmClock.HOUR_KEY;
import static com.example.ashkan.a531.AlarmClock.MINUTE_KEY;

;

public class AlarmClockActivity extends AppCompatActivity implements AlarmClock.AlarmActivityListener{


    private static final String ALARM_CLOCK_TIME_KEY = "alarmClockTimeSet";
    private static final String INTENT_EXTRAS_FROM_SET_CLOCK = "intentFromSetClock";
    private static final String SWITCH_KEY = "switchKey";
    private static final String MONDAY_TOGGLE_BUTTON_KEY = "mondayToggleBottonSwitchKey";
    private static final String TUESDAY_TOGGLE_BUTTON_KEY = "tuesdayToggleBottonSwitchKey";
    private static final String WEDNESDAY_TOGGLE_BUTTON_KEY = "wednesdayToggleBottonSwitchKey";
    private static final String THURSDAY_TOGGLE_BUTTON_KEY = "thursdayToggleBottonSwitchKey";
    private static final String FRIDAY_TOGGLE_BUTTON_KEY = "fridayToggleBottonSwitchKey";
    private static final String SATURDAY_TOGGLE_BUTTON_KEY = "saturdayToggleBottonSwitchKey";
    private static final String SUNDAY_TOGGLE_BUTTON_KEY = "sundayToggleBottonSwitchKey";
    private AlarmClock mAlarmClock;
    private FloatingActionButton mFab;
    private ArrayList<AlarmClock> mAlarmClockList;
    private List<Alarm> mAlarms;
    private ViewGroup mRootView;
    private RecyclerView mRecyclerView;
    private AlarmClockRecyclerViewAdapter mRecyclerViewAdapter;
    private DividerItemDecoration mDividerItemDecoration;
    private LinearLayoutManager mLayoutManager;
    private AlarmClockViewModel mAlarmClockViewModel;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        mAlarmClockViewModel = ViewModelProviders.of(this).get(AlarmClockViewModel.class);
        mAlarms = mAlarmClockViewModel.getAllAlarms().getValue();
        mAlarmClockViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> alarms) {
                mAlarms = alarms;
            }
        });

        initViews();
        mAlarmClockList = new ArrayList<AlarmClock>();


        if(mAlarms ==null){
            mAlarms = new ArrayList<Alarm>();
        }

        mRecyclerViewAdapter = new AlarmClockRecyclerViewAdapter(this, mAlarms);

        if(mAlarms !=null){
            //initClocks();
           // mRecyclerViewAdapter = new AlarmClockRecyclerViewAdapter(this,mAlarms);
        }


        initRecyclerView();

        //The method getIntent() returns the FIRST intent than launch activity.
        // we need to set flags then override onNewIntent to update the intents
        int code=0;
        Bundle possibleExtrasToStartAlarm = getIntent().getExtras();
        if(possibleExtrasToStartAlarm!=null) {
            code = possibleExtrasToStartAlarm.getInt("AlarmCode");
            retrieveExtrasAndRestoreViews(possibleExtrasToStartAlarm);
            findPosition(possibleExtrasToStartAlarm);
            if(code==100){
            }

        }
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.alarm_clock_recycler_view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        //make an instance of the ItemTouchHelper.Callback
        ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int positionInData = mAlarms.get(position).getPositionInDatabase();
                Alarm alarm = mAlarms.get(positionInData);
                mAlarmClockViewModel.removeClockAtPosition(alarm);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void findPosition(Bundle extras) {
        int hour = extras.getInt(HOUR_KEY);
        int minute = extras.getInt(MINUTE_KEY);
        boolean switchOn = extras.getBoolean(SWITCH_KEY);
        boolean mondaySelected =  extras.getBoolean(MONDAY_TOGGLE_BUTTON_KEY);
        boolean tuesdaySelected =  extras.getBoolean(TUESDAY_TOGGLE_BUTTON_KEY);
        boolean wednesdaySelected =  extras.getBoolean(WEDNESDAY_TOGGLE_BUTTON_KEY);
        boolean thursdaySelected =  extras.getBoolean(THURSDAY_TOGGLE_BUTTON_KEY);
        boolean fridaySelected =  extras.getBoolean(FRIDAY_TOGGLE_BUTTON_KEY);
        boolean saturdaySelected =  extras.getBoolean(SATURDAY_TOGGLE_BUTTON_KEY);
        boolean sundaySelected =  extras.getBoolean(SUNDAY_TOGGLE_BUTTON_KEY);

        AlarmClock currentAlarm;
        boolean check = false;
        for(int i=0;i<mAlarmClockList.size();i++)
        {
            currentAlarm = mAlarmClockList.get(i);
            int hourAlarm = currentAlarm.getTimePicker().getHour();
            int minuteAlarm = currentAlarm.getTimePicker().getMinute();
            boolean onOff = currentAlarm.getSwitch().isChecked();
            boolean mondaySelectedAlarm = currentAlarm.getMondayToggle().isChecked();
            boolean tuesdaySelectedAlarm = currentAlarm.getTuesdayToggle().isChecked();
            boolean wednesdaySelectedAlarm = currentAlarm.getWednesdayToggle().isChecked();
            boolean thursdaySelectedAlarm = currentAlarm.getThursdayToggle().isChecked();
            boolean fridaySelectedAlarm = currentAlarm.getFridayToggle().isChecked();
            boolean saturdaySelectedAlarm = currentAlarm.getSaturdayToggle().isChecked();
            boolean sundaySelectedAlarm = currentAlarm.getSundayToggle().isChecked();

            if(hour==hourAlarm
                    &&minute==minuteAlarm
                    &&onOff==true
                    &&mondaySelected==mondaySelectedAlarm
                    &&tuesdaySelected==tuesdaySelectedAlarm
                    &&wednesdaySelected==wednesdaySelectedAlarm
                    &&thursdaySelected==thursdaySelectedAlarm
                    &&fridaySelected==fridaySelectedAlarm
                    &&saturdaySelected==saturdaySelectedAlarm
                    &&sundaySelected==sundaySelectedAlarm){
                //currentAlarm.makeDismissVisible();

            }

        }
    }


    private void retrieveExtrasAndRestoreViews(Bundle possibleExtrasToStartAlarm) {
        //Bundle extras = possibleExtrasToStartAlarm.getBundle(INTENT_EXTRAS_FROM_SET_CLOCK);
        Bundle extras = possibleExtrasToStartAlarm;
        boolean switchOn = extras.getBoolean(SWITCH_KEY);
        boolean mondaySelected =  extras.getBoolean(MONDAY_TOGGLE_BUTTON_KEY);
        boolean tuesdaySelected =  extras.getBoolean(TUESDAY_TOGGLE_BUTTON_KEY);
        boolean wednesdaySelected =  extras.getBoolean(WEDNESDAY_TOGGLE_BUTTON_KEY);
        boolean thursdaySelected =  extras.getBoolean(THURSDAY_TOGGLE_BUTTON_KEY);
        boolean fridaySelected =  extras.getBoolean(FRIDAY_TOGGLE_BUTTON_KEY);
        boolean saturdaySelected =  extras.getBoolean(SATURDAY_TOGGLE_BUTTON_KEY);
        boolean sundaySelected =  extras.getBoolean(SUNDAY_TOGGLE_BUTTON_KEY);

        mAlarmClock.setMondayToggle(mondaySelected);
        mAlarmClock.setTuesdayToggle(tuesdaySelected);
        mAlarmClock.setWednesdayToggle(wednesdaySelected);
        mAlarmClock.setThursdayToggle(thursdaySelected);
        mAlarmClock.setFridayToggle(fridaySelected);
        mAlarmClock.setSaturdayToggle(saturdaySelected);
        mAlarmClock.setSundayToggle(sundaySelected);
        mAlarmClock.setSwitch(switchOn);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAlarmClocks();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveAlarmClocks();
    }

    private void saveAlarmClocks() {
        Alarm holder = new Alarm();
        AlarmClock currentAlarm;
        int updatedRows;
        if(mAlarms.size()==0){
            return;
        }
        for(int i = 0; i< mAlarms.size(); i++){
            AlarmClockRecyclerViewAdapter.AlarmClockViewHolder viewHolder =
                    (AlarmClockRecyclerViewAdapter.AlarmClockViewHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
            holder = viewHolder.mAlarmClock.getAlarmClockAsHolder(i);

            //updatedRows = DataManager.maintainClocks(holder,dbHelper);

        }
    }

//TODO: adb exec-out "run-as com.example.ashkan.a531 cat databases/oneRepMax.db" > oneRepMax.db
    private void initViews() {
        LayoutInflater inflater = getLayoutInflater();
        mRootView = (ViewGroup) findViewById(R.id.alarm_clock_root_view);
        //TODO:Add swipability to the alarms

        CardView clock = (CardView) inflater.inflate(R.layout.alarm_clock, null);
        mFab = (FloatingActionButton) mRootView.findViewById(R.id.alarm_clock_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAlarmClock = new AlarmClock(getApplicationContext());
//                mAlarmClockList.add(mAlarmClock);
//               insertAlarmClock(mAlarmClockList);
//                mRecyclerView.addView(mAlarmClock);
                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                Alarm alarm = new Alarm();
                alarm.setHour(hour);
                alarm.setMinute(minute);

                mAlarmClockViewModel.insertNewAlarmClock(alarm);
                AlarmClock currentClock = (AlarmClock) mRecyclerView.findViewById(R.id.alarm_clock_item);
                currentClock.setDate(now);
            }
        });
    }

    private void insertAlarmClock(ArrayList<AlarmClock> alarmClockList) {
        Alarm holder = new Alarm();
        int position = alarmClockList.size()-1;
        AlarmClock alarmClock = alarmClockList.get(position);
        int hour = alarmClock.getTimePicker().getHour();
        int minute = alarmClock.getTimePicker().getMinute();
        boolean onOff = alarmClock.getSwitch().isSelected();
        boolean mondaySelected = alarmClock.getMondayToggle().isSelected();
        boolean tuesdaySelected = alarmClock.getTuesdayToggle().isSelected();
        boolean wednesdaySelected = alarmClock.getWednesdayToggle().isSelected();
        boolean thursdaySelected = alarmClock.getThursdayToggle().isSelected();
        boolean fridaySelected = alarmClock.getFridayToggle().isSelected();
        boolean saturdaySelected = alarmClock.getSaturdayToggle().isSelected();
        boolean sundaySelected = alarmClock.getSundayToggle().isSelected();

        holder.setPositionInAdapter(position);
        holder.setHour(hour);
        holder.setMinute(minute);
        holder.setOnOff(onOff);
//        holder.setMondaySelected(mondaySelected);
//        holder.setTuesdaySelected(tuesdaySelected);
//        holder.setWednesdaySelected(wednesdaySelected);
//        holder.setThursdaySelected(thursdaySelected);
//        holder.setFridaySelected(fridaySelected);
//        holder.setSaturdaySelected(saturdaySelected);
//        holder.setSundaySelected(sundaySelected);

        //DataManager.insertNewAlarmClock(holder,dbHelper);
    }


    @Override
    public void dismissButtonListener() {
        //mMediaPlayer.stop();
    }

    public class AlarmClockRecyclerViewAdapter extends RecyclerView.Adapter<AlarmClockRecyclerViewAdapter.AlarmClockViewHolder>{

        private ArrayList<Alarm> mAlarmClocks;
        private Context mContext;
        private final LayoutInflater mLayoutInflater;

        public AlarmClockRecyclerViewAdapter(Context context , List<Alarm> alarmClocks){
            mContext = context;
            mAlarms = alarmClocks;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public AlarmClockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup itemView = (ViewGroup) mLayoutInflater.inflate(R.layout.alarm_clock_recycler_view_item,parent,false);
            return new AlarmClockViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AlarmClockViewHolder holder, int position) {
            holder.mAlarmClock.initClocks(mAlarmClocks.get(position));
        }

        @Override
        public int getItemCount() {
            return mAlarmClocks.size();
        }

        public class AlarmClockViewHolder extends RecyclerView.ViewHolder {

            private AlarmClock mAlarmClock;

            public AlarmClockViewHolder(View itemView) {
                super(itemView);
                mAlarmClock = (AlarmClock) itemView.findViewById(R.id.alarm_clock_item);
            }
        }
    }

}
