package diogenes.br.com.contadorestoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import diogenes.br.com.contadorestoque.R;
import diogenes.br.com.contadorestoque.interfaces.RecyclerViewOnClickListenerHack;
import diogenes.br.com.contadorestoque.model.Item;

public class ItemAdaper extends RecyclerView.Adapter<ItemAdaper.ViewHolder> {
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context context;
    private List<Item> lstItens;


    public ItemAdaper(List<Item> lstItens, Context context) {
        this.lstItens = lstItens;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contagem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item bean  = lstItens.get(position);
        holder.txtcodean.setText(bean.getCodean());
        //holder.txtdescricao.setText(bean.getDescricao());
        holder.txtqtd.setText(bean.getQuantidade().toString());
    }

    @Override
    public int getItemCount() {
        return lstItens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtcodean;
        public TextView txtdescricao;
        public TextView txtqtd;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtcodean = (TextView) itemView.findViewById(R.id.txtcodean);
            this.txtdescricao= (TextView) itemView.findViewById(R.id.txtdescricao);
            this.txtqtd   = (TextView) itemView.findViewById(R.id.txtqtd);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }


    public void addItem(Item item){
        this.lstItens.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Item> items) {
        int previousDataSize = this.lstItens.size();
        this.lstItens.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }
}
