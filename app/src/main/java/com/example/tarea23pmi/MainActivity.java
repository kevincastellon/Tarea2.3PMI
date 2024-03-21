package com.example.tarea23pmi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tarea23pmi.config.SQLiteConexion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView Img;
    Button btnGuardar,btnLista;
    EditText Descripcion;
    String currentPhotoPath;
    static final int Peticion_AccesoCamara = 101;
    static final int Peticion_TomarFoto = 102;
    SQLiteConexion conexion;
    FloatingActionButton btnTomarFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnLista = (Button)findViewById(R.id.btnLista);
        Descripcion = (EditText) findViewById(R.id.edtDescripcion);
        Img = (ImageView)findViewById(R.id.imageView);
        btnTomarFoto = (FloatingActionButton)findViewById(R.id.btnTomarFoto);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permisos();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Description = Descripcion.getText().toString();
                if(Description.isEmpty()){
                    Descripcion.setError("Ingrese una descripción de la imagen");
                }else{
                    GuardarDatos();
                }

            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityLista.class);
                startActivity(intent);
            }
        });


    }
    private void Permisos(){
        // Metodo para obtener los permisos requeridos de la aplicacion
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},Peticion_AccesoCamara);
        }
        else
        {
            dispatchTakePictureIntent();
            //TomarFoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==  Peticion_AccesoCamara){
            if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }{
                Toast.makeText(getApplicationContext(),"Se necesita permiso de la camara",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Peticion_TomarFoto){
//            Bundle extras = data.getExtras();
//            Bitmap imagen = (Bitmap) extras.get("data");
//            Objetoimagen.setImageBitmap(imagen);
            try {
                File foto = new File(currentPhotoPath);
                Img.setImageURI(Uri.fromFile(foto));
            }
            catch (Exception ex)
            {
                ex.toString();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.toString();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.tarea23pmi.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Peticion_TomarFoto);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use  with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String convertImage64(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imagearray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagearray,Base64.DEFAULT);
    }



    public void GuardarDatos() {
        conexion = new SQLiteConexion(this,null);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteConexion.foto, convertImage64(currentPhotoPath));
        values.put(SQLiteConexion.descripcion, Descripcion.getText().toString());
        db.insert(SQLiteConexion.tableName, null, values);
        Toast.makeText(getApplicationContext(), "Registro ingresado ",Toast.LENGTH_LONG).show();
        CleanScreen();
        db.close();
    }
    private void CleanScreen() {
        Img.setImageResource(R.drawable.profile);
        Descripcion.setText("");
    }



}