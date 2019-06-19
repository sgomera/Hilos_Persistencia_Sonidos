package com.example.hilos_persistencia_sonidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ayuda(View view){
        Intent intencion = new Intent(this, AyudaActividad.class);
        startActivity(intencion);
    }

    public void dificultad(View view){
        String dific = (String) ((Button) view).getText(); //recupera el valor del text del botó i
            //el guarda a dific
        int dificultat = 1 ;
        if (dific.equals("Standard")) dificultat = 2;
        if (dific.equals("Difficult")) dificultat = 3;

        Intent intent = new Intent(this, Gestion.class);
        intent.putExtra("DIFICULTAD", dificultat);

        startActivity(intent);
    }
}
