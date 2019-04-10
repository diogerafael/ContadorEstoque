package diogenes.br.com.contadorestoque.principal;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import diogenes.br.com.contadorestoque.dao.ItemDao;
import diogenes.br.com.contadorestoque.model.Item;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import diogenes.br.com.contadorestoque.R;

public class ScanCodeActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{

    ZBarScannerView scannerView;
    Double qtd=1.0;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            scannerView = new ZBarScannerView(this);
            scannerView.setFlash(true);
            scannerView.setAutoFocus(true);
            scannerView.setHorizontalFadingEdgeEnabled(true);

            Dexter.withActivity(this)
                    .withPermissions(Manifest.permission.CAMERA,Manifest.permission.VIBRATE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            setContentView(scannerView);
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    }).check();

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



    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result rawResult) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean pergun_qtd_ler = sharedPreferences.getBoolean("pergun_qtd_ler", false);


        if (pergun_qtd_ler){
            inputBox();
        }


        //inserir no banco
        ItemDao dao = new ItemDao(this);
        dao.save(new Item(rawResult.getContents(),qtd));

        try{
            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);

            boolean vibrar = sharedPreferences.getBoolean("vibrar_ao_ler_codigo", false);

            if (vibrar){
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(100);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scannerView.resumeCameraPreview(this);

    }


    private void inputBox(){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.input_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);


        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.edtqtd);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                         qtd= Double.valueOf(userInputDialogEditText.getText().toString());;
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

}