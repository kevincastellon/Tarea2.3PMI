package com.example.tarea23pmi.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConexion extends SQLiteOpenHelper
{
    public static final String nameDataBase = "Ejercicio3";
    public static final int versionDataBase = 1;
    public static final String tableName = "fotos";
    public static final String id = "id";
    public static final String foto = "foto";
    public static final String descripcion = "description";
    public static final String CreateTable =
            "CREATE TABLE " + tableName + "(" +
                    id + " INTEGER PRIMARY KEY AUTOINCREMENT," + foto + " BLOB," + descripcion + " TEXT" +
                    ")";
    public static final String DropTablePhotos = "DROP TABLE IF EXISTS " + tableName;

    public static final String SelectTablePhotos= "SELECT * FROM " + tableName;


    public SQLiteConexion(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, nameDataBase, factory, versionDataBase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creacion de objetos de bases de datos
        db.execSQL(CreateTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DropTablePhotos);
        onCreate(db);
    }


}
