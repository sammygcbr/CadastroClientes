package br.com.cadastroclientes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.cadastroclientes.model.Cliente;

/**
 * Created by Samantha on 24/12/2018.
 */

public class ClienteDAO extends SQLiteOpenHelper {

    public ClienteDAO(Context context) {
        super(context, "ClientesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Clientes(id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, " +
                "sobrenome TEXT, cpf TEXT, cep TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Clientes";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * Insere um objeto cliente no banco de dados.
     *
     * @param cliente
     */
    public void insere(Cliente cliente){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", cliente.getNome());
        cv.put("sobrenome", cliente.getSobrenome());
        cv.put("cpf", cliente.getCpf());
        cv.put("cep", cliente.getCep());
        db.insert("Clientes", null, cv);
    }

    /**
     * Busca todos os objetos clientes no banco de dados.
     *
     * @return - lista de clientes.
     */
    public List<Cliente> buscarClientes() {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM Clientes order by id DESC";
        List<Cliente> clientes = new ArrayList<>();
        Cursor c = database.rawQuery(sql,null);
        while (c.moveToNext()){
            Cliente cliente = new Cliente();
            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));
            cliente.setSobrenome(c.getString(c.getColumnIndex("sobrenome")));
            cliente.setCpf(c.getString(c.getColumnIndex("cpf")));
            cliente.setCep(c.getString(c.getColumnIndex("cep")));
            clientes.add(cliente);
        }
        c.close();
        return clientes;
    }

    /**
     * Busca um objeto cliente no banco de dados a partir de seu id
     *
     * @param query - id do cliente socilicidado.
     * @return - cliente solicitado.
     */
    public Cliente buscaPorId(Long query) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM Clientes WHERE id like" + query +"";
        List<Cliente> clientes = new ArrayList<>();
        Cursor c = database.rawQuery(sql,null);
        Cliente cliente = new Cliente();
        while (c.moveToNext()){
            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));
            cliente.setSobrenome(c.getString(c.getColumnIndex("sobrenome")));
            cliente.setCpf(c.getString(c.getColumnIndex("cpf")));
            cliente.setCep(c.getString(c.getColumnIndex("cep")));
            clientes.add(cliente);
        }
        c.close();
        return cliente;
    }
}
