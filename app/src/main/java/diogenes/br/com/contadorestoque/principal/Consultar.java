package diogenes.br.com.contadorestoque.principal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import diogenes.br.com.contadorestoque.R;
import diogenes.br.com.contadorestoque.adapter.ItemAdaper;
import diogenes.br.com.contadorestoque.dao.ItemDao;
import diogenes.br.com.contadorestoque.model.Item;


public class Consultar extends AppCompatActivity implements View.OnClickListener {
    private Button btnConsultarCodigoBarras;
    private TextView editCodigo;
    private ListView listItens;
    private ArrayList<Item> itens = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ItemAdaper adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contar_estoque);

        initObjects();


        mRecyclerView = (RecyclerView) findViewById(R.id.listView);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);
        //mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    private void initObjects(){
        btnConsultarCodigoBarras = (Button)findViewById(R.id.btnlercodigobarras);
        btnConsultarCodigoBarras.setOnClickListener(this);
        editCodigo = (TextView) findViewById(R.id.textViewCodEan);

        adapter = new ItemAdaper(itens,this);
    }

    @Override
    public void onClick(View view) {
     if (view.getId()== btnConsultarCodigoBarras.getId()){
        //scannerView = new ZXingScannerView(this);
         Intent in = new Intent(this,ScanCodeActivity.class);
         startActivityForResult(in,1);
     }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==1 && data !=null){
            editCodigo.setText(data.getStringExtra("CODEAN"));
            //inserir na lista
            Item i = new Item();
            ItemDao dao = new ItemDao();
            i.setCodean(editCodigo.getText().toString());
            i.setQuantidade(1.0);
            //dao.save(i);
            itens.add(i);
        }
    }
}
