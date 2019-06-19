package com.example.hilos_persistencia_sonidos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    int record;

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
        if (dific.equals(getString(R.string.medio))) dificultat = 2;
        if (dific.equals(getString(R.string.dificil))) dificultat = 3;

        Intent intent = new Intent(this, Gestion.class);
        intent.putExtra("DIFICULTAD", dificultat);

        //startActivity(intent); // si volem obtenir el resultat del record de la següent activitat, ha
                                //d'ésser startActivityforResult()
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent puntuacion) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 1 || resultCode !=RESULT_OK) return;

        int resultado = puntuacion.getIntExtra("PUNTUACION", 0);

        if (resultado > record) {
            record = resultado;
            TextView caja = (TextView) findViewById(R.id.record);
            caja.setText("Record: " + record);
            guardarRecord();
        } else{
            String puntuacionPartida = "" + resultado;
            Toast toast = Toast.makeText(this, puntuacionPartida, Toast.LENGTH_LONG);
            toast.show();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        leeRecord();

    }

    private void guardarRecord(){
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor miEditor = datos.edit();
        miEditor.putInt("RECORD", record);
        miEditor.apply();
    }

    private void leeRecord(){
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        record = datos.getInt("RECORD", 0);
        TextView caja = (TextView) findViewById(R.id.record);
        caja.setText("Record: " + record);
    }
}
