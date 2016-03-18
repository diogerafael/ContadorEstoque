package diogenes.br.com.contadorestoque.dao;

/**
 * Created by Diogenes on 09/12/2015.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

    private final Context context;
    private static final String DATABASE_NAME = "mybook";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "Todos";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;


    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //carregaDB();
        criarBanco(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO Auto-generated method stub

    }


    /*********************************************************
     * Mtodo:criar Banco, ele verifica se o banco existe e cria um novo
     * @param db
     */
    public void criarBanco(SQLiteDatabase db){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE [item] ( ");
        sql.append("[id] INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT,");
        sql.append("[descricao] varchar(50),");
        sql.append("[quantidade] decimal(16,2)");

        db.execSQL(sql.toString());
        sql = new StringBuilder();

        sql = null;

        sql = new StringBuilder();
        sql.append(" CREATE TABLE [configuracao] ( ");
        sql.append("[id] INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT,");
        sql.append("[caracter_separacao] varchar(1),");
        sql.append("[perguntar_quantidade] int");
        db.execSQL(sql.toString());
        
    }

    @SuppressLint("SdCardPath")
    public void carregaDB(){
        //Carregando o database
        try{
            String destPath = "/data/data/"+ context.getPackageName() + "/databases/"+DATABASE_NAME;
            File f = new File(destPath);
            if(!f.exists()){
                //copyDB(context.getAssets().open("onyx.sqlite"), new FileOutputStream(destPath));
                copiarDataBase();
            }
        }
        catch (IOException  e){
            Log.v("Erro",e.getMessage());
        }
    }

    public void copyDB(InputStream inputStream, OutputStream outputStream) throws IOException{
        byte[] buffer = new byte[1024];
        int lenght;
        while((lenght = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer, 0, lenght);
        }
        inputStream.close();
        outputStream.close();
    }


    private void copiarDataBase() throws IOException{

        InputStream myInput = this.context.getAssets().open(DATABASE_NAME);
        //String outFileName = D + DB_NAME;
        String destPath = "/data/data/"+ context.getPackageName() + "/databases/"+DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(destPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)
        {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

}

