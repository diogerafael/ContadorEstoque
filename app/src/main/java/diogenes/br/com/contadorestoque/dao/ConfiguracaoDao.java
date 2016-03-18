package diogenes.br.com.contadorestoque.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import diogenes.br.com.contadorestoque.model.Configuracao;

/**
 * Created by Diogenes on 11/12/2015.
 */
public class ConfiguracaoDao extends AbstractDao implements InterfaceDao<Configuracao> {

    private Context context;
    private String table_name = "configuracao";
    private String[] colunas = new String[] { "id", "caracter_separacao","perguntar_quantidade"};

    public ConfiguracaoDao(Context ctx){
        openHelper = new DBHelper(ctx);
        this.context = ctx;
    }


    @Override
    public long save(Configuracao entidade) {
        open();//abrindo conexao com banco
        ContentValues values = new ContentValues();
        values.put("caracter_separacao", entidade.getCaracterSeparacao());
        values.put("perguntar_quantidade", entidade.getPerguntarQuantidade());
        //gravando dados no banco
        return this.dataBase.insert(table_name, null, values);


    }

    @Override
    public long update(Configuracao entidade)
    {
        open();
        String filter = "id="+entidade.getId();
        ContentValues values = new ContentValues();
        values.put("caracter_separacao", entidade.getCaracterSeparacao());
        values.put("perguntar_quantidade", entidade.getPerguntarQuantidade());
        return this.dataBase.update(table_name,values,filter,null);

    }

    @Override
    public long delete(Configuracao entidade) {
        return 0;
    }

    @Override
    public List<Configuracao> list() {
        return null;
    }

    @Override
    public Configuracao getEntidade(int prID)
    {
        Configuracao entidade =null;
        try{//lendo redos da database
            this.dataBase = this.openHelper.getReadableDatabase();
            Cursor cursor = this.dataBase.query("configuracao", colunas,
                    null, null, null, null, "descricao desc");
            if (cursor.moveToNext()) {

                entidade = new Configuracao();
                entidade.setId(cursor.getInt(cursor.getColumnIndex("id")));
                entidade.setCaracterSeparacao(cursor.getString(cursor.getColumnIndex("caracter_separacao")));
                entidade.setPerguntarQuantidade(cursor.getInt(cursor.getColumnIndex("perguntar_quantidade")));
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return entidade;

    }
}
