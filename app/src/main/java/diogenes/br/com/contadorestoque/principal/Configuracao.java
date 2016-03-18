package diogenes.br.com.contadorestoque.principal;

import android.app.Activity;
import android.os.Bundle;
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

public class Configuracao extends Activity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    private Switch aSwitch;
    private EditText editconfiguracao;
    private Button btnGravarConfiguracao;
    private diogenes.br.com.contadorestoque.model.Configuracao configuracaoModel;
    private ConfiguracaoDao daoConfiguracao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracao);
    }

    private void initObjects(){
        aSwitch = (Switch)findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(this);

        btnGravarConfiguracao = (Button)findViewById(R.id.btngravarconfguracao);
        btnGravarConfiguracao.setOnClickListener(this);
        editconfiguracao = (EditText)findViewById(R.id.editcaracter);


    }

    private void initDados(){
        configuracaoModel = new diogenes.br.com.contadorestoque.model.Configuracao();
        daoConfiguracao = new ConfiguracaoDao(this);
        configuracaoModel = daoConfiguracao.getEntidade(1);

        if (configuracaoModel.getPerguntarQuantidade() == 1) {
            aSwitch.setChecked(true);
        }else{
            aSwitch.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if  (b) {
            configuracaoModel.setPerguntarQuantidade(1);
        }else {
            configuracaoModel.setPerguntarQuantidade(0);
        }
    }

    @Override
    public void onClick(View view) {
     if (view.getId()==btnGravarConfiguracao.getId()){
         if (editconfiguracao.getText().toString().equals("")){
             Toast.makeText(this,"Definir Caracter de Separação",3000);
             return;
         }
         if (configuracaoModel.getId()==0) {
             daoConfiguracao.save(configuracaoModel);
         }
         else{
             daoConfiguracao.update(configuracaoModel);
         }
     }
    }
}