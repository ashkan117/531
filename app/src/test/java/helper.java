/**
 * Created by Ashkan on 2/5/2018.
 */

public class helper {

//    public static final String BEING_ALARM_KEY = "beginAlarmKey";
//    private static final String ALARM_CLOCK_TIME_KEY = "alarmClockTimeSet";
//    private static final String SWITCH_KEY = "switchKey";
//    private static final String MONDAY_TOGGLE_BUTTON_KEY = "mondayToggleBottonSwitchKey";
//    private static final String TUESDAY_TOGGLE_BUTTON_KEY = "tuesdayToggleBottonSwitchKey";
//    private static final String WEDNESDAY_TOGGLE_BUTTON_KEY = "wednesdayToggleBottonSwitchKey";
//    private static final String THURSDAY_TOGGLE_BUTTON_KEY = "thursdayToggleBottonSwitchKey";
//    private static final String FRIDAY_TOGGLE_BUTTON_KEY = "fridayToggleBottonSwitchKey";
//    private static final String SATURDAY_TOGGLE_BUTTON_KEY = "saturdayToggleBottonSwitchKey";
//    private static final String SUNDAY_TOGGLE_BUTTON_KEY = "sundayToggleBottonSwitchKey";
//    private static final String INTENT_EXTRAS_FROM_SET_CLOCK = "intentFromSetClock";
//    private int mBeginAlarmValue;
//
//    public interface OnRegisterRecieved{
//        void playAlarm();
//    }
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Intent destinationIntent = new Intent(context,AlarmClockActivity.class);
//        //transferIntentToDestination(intent,destinationIntent);
//        Bundle extras = intent.getExtras();
//        mBeginAlarmValue = 100;
//        boolean switchOn = extras.getBoolean(SWITCH_KEY);
//        boolean mondaySelected =  extras.getBoolean(MONDAY_TOGGLE_BUTTON_KEY);
//        boolean tuesdaySelected =  extras.getBoolean(TUESDAY_TOGGLE_BUTTON_KEY);
//        boolean wednesdaySelected =  extras.getBoolean(WEDNESDAY_TOGGLE_BUTTON_KEY);
//        boolean thursdaySelected =  extras.getBoolean(THURSDAY_TOGGLE_BUTTON_KEY);
//        boolean fridaySelected =  extras.getBoolean(FRIDAY_TOGGLE_BUTTON_KEY);
//        boolean saturdaySelected =  extras.getBoolean(SATURDAY_TOGGLE_BUTTON_KEY);
//        boolean sundaySelected =  extras.getBoolean(SUNDAY_TOGGLE_BUTTON_KEY);
//        destinationIntent.putExtra(BEING_ALARM_KEY, mBeginAlarmValue);
//        destinationIntent.putExtra(SWITCH_KEY,switchOn);
//        destinationIntent.putExtra(MONDAY_TOGGLE_BUTTON_KEY,mondaySelected);
//        destinationIntent.putExtra(TUESDAY_TOGGLE_BUTTON_KEY,tuesdaySelected);
//        destinationIntent.putExtra(WEDNESDAY_TOGGLE_BUTTON_KEY,wednesdaySelected);
//        destinationIntent.putExtra(THURSDAY_TOGGLE_BUTTON_KEY,thursdaySelected);
//        destinationIntent.putExtra(FRIDAY_TOGGLE_BUTTON_KEY,fridaySelected);
//        destinationIntent.putExtra(SATURDAY_TOGGLE_BUTTON_KEY,saturdaySelected);
//        destinationIntent.putExtra(SUNDAY_TOGGLE_BUTTON_KEY,sundaySelected);
//        context.startActivity(destinationIntent);
//    }
//
//    private void transferIntentToDestination(Intent intent, Intent destinationIntent) {
//        Bundle extras = intent.getExtras();
//        boolean switchOn = extras.getBoolean(SWITCH_KEY);
//        boolean mondaySelected =  extras.getBoolean(MONDAY_TOGGLE_BUTTON_KEY);
//        boolean tuesdaySelected =  extras.getBoolean(TUESDAY_TOGGLE_BUTTON_KEY);
//        boolean wednesdaySelected =  extras.getBoolean(WEDNESDAY_TOGGLE_BUTTON_KEY);
//        boolean thursdaySelected =  extras.getBoolean(THURSDAY_TOGGLE_BUTTON_KEY);
//        boolean fridaySelected =  extras.getBoolean(FRIDAY_TOGGLE_BUTTON_KEY);
//        boolean saturdaySelected =  extras.getBoolean(SATURDAY_TOGGLE_BUTTON_KEY);
//        boolean sundaySelected =  extras.getBoolean(SUNDAY_TOGGLE_BUTTON_KEY);
//
//        destinationIntent.putExtra(INTENT_EXTRAS_FROM_SET_CLOCK,extras);
//
//    }
}
