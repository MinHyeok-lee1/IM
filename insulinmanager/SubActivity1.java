package com.example.insulinmanager;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SubActivity1 extends AppCompatActivity {

    TextView timeT;
    TextView year;
    Button sendtimes;
    ImageView tlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); requestWindowFeature(Window.FEATURE_NO_TITLE);
        tlayout = (ImageView)findViewById(R.id.tlayout);
        setContentView(R.layout.activity_sub1);
        timeT = (TextView)findViewById(R.id.timeView);
        year = (TextView)findViewById(R.id.Year);
        year.bringToFront();

        final TimePicker picker=(TimePicker)findViewById(R.id.timePicker);
        picker.setIs24HourView(true);
        picker.bringToFront();

        // ?????? ????????? ????????? ????????????
        // ????????? ????????? ?????? ????????????
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("MM??? dd??? EE?????? a hh??? mm??? ", Locale.getDefault()).format(nextDate);
        String date_text1 = "";
        String date_text2 = new SimpleDateFormat("yyyy???", Locale.getDefault()).format(nextDate);
        String compare = new SimpleDateFormat("a", Locale.getDefault()).format(nextDate);
        String time = new SimpleDateFormat("hh", Locale.getDefault()).format(nextDate);
        String minutes = new SimpleDateFormat("mm", Locale.getDefault()).format(nextDate);
        int change = 0;
        String t = "";

        if(compare.equals("??????")) {
            if(time.equals("12")) t = "12";
            else {
                change = Integer.parseInt(time);
                change = change + 12;
                t = Integer.toString(change);
            }
        }else if (compare.equals("??????") && time.equals("12")) {
            t = "00";
        } else {
            t = time;
        }

        date_text1 = t.concat(minutes);

        Toast.makeText(getApplicationContext(),"?????? ????????? " + date_text + "?????? ????????? ?????????????????????!", Toast.LENGTH_SHORT).show();

        timeT.setText("?????? ?????? ??????: " + date_text);
        Number.Timed = date_text1;
        year.setText(date_text2);

        // ?????? ??????????????? TimePicker ?????????
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));


        if (Build.VERSION.SDK_INT >= 23 ){
            picker.setHour(pre_hour);
            picker.setMinute(pre_minute);
        }
        else{
            picker.setCurrentHour(pre_hour);
            picker.setCurrentMinute(pre_minute);
        }


        sendtimes = (Button) findViewById(R.id.button);
        sendtimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int hour, hour_24, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour_24 = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour_24 = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if(hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                }
                else
                {
                    hour = hour_24;
                    am_pm="AM";
                }

                // ?????? ????????? ???????????? ?????? ?????? ??????
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                // ?????? ?????? ????????? ??????????????? ????????? ?????? ???????????? ??????
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("MM??? dd??? EE?????? a hh??? mm??? ", Locale.getDefault()).format(currentDateTime);
                String date_text1 = "";
                String date_text2 = new SimpleDateFormat("yyyy???", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(getApplicationContext(),date_text + "?????? ????????? ?????????????????????!", Toast.LENGTH_SHORT).show();

                String compare = new SimpleDateFormat("a", Locale.getDefault()).format(currentDateTime);
                String time = new SimpleDateFormat("hh", Locale.getDefault()).format(currentDateTime);
                String minutes = new SimpleDateFormat("mm", Locale.getDefault()).format(currentDateTime);
                int change;
                String t;

                if(compare.equals("??????")) {
                    if(time.equals("12")) t = "12";
                    else {
                        change = Integer.parseInt(time);
                        change = change + 12;
                        t = Integer.toString(change);
                    }
                }else if (compare.equals("??????") && time.equals("12")) {
                    t = "00";
                } else {
                    t = time;
                }
                date_text1 = t.concat(minutes);

                Number.sol = date_text1;
                MainActivity.onClickk();

                //  Preference??? ????????? ??? ??????
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                editor.apply();
                diaryNotification(calendar);

                Toast.makeText(getApplicationContext(),"?????? ????????? " + date_text + "?????? ????????? ?????????????????????!", Toast.LENGTH_SHORT).show();

                year.setText(date_text2);
                timeT.setText("?????? ?????? ??????: " + date_text);
            }

        });
    }

    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // ????????? ????????? ??????

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // ???????????? ?????? ????????? ???????????????
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // ?????? ??? ???????????? ????????? ?????????????????? ??????
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

}

