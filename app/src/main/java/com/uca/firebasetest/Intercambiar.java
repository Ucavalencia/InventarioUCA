package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;


//TODO    IMPLEMENTAR LAS INSTANCIAS DE LOS ELEMENTOS DEL LAYOUT, LOS LISTENERS Y EL LISTENER DEL BOTON.

public class Intercambiar extends AppCompatActivity {
    private Spinner spinnerDisp;
    private Spinner spinnerOrigin;
    private Spinner spinnerDest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambiar);

        spinnerDisp = (Spinner) findViewById(R.id.spinnerIntercambiar1);
        spinnerOrigin = (Spinner) findViewById(R.id.spinnerIntercambiar2);
        spinnerDest = (Spinner) findViewById(R.id.spinnerIntercambiar3);
    }

    public void intercambiarDisp(View view)
    {

    }
}
