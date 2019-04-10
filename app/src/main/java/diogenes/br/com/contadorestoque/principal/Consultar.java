package diogenes.br.com.contadorestoque.principal;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import com.paginate.recycler.LoadingListItemSpanLookup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import diogenes.br.com.contadorestoque.R;
import diogenes.br.com.contadorestoque.adapter.ItemAdaper;
import diogenes.br.com.contadorestoque.dao.ItemDao;
import diogenes.br.com.contadorestoque.model.Item;


public class Consultar extends AppCompatActivity implements View.OnClickListener, Paginate.Callbacks {
    private Button btnConsultarCodigoBarras;
    private TextView editCodigo;
    ImageView ivNenhumItem;


    ItemDao dao ;

    private String textoPesquisa = "";

    private ListView listItens;
    private ArrayList<Item> itens = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ItemAdaper adapter;




    View viewLoad;



    //paginacao
    private boolean loading = false;
    private Handler handler;
    private Paginate paginate;
    private int page = 0;
    int qtdRegistros = 0;
    protected int itemsPerPage = 10;
    protected int totalPages = 0;
    protected int threshold = 2;
    protected boolean addLoadingRow = true;
    protected boolean customLoadingListItem = false;
    private static final int GRID_SPAN =4;



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
        ivNenhumItem = (ImageView) findViewById(R.id.nenhum_item_encontrado);
        btnConsultarCodigoBarras = (Button)findViewById(R.id.btnlercodigobarras);
        btnConsultarCodigoBarras.setOnClickListener(this);
        editCodigo = (TextView) findViewById(R.id.textViewCodEan);

        dao = new ItemDao(this);

        adapter = new ItemAdaper(dao.list(),this);

        handler = new Handler();
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
    protected void onResume() {
        super.onResume();
        setupPagination();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==1 && data !=null){
            editCodigo.setText(data.getStringExtra("CODEAN"));
            //inserir na lista
            Item i = new Item();

            i.setCodean(editCodigo.getText().toString());
            i.setQuantidade(1.0);
            //dao.save(i);
            itens.add(i);
            adapter = new ItemAdaper(itens,this);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoadMore() {
        loading = true;
        handler.postDelayed(fakeCallback,1000);
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page >= totalPages;
    }


    private Runnable fakeCallback = new Runnable() {
        @Override
        public void run() {
            if (qtdRegistros > 0) {
                ivNenhumItem.setVisibility(View.GONE);
                page++;
                adapter.addAll(getRandomData());
                loading = false;
            }
            else if (viewLoad != null) {
                viewLoad.setVisibility(View.GONE);
                loading = false;
            }
        }
    };


    private ArrayList<Item> getRandomData() {
        int offset = 0;
        if (page > 0)
            offset = (page *itemsPerPage ) + 1;


        List<Item> lista = null;
        if (TextUtils.isEmpty(textoPesquisa)) {
            qtdRegistros = dao.getTotal();
            lista = dao.list(textoPesquisa,offset,itemsPerPage);
        }else {
            qtdRegistros = dao.getTotal(textoPesquisa);
            lista = dao.list(textoPesquisa,offset,itemsPerPage);
        }
        calcularPaginas();
        if (qtdRegistros == 0)
            ivNenhumItem.setVisibility(View.VISIBLE);
        return  (ArrayList<Item>) lista;
    }

    private void calcularPaginas() {
        totalPages = qtdRegistros / itemsPerPage;
        if ((qtdRegistros % itemsPerPage) > 0)
            totalPages++;
    }




    protected void setupPagination() {
        if (paginate != null) {
            paginate.unbind();
        }

        itemsPerPage = 10;

        loading = false;
        page = 0;
        handler.removeCallbacks(fakeCallback);
        adapter = new ItemAdaper(getRandomData(), this);
        qtdRegistros = 1;
        page = 0;


        @SuppressLint("WrongConstant") RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);
        //mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(adapter);

        paginate = Paginate.with(mRecyclerView, this)
                .setLoadingTriggerThreshold(threshold)
                .addLoadingListItem(addLoadingRow)
                .setLoadingListItemCreator(customLoadingListItem ? new CustomLoadingListItemCreator() : null)
                .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                    @Override
                    public int getSpanSize() {
                        return GRID_SPAN;
                    }
                })
                .build();


    }



    private class CustomLoadingListItemCreator implements LoadingListItemCreator {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            vh.tvLoading.setText(String.format("Total items loaded: %d.\nLoading more...", adapter.getItemCount()));

            // This is how you can make full span if you are using StaggeredGridLayoutManager
            if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) vh.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
    }


    static class VH extends RecyclerView.ViewHolder {
        TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }
    }

}