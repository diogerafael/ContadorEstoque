package diogenes.br.com.contadorestoque.dao;

/**
 * Created by Diogenes on 11/12/2015.
 */

import android.content.Context;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public abstract class  AbstractDao {

    protected SQLiteDatabase dataBase;
    //atributo de manipulação na estrutura do banco
    protected DBHelper openHelper;
    //contexto da aplicação
    protected Context context;



    public SQLiteDatabase getDataBase() {
        return dataBase;
    }
    public void setDataBase(SQLiteDatabase dataBase) {
        this.dataBase = dataBase;
    }
    public DBHelper getOpenHelper() {
        return openHelper;
    }
    public void setOpenHelper(DBHelper openHelper) {
        this.openHelper = openHelper;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    /**********************************************************
     * Metodo: Open; Abre uma conexao com esse context no sqlite
     */
    public void open(Context context){
        try{
            this.openHelper = new DBHelper(context);
            this.dataBase = this.openHelper.getWritableDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /*********************************************************/


    /*********************************************************
     *
     */
    public void close () {
        this.dataBase.close ();
    }
    /*********************************************************/


}
