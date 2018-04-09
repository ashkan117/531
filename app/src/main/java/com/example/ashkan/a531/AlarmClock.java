package com.example.ashkan.a531;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ashkan.a531.Data.Alarm;
import com.example.ashkan.a531.Model.Time;
import com.example.ashkan.a531.databinding.AlarmClockBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ashkan on 1/20/2018.
 */

public class AlarmClock extends ConstraintLayout {

    private static final String ALARM_CLOCK_TIME_KEY = "alarmClockTimeSet";
    public static final String SWITCH_KEY = "switchKey";
    public static final String MONDAY_TOGGLE_BUTTON_KEY = "mondayToggleBottonSwitchKey";
    public static final String TUESDAY_TOGGLE_BUTTON_KEY = "tuesdayToggleBottonSwitchKey";
    public static final String WEDNESDAY_TOGGLE_BUTTON_KEY = "wednesdayToggleBottonSwitchKey";
    public static final String THURSDAY_TOGGLE_BUTTON_KEY = "thursdayToggleBottonSwitchKey";
    public static final String FRIDAY_TOGGLE_BUTTON_KEY = "fridayToggleBottonSwitchKey";
    public static final String SATURDAY_TOGGLE_BUTTON_KEY = "saturdayToggleBottonSwitchKey";
    public static final String SUNDAY_TOGGLE_BUTTON_KEY = "sundayToggleBottonSwitchKey";
    private ArrayList<Boolean> isSelected;
    private TimePicker mTimePicker;
    private Switch mSwitch;
    private Time mTime;
    private long mTimeSelectedInMillisecondsEpoch;
    private Context mContext;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;
    public static final String HOUR_KEY = "hourKey";
    public static final String MINUTE_KEY = "minuteKey";
    private LinearLayout mDismissLayout;
    private Button mDismissButton;
    private AlarmActivityListener mActivityListener;
    private Date mDate;
    private AlarmClockBinding mAlarmClockBinding;

    public void initClocks(Alarm currentHolder) {
            int hour = currentHolder.getHour();
            int minute = currentHolder.getMinute();
            boolean onOff = currentHolder.isOnOff();
//            boolean mondaySelected = currentHolder.isMondaySelected();
//            boolean tuesdaySelected = currentHolder.isTuesdaySelected();
//            boolean wednesdaySelected = currentHolder.isWednesdaySelected();
//            boolean thursdaySelected = currentHolder.isThursdaySelected();
//            boolean fridaySelected = currentHolder.isFridaySelected();
//            boolean saturdaySelected = currentHolder.isSaturdaySelected();
//            boolean sundaySelected = currentHolder.isSundaySelected();
//

            setTimePicker(new Time(hour,minute));
//            setMondayToggle(mondaySelected);
//            setSwitch(onOff);
//            setTuesdayToggle(tuesdaySelected);
//            setWednesdayToggle(wednesdaySelected);
//            setThursdayToggle(thursdaySelected);
//            setFridayToggle(fridaySelected);
//            setSaturdayToggle(saturdaySelected);
//            setSundayToggle(sundaySelected);

    }

    public Alarm getAlarmClockAsHolder(int position) {
        int hour = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        boolean onOff = mSwitch.isChecked();


        Alarm alarm = new Alarm();
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.setPositionInAdapter(position);
//        alarm.setMondaySelected(mondaySelected);
//        alarm.setTuesdaySelected(tuesdaySelected);
//        alarm.setWednesdaySelected(wednesdaySelected);
//        alarm.setThursdaySelected(thursdaySelected);
//        alarm.setFridaySelected(fridaySelected);
//        alarm.setSaturdaySelected(saturdaySelected);
//        alarm.setSundaySelected(sundaySelected);
        return alarm;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public interface AlarmActivityListener{
        void dismissButtonListener();
    }

    public void setTimePicker(Time time) {
        mTimePicker.setMinute(time.getMinute());
        mTimePicker.setIs24HourView(false);
        int hour = time.getHour();
        mTimePicker.setHour(hour);
    }

    public void setSwitch(Boolean isSelected) {
        mSwitch.setChecked(isSelected);
    }

    public AlarmClock(Context context) {
        super(context);
    }

    public AlarmClock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
        setSwitchListener();
        setTime();
    }


