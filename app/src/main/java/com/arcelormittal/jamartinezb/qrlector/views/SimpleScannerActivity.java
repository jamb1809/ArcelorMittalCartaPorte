package com.arcelormittal.jamartinezb.qrlector.views;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;



/**
 * Created by jamartinezb on 14/09/2017.
 */

public class SimpleScannerActivity extends Activity implements ZBarScannerView.ResultHandler {

    private final static String TAG = "ScannerLog";
    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        final String code = rawResult.getContents();
        final String format = rawResult.getBarcodeFormat().getName();
        final String fullMessage = "Código: "+code+"\nFormat: "+format;

        try{
            Uri notificacion = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notificacion);
            r.play();
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }

        Toast toast = Toast.makeText(this,fullMessage,Toast.LENGTH_LONG);
        toast.show();
    }

}
