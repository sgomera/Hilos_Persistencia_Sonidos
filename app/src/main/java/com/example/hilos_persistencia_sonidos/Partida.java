package com.example.hilos_persistencia_sonidos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

public class Partida extends android.support.v7.widget.AppCompatImageView {

    private int acel;
    private Bitmap pelota, fondo;
    private int tam_pantX, tam_pantY, posX, posY, velX, velY;
    private int tamPelota;
    boolean pelota_sube;

    //constructor
    public Partida(Context contexto, int nivel_dificultad){
        super(contexto);
        //The interface that apps use to talk to the window manager.
        WindowManager manejador_ventana=(WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);
        //Provides information about the size and density of a logical display
        Display pantalla=manejador_ventana.getDefaultDisplay();//We've stored the display into pantalla

        Point maneja_coord=new Point();//Point holds two integer coordinates (X and Y). We've created a new Point object
                //called maneja_coord

        pantalla.getSize(maneja_coord);//Gets the size of the display, in pixels and stores it into a Point object

        tam_pantX=maneja_coord.x; //stores the value x of the Point object maneja_coord into tam_pantX
        tam_pantY=maneja_coord.y; //stores the value y of the Point object maneja_coord into tam_pantY

        //Construir un layout Programático. A Drawable that wraps a bitmap and can be tiled, stretched, or aligned
        BitmapDrawable dibujo_fondo=(BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.paisaje_1);
        fondo=dibujo_fondo.getBitmap();// mirar en api getBitmap en clase BitmapDrawable. esto nos lleva a la siguiente instr.
        fondo=Bitmap.createScaledBitmap(fondo, tam_pantX, tam_pantY, false);//mirar en clase Bitmap

        //contruïm la pilota
        BitmapDrawable objetoPelota=(BitmapDrawable)ContextCompat.getDrawable(contexto, R.drawable.pelota_1);
        pelota=objetoPelota.getBitmap();
        tamPelota=tam_pantY/3;
        pelota=Bitmap.createScaledBitmap(pelota, tamPelota, tamPelota, false);
        posX=tam_pantX/2-tamPelota/2;
        posY=0-tamPelota;

        //acceleració segons el nivell de dificultat que haguem premut (tres botons de dificultat)
        acel=nivel_dificultad*(maneja_coord.y/400);
    }

    public boolean toque(int x, int y){
        if(y<tam_pantY/3) return false; //si s'ha tocat el terç superior de la pantalla, aleshores false.
        if(velY<=0) return false; //si la pilota està parada, aleshores false.
        if(x<posX || x> posX+tamPelota) return false; //si s'ha tocat fora de la pilota horitzontalment, false.
        if(y<posY || y>posY+tamPelota) return false;// si s'ha tocat fora de la pilota verticalment, false.

        velY=-velY; //s'inverteix la velocitat vertical(rebot)...
        double desplX=x-(posX+tamPelota/2);
        desplX=desplX/(tamPelota/2)*velY/2;
        velX+=(int)desplX;

        return true;
    }


    //retorna true quan la pilota es perd pels bordes. Sinó false
    public boolean movimientoBola(){

        if(posX<0-tamPelota){
            posY=0-tamPelota;
            velY=acel;
        }

        posX+=velX;
        posY+=velY;

        if(posY>=tam_pantY) return true;
        if(posX+tamPelota<0 || posX>tam_pantX) return true;
        if(velY<0) pelota_sube=true;
        if(velY>0 && pelota_sube){
            pelota_sube=false;
        }

        velY+=acel;

        return false;
    }

    protected void onDraw(Canvas lienzo){
        lienzo.drawBitmap(fondo, 0,0, null);
        lienzo.drawBitmap(pelota, posX, posY, null);
    }
}


