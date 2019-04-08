package diogenes.br.com.contadorestoque.principal;

import android.os.Bundle;
import android.preference.PreferenceActivity;


import androidx.annotation.Nullable;
import diogenes.br.com.contadorestoque.R;

public class Configuracao extends PreferenceActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}