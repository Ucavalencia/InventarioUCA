package com.uca.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//TODO El dato introducido manualmente ya no incluye las letras "VLC" hay que añadirselo cuando consultamos la BBDD o escribamos sobre ella

public class Manual extends AppCompatActivity {
    private Helper helper;
    private Button button;
    private Spinner spinner;
    private Spinner dSpinner;
    private Spinner mSpinner;
    private EditText textCodigo;
    private TextView dActual;

    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        helper = new Helper();
        button = (Button) findViewById(R.id.botonBuscarPorCodigo);
        spinner = (Spinner) findViewById(R.id.spinner);
        dSpinner = (Spinner) findViewById(R.id.spinnerDispositivo);
        mSpinner = (Spinner) findViewById(R.id.spinnerMover);
        textCodigo = (EditText) findViewById(R.id.textCodigo);
        dActual = (TextView) findViewById(R.id.dActual);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, helper.ubicacionesAT));
        mSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, helper.ubicacionesAT));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinner.getSelectedItem().toString().toLowerCase();
                if (item.equals("almacen") || item.equals("tunel")) // Almacen y Tunel
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivos));
                else if (item.substring(0, 1).equals("m")) // Mostradores
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivosM));
                else if (item.substring(0, 1).equals("t")) // Transitos
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivosT));
                else // Puertas
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, helper.dispositivosP));

                if (item.equals("almacen") || item.equals("tunel"))
                {
                    mSpinner.setEnabled(false);
                    mSpinner.setClickable(false);
                }
                else
                {
                    mSpinner.setEnabled(true);
                    mSpinner.setClickable(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                button.setEnabled(false);
                button.setClickable(false);

                String item = spinner.getSelectedItem().toString().toLowerCase();
                if (item.equals("almacen") || item.equals("tunel")) // Almacen
                {
                    dActual.setText("");
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref = helper.seleccionarLugar(ref, item);

                // Seleccionar ID lugar
                ref = ref.child(item.substring(item.length() - 2, item.length()));

                // Seleccionar dispositivo
                ref = ref.child(dSpinner.getSelectedItem().toString().toUpperCase());

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        dActual.setText(value);

                        button.setEnabled(true);
                        button.setClickable(true);

                        if (dActual.getText().toString().isEmpty())
                        {
                            mSpinner.setEnabled(false);
                            mSpinner.setClickable(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        dActual.setText("");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dActual.setText("");
            }
        });


    }

    public void startScanner(View view)
    {
        // Start scanner here
        Intent intent = new Intent(getApplicationContext(), QRScanner.class);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                textCodigo.setText(data.getStringExtra("result"));
            }
        }
    }

    public void introducirCodigo(View view)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();

        // Seleccionar lugar principal
        String item = spinner.getSelectedItem().toString().toLowerCase();
        ref = helper.seleccionarLugar(ref ,item);

        // Casos especificos (Almacen/Tunel)
        if (ref.getKey().equals("Almacen") || ref.getKey().equals("Tunel"))
        {
            // Coger dispositivo e introducir directamente
            ref = ref.child(dSpinner.getSelectedItem().toString().toUpperCase());

            // Crear child con nombre de codigo VLC
            ref.child(textCodigo.getText().toString()).push();
            ref = ref.child(textCodigo.getText().toString());
            ref.setValue(dSpinner.getSelectedItem().toString().toUpperCase());
            return;
        }

        // Seleccionar ID lugar
        ref = ref.child(item.substring(item.length() - 2, item.length()));

        // Seleccionar dispositivo
        ref = ref.child(dSpinner.getSelectedItem().toString().toUpperCase());

        // Mover antiguo dispositivo
        moverAntiguoDispositivo();

        // Introducir valor
        ref.setValue(textCodigo.getText().toString());
    }

    public void moverAntiguoDispositivo()
    {
        String codigo = dActual.getText().toString();
        if (codigo.isEmpty())
            return;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String item = mSpinner.getSelectedItem().toString().toLowerCase();

        ref = helper.seleccionarLugar(ref, item);
        if (ref.getKey().equals("Almacen") || ref.getKey().equals("Tunel"))
        {
            ref = ref.child(dSpinner.getSelectedItem().toString().toUpperCase());

            ref.child(codigo).push();
            ref = ref.child(codigo);
            ref.setValue(dSpinner.getSelectedItem().toString().toUpperCase());
        }
        else
        {
            ref = ref.child(item.substring(item.length() - 2, item.length()));
            ref = ref.child(dSpinner.getSelectedItem().toString().toUpperCase());
            ref.setValue(codigo);
        }
    }
}
