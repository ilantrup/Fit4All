package com.example.fit4all;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class fragIngresoFoto extends Fragment implements View.OnClickListener {
    private static final int RESULT_OK = -1 ;
    Button flechaD;
    ImageView mas;
    de.hdodenhof.circleimageview.CircleImageView foto;
    Button but;
    Context contextOfApplication;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_ingresarfoto,container,false);
        MainActivity main =(MainActivity) getActivity();
         contextOfApplication = main.getApplicationContext();
        flechaD=vista.findViewById(R.id.btnFlechaDerFoto);
        foto=vista.findViewById(R.id.imageViewFoto);
        mas=vista.findViewById(R.id.imageViewMas);
        but=vista.findViewById(R.id.buttonFoto);
        flechaD.setOnClickListener(this);
        but.setOnClickListener(this);
        return vista;
    }

    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(flechaD.getId()== botonApretado.getId()){
            MainActivity main=(MainActivity) getActivity();
            main.pasarAcita();
        }
        if(but.getId()== botonApretado.getId())
        {

        /*      Intent llamarASacarFoto;
        llamarASacarFoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(llamarASacarFoto,1);
      Intent llamarAObtFoto;
            llamarAObtFoto=new Intent(Intent.ACTION_GET_CONTENT);
            llamarAObtFoto.setType("image/*");
            startActivityForResult(Intent.createChooser(llamarAObtFoto,"SeleccioneFoto"),1);*/
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Seleccione una imagen"), 1);
        }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == 1) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream =contextOfApplication.getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista

                            foto.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }
    }
}

