package com.example.tarea23pmi.config;
import android.content.Context;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tarea23pmi.R;

import java.util.List;

public class FotosAdapter extends ArrayAdapter<Fotos> implements View.OnClickListener {
SQLiteConexion Conexion;
private List<Fotos> Foto;
private Context context;


    public FotosAdapter(@NonNull Context context, List<Fotos> Foto) {
        super(context, R.layout.activity_item, Foto);
        this.Foto = Foto;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fotos photograh = Foto.get(position);

        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_item,null);
        }
        ImageView imagen = view.findViewById(R.id.Img2);
        TextView description = view.findViewById(R.id.DescripcionAd);

        imagen.setImageBitmap(obtenerImagen(photograh.getId()));
        description.setText(photograh.getDescripcion());

        return view;
    }
    private Bitmap obtenerImagen(String id) {
        Conexion = new SQLiteConexion(context, null);
        SQLiteDatabase db = Conexion.getReadableDatabase();
        Bitmap bitmap;
        String selectQuery = "SELECT " + SQLiteConexion.foto +" FROM " + SQLiteConexion.tableName + " WHERE id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            byte[] imageData = Base64.decode(cursor.getBlob(cursor.getColumnIndexOrThrow("foto")), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
        else{
            bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.profile);
        }
        // Cierra el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
        return bitmap;
    }


    @Override
    public void onClick(View v) {

    }
}
