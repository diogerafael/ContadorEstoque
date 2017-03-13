package diogenes.br.com.contadorestoque.principal;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import diogenes.br.com.contadorestoque.dao.ConfiguracaoDao;
import  diogenes.br.com.contadorestoque.model.*;
import diogenes.br.com.contadorestoque.R;

public class Configuracao extends PreferenceActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}