    private void updateDatabase() {
        Alarm holder = new Alarm();
    }

    private void updateAlarm() {
        deactivateCurrentAlarm();
        prepareAlarm(mTime,0);
    }

    private void deactivateCurrentAlarm() {
        Intent intent = new Intent(mContext,NotificationReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
    }

    //sets our pulib time field to the proper time
    private void setTime() {
        int hour = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        Time.AM_PM am_pm;
        if (hour>12){
            am_pm= Time.AM_PM.AM;
        }
        else {
            am_pm= Time.AM_PM.PM;
        }
        mTime = new Time(hour,minute,am_pm);

    }

    private void setSwitchListener() {
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = mSwitch.isChecked();
                if(check){
                    setTime();
                    boolean atLeastOne = false;
                    for(int i=0;i<isSelected.size();i++){
                        if(isSelected.get(i)==true){
                            atLeastOne=true;
                            break;
                        }
                    }
                    if(atLeastOne){
                        prepareAlarm(mTime,1);
                    }
                }
            }
        });
    }




    public ArrayList<Boolean> getIsSelected() {
        return isSelected;
    }

    public TimePicker getTimePicker() {
        return mTimePicker;
    }

    public Switch getSwitch() {
        return mSwitch;
    }

    public Time getTime() {
        return mTime;
    }

    public long getTimeSelectedInMillisecondsEpoch() {
        return mTimeSelectedInMillisecondsEpoch;
    }


    private void init(Context context)
    {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //why this?
        inflater.inflate(R.layout.alarm_clock,this);
    }

    //flag repersents setting a toast or not
    //1 means have a toast, 0 for no
    private void prepareAlarm(Time time, int flag) {

        //TODO: We need to save the state of the alarmclock and set it with the broadcast
        //  this is so we can load it up again later
        //measures time since epoch
        long currentTime = System.currentTimeMillis();

        long[] timeInFutureMilli = getDaysToSetAlarmFor(currentTime,time);

        Intent intent = new Intent(mContext,NotificationReciever.class);

        /*
        * Need to save
        *   switch
        *   buttons selected
        *   time
        *   **/
        intent.putExtra("String","does this make it");
        // intent.putExtra(ALARM_CLOCK_TIME_KEY,mTime);
        intent.putExtra(HOUR_KEY,mTimePicker.getHour());
        intent.putExtra(MINUTE_KEY,mTimePicker.getMinute());
        intent.putExtra(SWITCH_KEY,mSwitch.isChecked());
//        intent.putExtra(MONDAY_TOGGLE_BUTTON_KEY,mMondayToggle.isChecked());
//        intent.putExtra(TUESDAY_TOGGLE_BUTTON_KEY,mTuesdayToggle.isChecked());
//        intent.putExtra(WEDNESDAY_TOGGLE_BUTTON_KEY,mWednesdayToggle.isChecked());
//        intent.putExtra(THURSDAY_TOGGLE_BUTTON_KEY,mThursdayToggle.isChecked());
//        intent.putExtra(FRIDAY_TOGGLE_BUTTON_KEY,mFridayToggle.isChecked());
//        intent.putExtra(SATURDAY_TOGGLE_BUTTON_KEY,mSaturdayToggle.isChecked());
//        intent.putExtra(SUNDAY_TOGGLE_BUTTON_KEY,mSundayToggle.isChecked());


//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //The method getIntent() returns the FIRST intent than launch activity.
        Bundle extras = intent.getExtras();
        boolean switchOn = extras.getBoolean(SWITCH_KEY);
        boolean mondaySelected =  extras.getBoolean(MONDAY_TOGGLE_BUTTON_KEY);
        boolean tuesdaySelected =  extras.getBoolean(TUESDAY_TOGGLE_BUTTON_KEY);
        boolean wednesdaySelected =  extras.getBoolean(WEDNESDAY_TOGGLE_BUTTON_KEY);
        boolean thursdaySelected =  extras.getBoolean(THURSDAY_TOGGLE_BUTTON_KEY);
        boolean fridaySelected =  extras.getBoolean(FRIDAY_TOGGLE_BUTTON_KEY);
        boolean saturdaySelected =  extras.getBoolean(SATURDAY_TOGGLE_BUTTON_KEY);
        boolean sundaySelected =  extras.getBoolean(SUNDAY_TOGGLE_BUTTON_KEY);

        //changed to Flag.Cancel.Current

        //must have multiple pendingIntents
        //if not the new alarm will just override the old one
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        long timeEx = System.currentTimeMillis()+10*1000;

        for(int i=0;i<timeInFutureMilli.length;i++){
            /*
            900000ms    1s          1min
                        1000ms      60s
             */

            mPendingIntent = PendingIntent.getBroadcast(mContext,i,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            long remainder;
            long tracker;
            long inDays = timeInFutureMilli[i]/1000/60/60/24;
            tracker = timeInFutureMilli[i]-inDays*Time.ONE_DAY_IN_MILLI;
            //remainder = timeInFutureMilli[i]%(1000/60/60/24);
            long inHours = tracker/1000/60/60;
            tracker = timeInFutureMilli[i]-inDays*Time.ONE_DAY_IN_MILLI - inHours*Time.ONE_HOUR_IN_MILLI;
            //remainder = tracker%(1000/60/60);
            long inMinutes = tracker/1000/60;
            tracker = timeInFutureMilli[i]-inDays*Time.ONE_DAY_IN_MILLI
                    - inHours*Time.ONE_HOUR_IN_MILLI
                    - inMinutes * Time.ONE_MINUTE_IN_MILLI;
            //remainder = remainder%(1000/60);
            long inSeconds = tracker/1000;



            if(flag==1){
                String display = "";
                if(inDays>0){
                    display+=String.valueOf(inDays)+" days ";
                }
                if(inHours>0){
                    display+=String.valueOf(inHours)+" hours ";
                }
                if(inMinutes>0){
                    display+=String.valueOf(inMinutes)+" minutes ";
                }
                if(inSeconds>0){
                    display+=String.valueOf(inSeconds)+" seconds ";
                }
                Toast.makeText(getContext(),"You will receive an alarm in "+display,Toast.LENGTH_SHORT).show();
            }
            else{
                String display = "";
                if(inDays>0){
                    display+=String.valueOf(inDays)+" days ";
                }
                if(inHours>0){
                    display+=String.valueOf(inHours)+" hours ";
                }
                if(inMinutes>0){
                    display+=String.valueOf(inMinutes)+" minutes ";
                }
                if(inSeconds>0){
                    display+=String.valueOf(inSeconds)+" seconds ";
                }
                Toast.makeText(getContext(),"You will receive an alarm in "+display,Toast.LENGTH_SHORT).show();
            }
            long now = Calendar.getInstance().getTimeInMillis();
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,now+timeInFutureMilli[i],1000*60*60*24*7, mPendingIntent);
        }

    }

    private long[] getDaysToSetAlarmFor(long currentTime, Time time) {
        //timeInMilli is the time in ms
        //for exame 8:31 converted to ms
        //we want
        long timeInMilli = time.getTimeInMilli();
        long timeToSetInMilli = time.getTimeInMilli()+currentTime;
        long timeInFuture = timeToSetInMilli-currentTime;
        if(timeInFuture<0){
            //a week away so we must add six days
            timeInFuture+=6*Time.ONE_DAY_IN_MILLI;
        }
//
        ArrayList<Long> ret = new ArrayList<>();
//        Date now = new Date();
        Date now = Calendar.getInstance().getTime();
//        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);


        Calendar calendar = Calendar.getInstance();

        calendar.setTime(now);
        int day = calendar.get(Calendar.DAY_OF_WEEK);



//
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar basisCalander = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int date = calendar.get(Calendar.DATE);

        int hour = mTime.getHour();
        int timeP = mTimePicker.getHour();
        int minute = mTime.getMinute();

        //TODO: The reason why the date was wrong
        //Giving the calander an initial timezone messed things up
        basisCalander.set(Calendar.YEAR,year);
        basisCalander.set(Calendar.DAY_OF_WEEK,dayOfWeek);
        basisCalander.set(Calendar.DATE,date);
        basisCalander.set(Calendar.HOUR_OF_DAY,hour);
        basisCalander.set(Calendar.MINUTE,minute);
        basisCalander.set(Calendar.SECOND,0);
        Date basisDate = basisCalander.getTime();
        long timePickerTimeInMilli = basisCalander.getTimeInMillis();
        timeInFuture = timePickerTimeInMilli - currentTime;
        //round to nearest whole number
        long futureInMin = (long) Math.rint((double) timeInFuture/1000/60);

        if(futureInMin<0){
            //a week away so we must add six days
            timeInFuture+=7*Time.ONE_DAY_IN_MILLI;
        }
        //Goal is to get timepicker in UTC


        //3 cases:
        //  Same day:
        //  A day after (Current day Tues, Alarm for Wed)
        //  A day before (Current day Friday, Alarm for Tues)
        switch (day){
            case Calendar.MONDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(isSelected.get(i)==true){
                        ret.add(timeInFuture+(i*Time.ONE_DAY_IN_MILLI));
                    }
                }
                break;
            case Calendar.TUESDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(isSelected.get(1)==true){
                        ret.add(timeInFuture);
                    }
                    else if (i>1) {
                        if (isSelected.get(i) == true) {
                            ret.add(timeInFuture + ((i - 1) * Time.ONE_DAY_IN_MILLI));
                        }
                    }
                    else if(i==0){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((6)*Time.ONE_DAY_IN_MILLI));
                        }
                    }
                }
                break;
            case Calendar.WEDNESDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(isSelected.get(2)==true){
                        ret.add(timeInFuture);
                    }
                    else if (i>2){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i-2))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                    else if (i<2){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i+5))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                }
                break;
            case Calendar.THURSDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(isSelected.get(i) == true && i == 3){
                        ret.add(timeInFuture);
                    }

                    else if (i>3){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i-3))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                    else if (i<3){
                        // 0 1 2 3
                        // 4  5  6  0
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i+4))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                }
                break;
            case Calendar.FRIDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(isSelected.get(4)==true){
                        ret.add(timeInFuture);
                    }

                    else if (i>4){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i-4))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                    else if (i<4){
                        // 0 1 2 3
                        // 4  5  6  0
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i+3))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                }
                break;
            case Calendar.SATURDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(isSelected.get(5)==true){
                        ret.add(timeInFuture);
                    }

                    else if (i>5){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i-5))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                    else if (i<5){
                        // 0 1 2 3
                        // 4  5  6  0
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i+4))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                }
                break;
            case Calendar.SUNDAY:
                for(int i=0;i<isSelected.size();i++){
                    if(i==6&& isSelected.get(6)==true){
                        ret.add(timeInFuture);
                    }

                    else if (i>6){
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i-6))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                    else if (i<5){
                        // 0 1 2 3
                        // 4  5  6  0
                        if(isSelected.get(i)==true){
                            ret.add(timeInFuture+((i+5))*Time.ONE_DAY_IN_MILLI);
                        }
                    }
                }
                break;
        }

        if(ret.size()==0){
            return null;
        }
        long[] daysToBeSet = new long[ret.size()];
        for(int i=0;i<ret.size();i++){
            //add back the epoch time
            //ret.get gives us 15:32 in milliseconds
            //daysToBeSet[i] = ret.get(i)+currentTime;
            daysToBeSet[i] = ret.get(i);
        }
        return daysToBeSet;
    }


    private void init(Context context, AttributeSet attrs) {
        init(context);
        if(attrs!=null)
        {

        }
    }
    private void init(Context context, AttributeSet attrs, int defSyleLayout)
    {
        init(context,attrs);
    }

    public void makeDismissVisible() {
        //mDismissLayout.setVisibility(VISIBLE);
    }
}
