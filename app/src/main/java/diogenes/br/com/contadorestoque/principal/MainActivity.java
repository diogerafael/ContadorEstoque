package diogenes.br.com.contadorestoque.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import diogenes.br.com.contadorestoque.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnContagem, btnConfig, btnExportar;
    private Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }


    private void initObjects() {
        btnContagem = (ImageView) findViewById(R.id.imgIniciarInventario);
        btnContagem.setOnClickListener(this);

        btnConfig = (ImageView) findViewById(R.id.imgConfiguraoes);
        btnConfig.setOnClickListener(this);

        btnExportar = (ImageView) findViewById(R.id.imgExportarDados);
        btnExportar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnContagem.getId()) {
            in = new Intent(this, Consultar.class);
            startActivity(in);
        }
            if (view.getId() == btnConfig.getId()) {
                in = new Intent(this, Configuracao.class);
                startActivity(in);
            }
            if (view.getId() == btnExportar.getId()) {
                in = new Intent(this, ExportarActivity.class);
                startActivity(in);
            }
    }
}
