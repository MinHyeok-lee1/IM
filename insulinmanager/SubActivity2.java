package com.example.insulinmanager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.util.UUID;

public class SubActivity2 extends AppCompatActivity {
    Button bt; // 알림버튼
    EditText et; // 알림내용

    Button bt_call;
    Button bt_calling;
    EditText et1;
    TextView t1;
    Button btS;
    Button btV;
    Button btM;
    Button btC;
    Button btD;
    NotificationManager manager;
    NotificationCompat.Builder builder;
    ImageView Imageview1 = null;
    ImageView Imageview2 = null;
    ImageView Imageview3 = null;
    ImageView Imageview4 = null;
    ImageView imageview1 = null;
    ImageView imageview2 = null;
    ImageView imageview3 = null;
    ImageView imageview4 = null;
    int i,j,x,y = 0;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_sub2);

        bt_call = findViewById(R.id.PhoneNumber);
        bt_calling = findViewById(R.id.Calling);
        et1 = findViewById(R.id.PhoneInput);
        et1.addTextChangedListener((new PhoneNumberFormattingTextWatcher()));

        Imageview1 = (ImageView)findViewById(R.id.Image1);
        Imageview2 = (ImageView)findViewById(R.id.Image2);
        Imageview3 = (ImageView)findViewById(R.id.Image3);
        Imageview4 = (ImageView)findViewById(R.id.Image4);
        imageview1 = (ImageView)findViewById(R.id.image1);
        imageview2 = (ImageView)findViewById(R.id.image2);
        imageview3 = (ImageView)findViewById(R.id.image3);
        imageview4 = (ImageView)findViewById(R.id.image4);

        Imageview1.setVisibility(View.VISIBLE);
        Imageview2.setVisibility(View.VISIBLE);
        Imageview3.setVisibility(View.VISIBLE);
        Imageview4.setVisibility(View.VISIBLE);
        imageview1.setVisibility(View.INVISIBLE);
        imageview2.setVisibility(View.INVISIBLE);
        imageview3.setVisibility(View.INVISIBLE);
        imageview4.setVisibility(View.INVISIBLE);

        et = findViewById(R.id.et);
        bt = findViewById(R.id.bt);
        t1 = findViewById(R.id.PhoneSave);

        btV = findViewById(R.id.btnV);
        btS = findViewById(R.id.btnS);
        btM = findViewById(R.id.btnM);
        btC = findViewById(R.id.btnC);
        btD = findViewById(R.id.Phonedelete);

        SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
        String text = a.getString("First","");
        t1.setText("저장된 전화번호: " + text);

        Number.Phonenumbered = a.getString("First", "");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        btV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1 - i;

                if ( i == 0 ){
                    Imageview1.setVisibility(View.VISIBLE);
                    imageview1.setVisibility(View.INVISIBLE);
                    Number.sol = "g";
                    MainActivity.onClickk();

                } else{
                    Imageview1.setVisibility(View.INVISIBLE);
                    imageview1.setVisibility(View.VISIBLE);
                    Number.sol = "h";
                    MainActivity.onClickk();
                }
            }
        });

        btS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j = 1 - j;

                if ( j == 0 ){
                    Imageview2.setVisibility(View.VISIBLE);
                    imageview2.setVisibility(View.INVISIBLE);
                    Number.sol = "i";
                    MainActivity.onClickk();
                } else{
                    Imageview2.setVisibility(View.INVISIBLE);
                    imageview2.setVisibility(View.VISIBLE);
                    Number.sol = "j";
                    MainActivity.onClickk();
                }
            }
        });

        btM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x = 1 - x;
                if ( x == 0 ){
                    Imageview3.setVisibility(View.VISIBLE);
                    imageview3.setVisibility(View.INVISIBLE);
                    Number.flag1 = "onM";
                } else{
                    Imageview3.setVisibility(View.INVISIBLE);
                    imageview3.setVisibility(View.VISIBLE);
                    Number.flag1 = "offM";
                }
            }
        });

        btC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y = 1 - y;

                if ( y == 0 ){
                    Imageview4.setVisibility(View.VISIBLE);
                    imageview4.setVisibility(View.INVISIBLE);
                    Number.flag2 = "onC";
                }
                else{
                    Imageview4.setVisibility(View.INVISIBLE);
                    imageview4.setVisibility(View.VISIBLE);
                    Number.flag2 = "offC";
                }
            }
        });

        btD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.clear();
                Toast.makeText(getApplicationContext(), "전화번호 삭제가 완료되었습니다!", Toast.LENGTH_LONG).show();
                editor.commit();

                t1.setText("저장된 전화번호 : " + a.getString("First", ""));
                Number.Phonenumbered = a.getString("First", "");
            }
        });


        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putString("First", "" + et1.getText());
                Toast.makeText(getApplicationContext(), "전화번호 설정이 완료되었습니다!", Toast.LENGTH_LONG).show();
                editor.commit();
                t1.setText("저장된 전화번호 : " + a.getString("First",""));
                Number.Phonenumbered = a.getString("First", "");
            }
        });

        bt_calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callphones();
            }
        });

        if(name!=null){
            et.setText(name);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti(et.getText().toString());
            } });

    }

    //문자 알림
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
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(1,notification);
    }

    public void callphones() {
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+et1.getText()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            final String [] mycall = { Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(SubActivity2.this, mycall, 1004);
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
        finish();
    }
}
