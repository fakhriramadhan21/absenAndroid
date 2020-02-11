package com.example.barcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SuksesActivity extends AppCompatActivity {
WebView webView;
Button submit;
TextView TV_NIM, TV_TIMESTAMP,TV_EVENTCODE;
String url,nim,Eventcode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.sukses);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        nim = bundle.getString("nim");
        Eventcode = bundle.getString("eventcode");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        TV_NIM = (TextView) findViewById(R.id.tv_nim);
        TV_TIMESTAMP = (TextView) findViewById(R.id.tv_timestamp);
        TV_EVENTCODE = (TextView) findViewById(R.id.tv_eventcode);

        TV_NIM.setText(nim);
        TV_EVENTCODE.setText(Eventcode);
        TV_TIMESTAMP.setText(currentDateTime);



    }


    public void backHome(View view) {
        Intent intent = new Intent(SuksesActivity.this, EventCode.class);
        startActivity(intent);
    }
}






