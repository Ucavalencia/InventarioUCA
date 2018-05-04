package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

//TODO RELLENAR EL SPINNERS DISPOSITIVO
//TODO RELLENAR EL SPINNER UBICACION
//TODO ONCLICK DEL BOTON DAR DE ALTA
//TODO ONCLICK DEL BOTON DAR DE BAJA
//TODO AÑADIR LECTOR DE CODIGO DE BARRAS EN LA LÍNEA DEL CODIGO VLC
//TODO FUNCION AL LECTOR DE CODIGO DE BARRAS

public class AltaBaja extends AppCompatActivity {
    private Spinner spinnerDispositivo;
    private Spinner spinnerUbicacion;
    private EditText textCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_baja);

        spinnerDispositivo = (Spinner) findViewById(R.id.spinnerAltaBaja1);
        spinnerUbicacion = (Spinner) findViewById(R.id.spinnerAltaBaja2);
        textCodigo = (EditText) findViewById(R.id.editTextAltaBaja1);
    }

    public void darAlta(View view)
    {

    }

    public void darBaja(View view)
    {

    }
}
