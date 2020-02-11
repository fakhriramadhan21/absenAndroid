package com.example.barcode;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

public class ProcessBarcode extends AppCompatActivity {
WebView webView;
Button submit;
EditText eventcode;

String url,nim,Eventcode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.gagal);

        webView = (WebView) findViewById(R.id.qrprocessor);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        nim = bundle.getString("nim");
        Eventcode = bundle.getString("eventcode");
        LoadUrl(url);




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
                Toast.makeText(ProcessBarcode.this, "Processing...",
                        Toast.LENGTH_SHORT).show();
                if (url.contains("https://absen.hmsitel-u.id/index.php/Kehadiran/success")) {
                    Toast.makeText(ProcessBarcode.this, "Absensi sukses",
                            Toast.LENGTH_LONG).show();

                   Intent intent = new Intent(ProcessBarcode.this, SuksesActivity.class);


                Toast.makeText(ProcessBarcode.this, "Redirecting to :"+url,
                        Toast.LENGTH_LONG).show();

                intent.putExtra("url", url);
                intent.putExtra("nim", nim);
                intent.putExtra("eventcode", Eventcode);
                startActivity(intent);
                } else if (url.contains("https://absen.hmsitel-u.id/index.php/Kehadiran/failed")){
                    Toast.makeText(ProcessBarcode.this, "Absensi gagal",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        webView.loadUrl(url);

    }

}






