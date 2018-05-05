package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

//TODO RELLENAR  EL SPINNER ORIGIN
//TODO RELLENAR EL SPINNER DEST
//TODO RELLENAR EL SPINNER DISPOSITIVO
//TODO INSTANCIAR EL BOTON
//TODO IMPLEMENTAR LA FUNCION DEL BOTON

public class Intercambiar extends AppCompatActivity {
    private Spinner spinnerOrigin;
    private Spinner spinnerDest;
    private Spinner spinnerDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambiar);

        final Helper helper = new Helper();
        spinnerOrigin = (Spinner) findViewById(R.id.spinnerIntercambiar1);
        spinnerDest = (Spinner) findViewById(R.id.spinnerIntercambiar2);
        spinnerDisp = (Spinner) findViewById(R.id.spinnerIntercambiar3);

        spinnerOrigin.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, helper.ubicacionesNoAT));
        spinnerDest.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, helper.ubicacionesNoAT));

        spinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinnerOrigin.getSelectedItem().toString().toLowerCase();

                switch (item.substring(0, 1)) {
                    case "m": // Mostradores
                        spinnerDisp.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivosM));
                        break;
                    case "t": // Transitos
                        spinnerDisp.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivosT));
                        break;
                    default: // Puertas
                        spinnerDisp.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivosP));
                        break;
                }

                spinnerDisp.setClickable(true);
                spinnerDisp.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerDisp.setClickable(false);
                spinnerDisp.setEnabled(false);
            }
        });
    }

    public void intercambiarDisp(View view)
    {
        String origin = spinnerOrigin.getSelectedItem().toString();
        String dest = spinnerDest.getSelectedItem().toString();
        if (origin.equals(dest))
        {
            Toast.makeText(getApplicationContext(), "No se puede intercambiar al mismo sitio", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}
