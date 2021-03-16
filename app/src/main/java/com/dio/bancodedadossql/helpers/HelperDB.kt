package com.dio.bancodedadossql.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(
    context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "contato.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "contato"
    val COLUMNS_ID = "id"
    val COLUMNS_TELEFONE = "nome"
    val COLUMNS_NOME = "telefone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_NOME TEXT NOT NULL," +
            "$COLUMNS_TELEFONE TEXT NOT NULL," +
            "" +
            "PRIMARY KEY ($COLUMNS_ID  AUTOINCREMENT)" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }


    fun buscarContatos(busca: String): List<ContatosVO> {
        val db = readableDatabase ?: return mutableListOf()
        val lista = mutableListOf<ContatosVO>()
        val sql = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(sql, null) ?: return mutableListOf()
        while(cursor.moveToNext()){
            var contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                cursor.getString( cursor.getColumnIndex(COLUMNS_TELEFONE))
            )
            lista.add (contato)
        }
        return lista
    }
}