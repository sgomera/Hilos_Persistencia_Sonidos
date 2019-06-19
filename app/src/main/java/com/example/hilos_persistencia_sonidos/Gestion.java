package com.example.hilos_persistencia_sonidos;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

//aquesta activity seria el controller. Té part gràfica, per això és una activity
public class Gestion extends AppCompatActivity {
    private Partida partida;
    private int dificultad;
    private int FPS = 30;//frames per segon per a l'animació de la pilota
    private Handler temporizador;//manejador que necessiten els fils per executar activitats en paral·lel
    private int botes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        botes = 0;
        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("DIFICULTAD");//recuperem el nivell de dificultat de l'act. principal

        partida = new Partida(getApplicationContext(), dificultad);

        setContentView(partida); //carrega la partida gràficament

        temporizador = new Handler();//inicialitzar el temporitzador
        temporizador.postDelayed(elHilo, 2000); //no s'havia arrencat elHilo de la pilota!!!


    }

    //el moviment de la pilota ha d'anar en un fil diferent, per tal de no bloquejar la part gràfica de la partida
    private Runnable elHilo = new Runnable() {
        @Override
        public void run() {
            if(partida.movimientoBola()) fin(); //si la bola s'ha perdut, aleshores fin().
            else {
                partida.invalidate(); //elimina el contenido de ImageView (esborra el frame anterior)
                    // i torna a cridar a onDraw
                temporizador.postDelayed(elHilo, 1000/FPS); //determina la velocitat de l'animació
            }
        }
    };

    //capturar el toque (on ha tocat l'usuari la pantalla)
    public boolean onTouchEvent(MotionEvent evento){
        int x = (int) evento.getX();
        int y = (int) evento.getY();

        //ara ja tenim la info d'on s'ha tocat i podem cridar al mètode toque de la partida
        if  (partida.toque(x, y)) botes++;
        return false; //quan ho ha fet tot surt i ja està, no sé pq false??
    }

    public void fin(){
        temporizador.removeCallbacks(elHilo);//esborra els calls de més al elHilo

        //Intent intent = new Intent(); ell no ha posat res a dins, no deu caler, pq fa el finish
        Intent in = new Intent();
        in.putExtra("PUNTUACION", botes);

        setResult(RESULT_OK, in);
        finish();//destrueix l'activitat actual

    }
}
