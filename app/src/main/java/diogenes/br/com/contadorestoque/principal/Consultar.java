package diogenes.br.com.contadorestoque.principal;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import diogenes.br.com.contadorestoque.R;

public class Consultar extends Activity implements View.OnClickListener{
    private ImageView btnConsultarCodigoBarras;
    private Button btnCodigoEan;
    private EditText editCodigo;
    private ListView listItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contar_estoque);
    }

    private void initObjects(){
        btnConsultarCodigoBarras = (ImageView)findViewById(R.id.btncodigobarras);
        btnConsultarCodigoBarras.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
    }
}
