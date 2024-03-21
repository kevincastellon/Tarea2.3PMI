package com.example.tarea23pmi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tarea23pmi.config.Fotos;
import com.example.tarea23pmi.config.FotosAdapter;
import com.example.tarea23pmi.config.SQLiteConexion;

import java.util.ArrayList;
import java.util.List;

public class ActivityLista extends AppCompatActivity {
    ListView listView;
    List<Fotos> LFoto= new ArrayList<>();
    FotosAdapter Adapter;
    SQLiteConexion Conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Conexion = new SQLiteConexion(this, null);

        listView = (ListView) findViewById(R.id.listView);
        ObtenerTabla();
        Adapter = new FotosAdapter(ActivityLista.this, LFoto);
        listView.setAdapter(Adapter);

    }

    private void ObtenerTabla() {
        SQLiteDatabase db = Conexion.getReadableDatabase();
        Fotos Foto = null;
        //Cursor de base de datos
        Cursor cursor = db.rawQuery(SQLiteConexion.SelectTablePhotos, null);

        //Recorremos el cursor
        try {
            while (cursor != null && cursor.moveToNext()) {
                Foto = new Fotos();
                Foto.setId(cursor.getString(0));
                Foto.setDescripcion(cursor.getString(2));
                LFoto.add(Foto);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}