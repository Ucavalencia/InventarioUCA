package com.uca.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScanner extends AppCompatActivity
{
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        // Init Scanner
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // QR Scanner result holder
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        // Result is found
        if (result != null) {
            //getIntent().putExtra("codeResult", result.getContents());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", result.getContents());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else // Result is not found
            super.onActivityResult(requestCode, resultCode, data);
    }
}