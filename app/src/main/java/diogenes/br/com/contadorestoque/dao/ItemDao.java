package diogenes.br.com.contadorestoque.dao;

import android.content.ContentValues;
import android.util.Log;

import java.util.List;

import diogenes.br.com.contadorestoque.model.Item;

public class ItemDao extends AbstractDao implements InterfaceDao<Item> {
    @Override
    public long save(Item entidade) {
        try{
            ContentValues values = new ContentValues();
            values.put("descricao", entidade.getDescricao());
            values.put("codean",entidade.getCodean());
            values.put("quantidade", entidade.getQuantidade());

            return dataBase.insert("item", null, values);
        }catch (Exception e){
            Log.e("Inserir Itens","Erro ao inserir cliente"+e.getMessage());
            return 0;
        }
    }

    @Override
    public long update(Item entidade) {
        return 0;
    }

    @Override
    public long delete(Item entidade) {
        return 0;
    }

    @Override
    public List<Item> list() {
        return null;
    }

    @Override
    public Item getEntidade(int prID) {
        return null;
    }
}
