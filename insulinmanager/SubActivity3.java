package com.example.insulinmanager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.io.UnsupportedEncodingException;

import static com.example.insulinmanager.MainActivity.BT_MESSAGE_READ;

public class SubActivity3 extends AppCompatActivity {
    Button btt; // 알림버튼
    EditText ett;
    Button bt_calll;
    NotificationManager manager;
    NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel2";
    private static String CHANEL_NAME = "Channel2";

    Button a;
    Button b;
    Button c;
    Button d;
    Button e;
    Button f;

    InputMethodManager imm;
    PendingIntent sentPI;


    Button btn0;
    Button btnc;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_sub3);
        bt_calll = findViewById(R.id.PhoneNumberr);
        ett = findViewById(R.id.ett);
        btt = findViewById(R.id.btt);
        btnc = findViewById(R.id.btnC);
        bt_calll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callphones();
            }
        });
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        btn0 =  findViewById(R.id.buttonSend);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        btnc.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn0.performClick();
            }
        });

        btn0.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(Number.Phonenumbered, "IM 확인 문자입니다.");
            }
        });

        if(name!=null){
            ett.setText(name);
        }

        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti(ett.getText().toString());
            } });

        a = (Button) findViewById(R.id.onL);
        a.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number.sol = "a";
                MainActivity.onClickk();
                }
        });
        b = (Button) findViewById(R.id.offL);
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number.sol = "b";
                MainActivity.onClickk();
            }
        });
        c = (Button) findViewById(R.id.onS);
        c.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number.sol = "c";
                MainActivity.onClickk();
            }
        });
        d = (Button) findViewById(R.id.offS);
        d.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number.sol = "d";
                MainActivity.onClickk();
            }
        });
        e = (Button) findViewById(R.id.onV);
        e.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number.sol = "e";
                MainActivity.onClickk();
            }
        });
        f = (Button) findViewById(R.id.offV);
        f.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Number.sol = "f";
                MainActivity.onClickk();
            }
        });


        if(!Number.reading.equals("new") && Number.flag2.equals("onC")) {
            Number.reading = "new";
            callphones();
        }

        if(!Number.reading.equals("new") && Number.flag1.equals("onM")) {
            Number.reading = "new";
            sendSMS(Number.Phonenumbered, "환자가 인슐린 투약시간 10분이 지나도 투약을 하지 않았습니다. 확인 부탁드립니다.");
        }

    }

    //문자 발송
    private void sendSMS(String phoneNumber, String message) {

        // 권한이 허용되어 있는지 확인한다
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},1);
            Toast.makeText(this,"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
        } else {
            SmsManager sms = SmsManager.getDefault();

            // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
            sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
            Toast.makeText(this,"전송을 완료하였습니다",Toast.LENGTH_LONG).show();
        }
    }

    //알림 전송
    public void showNoti(String name){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT) );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        }else{ builder = new NotificationCompat.Builder(this); }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name",name);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("IM");
        //알림창 메시지
        builder.setContentText("인슐린 미투약");
        builder.setSmallIcon(R.drawable.word);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(1,notification);
    }

    // 긴급 전화
    public void callphones() {
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+Number.Phonenumbered));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            final String [] mycall = { Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(SubActivity3.this, mycall, 1004);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(call);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            callphones();
    }

    public void onClick(View view) {
    }


}

