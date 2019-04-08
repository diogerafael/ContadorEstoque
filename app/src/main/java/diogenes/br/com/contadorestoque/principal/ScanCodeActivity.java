package diogenes.br.com.contadorestoque.principal;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.util.Log;
import android.view.View;

import diogenes.br.com.contadorestoque.R;

public class ScanCodeActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{

    ZBarScannerView scannerView;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            scannerView = new ZBarScannerView(this);
            scannerView.setFlash(true);
            scannerView.setAutoFocus(true);

            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            // permission is granted, open the camera
                            setContentView(scannerView);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();


//        setContentView(R.layout.activity_scan_code);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }


    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

//    @Override
//    public void handleResult(Result rawResult) {
//            MainActivity.result.setText(rawResult.getText());
//        Log.v("BARCOD", rawResult.getText()); // Prints scan results
//        Log.v("BARCOD", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
//
//        // If you would like to resume scanning, call this method below:
//        scannerView.resumeCameraPreview(this);
//            onBackPressed();//voltar tela anterior
//    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result rawResult) {
        Intent i = new Intent();
        i.putExtra("CODEAN",rawResult.getContents());
        setResult(1, i);

        Log.v("BARCOD", rawResult.getContents()); // Prints scan results
        Log.v("BARCOD", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        scannerView.resumeCameraPreview(this);
        finish();
    }
}
