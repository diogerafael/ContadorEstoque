package diogenes.br.com.contadorestoque.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import diogenes.br.com.contadorestoque.model.Item;

public class ItemDao extends AbstractDao implements InterfaceDao<Item> {


    public ItemDao(Context context) {
        open(context);
    }


    @Override
    public long save(Item entidade) {
        try{
            ContentValues values = new ContentValues();
            //values.put("descricao", entidade.getDescricao());
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
    public int getTotal() {
        String SQL = "SELECT count(id) FROM item ";
        String[] Parm = {  };
        Cursor cursor ;
        try{
            cursor = dataBase.rawQuery(SQL, Parm);
        }catch (Exception e){
            Log.e("erro",e.getMessage());
            return 0;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    public int getTotal(String Parametro) {
        String SQL="";
        SQL = "SELECT count(id) FROM item  where codean like '%" + Parametro + "%'";
        String[] Parm = {  };
        Cursor cursor ;
        try{
            cursor = dataBase.rawQuery(SQL, Parm);
        }catch (Exception e){
            Log.e("erro",e.getMessage());
            return 0;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    @Override
    public List<Item> list() {
        return null;
    }

    @Override
    public List<Item> list(Integer offset,Integer limit) {
        List<Item> lstItens = new ArrayList<Item>();
        String SQL="";
        SQL = "SELECT id,codean,quantidade FROM item order by id limit ? OFFSET ?";
        String[] Parm = { limit.toString(),offset.toString() };
        Cursor cursor = dataBase.rawQuery(SQL, Parm);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lstItens.add(cursorToItem(cursor));
            cursor.moveToNext();
        }
        return lstItens;
    }


    public List<Item> list(String Parametro,Integer offset,Integer limit) {
        List<Item> lstItens = new ArrayList<Item>();
        String SQL="";
        SQL = "SELECT id,codean,quantidade FROM item where codean like '%" + Parametro + "%' order by id limit ? OFFSET ?";
        String[] Parm = { limit.toString(),offset.toString() };
        Cursor cursor = dataBase.rawQuery(SQL, Parm);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lstItens.add(cursorToItem(cursor));
            cursor.moveToNext();
        }
        return lstItens;
    }

    @Override
    public Item getEntidade(int prID) {
        return null;
    }

    private Item cursorToItem(Cursor cursor){
        return new Item(cursor.getString(1),cursor.getDouble(2));
    }
}
