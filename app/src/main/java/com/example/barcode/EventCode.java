package com.example.barcode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;
import info.vividcode.android.zxing.CaptureResult;

public class EventCode extends AppCompatActivity {
    int ALL_PERMISSIONS = 101;
    WebView webView;
Button submit;
EditText eventcode;
String url, Eventcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.eventcode);
        permision();


        webView = (WebView) findViewById(R.id.UrlEventcode);
        submit = (Button) findViewById(R.id.cekBtn);
        eventcode = (EditText) findViewById(R.id.eventcode);

        url= "";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Eventcode = eventcode.getText().toString();
                url = "https://absen.hmsitel-u.id/index.php/Kehadiran/checkEvent/"+Eventcode;

//                webView.loadUrl(url);
                LoadUrl(url);


            }
        });




    }
    public void LoadUrl(String url){
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                progDailog.show();
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Toast.makeText(EventCode.this, "Processing...",
                        Toast.LENGTH_SHORT).show();
                if (url.contains("https://absen.hmsitel-u.id/index.php/Kehadiran/success")) {
                    Toast.makeText(EventCode.this, "Eventcode ditemukan",
                            Toast.LENGTH_LONG).show();
                    Intent captureIntent = new Intent(EventCode.this, CaptureActivity.class);

                    CaptureActivityIntents.setPromptMessage(captureIntent, "Barcode scanning...");

                    startActivityForResult(captureIntent, 0);
                } else if (url.contains("https://absen.hmsitel-u.id/index.php/Kehadiran/failed")){
                    Toast.makeText(EventCode.this, "Eventcode tidak ditemukan",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        webView.loadUrl(url);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(EventCode.this, value,
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(EventCode.this, ProcessBarcode.class);

                url = "https://absen.hmsitel-u.id/index.php/Kehadiran/addKehadiran/"+value+"/"+Eventcode;
                Toast.makeText(EventCode.this, "Redirecting to :"+url,
                        Toast.LENGTH_LONG).show();

                intent.putExtra("url", url);
                intent.putExtra("nim", value);
                intent.putExtra("eventcode", Eventcode);
                startActivity(intent);
//                tvScanResult.setText(value);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(EventCode.this, "Tidak menerima nilai",
                        Toast.LENGTH_LONG).show();
//                tvScanResult.setText("Scanning Gagal, mohon coba lagi.");
            }
        } else {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void permision(){
        final String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WAKE_LOCK, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);
    }
}






