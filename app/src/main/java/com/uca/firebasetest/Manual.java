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

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Manual extends AppCompatActivity {
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

        button = (Button) findViewById(R.id.buttonIntroducir);
        spinner = (Spinner) findViewById(R.id.spinner);
        dSpinner = (Spinner) findViewById(R.id.spinnerDispositivo);
        mSpinner = (Spinner) findViewById(R.id.spinnerMover);
        textCodigo = (EditText) findViewById(R.id.textCodigo);
        dActual = (TextView) findViewById(R.id.dActual);

        String[] ubicaciones = new String[] {"Almacen","Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
                "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
                "Mostrador60","Mostrador61","Mostrador62","Puerta 01","Puerta 03","Puerta 05","Puerta 07","Puerta 09","Puerta 11","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
                "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60","Transito T05", "Tunel"};

        final String[] dAlmacen = new String[] { "ATB", "BTP", "LSR", "BGR", "DCP", "Monitor", "Teclado", "Torre" };
        final String[] dMostradores = new String[] { "ATB", "BTP", "LSR", "Monitor", "Teclado", "Torre" };
        final String[] dPuertas = new String[] { "BGR", "DCP", "Monitor", "Teclado", "Torre" };
        final String[] dTransitos = new String[] { "ATB", "DCP", "Monitor", "Torre" };

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ubicaciones));
        mSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ubicaciones));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinner.getSelectedItem().toString().toLowerCase();
                if (item.equals("almacen") || item.equals("tunel")) // Almacen y Tunel
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dAlmacen));
                else if (item.substring(0, 1).equals("m")) // Mostradores
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dMostradores));
                else if (item.substring(0, 1).equals("t")) // Transitos
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dTransitos));
                else // Puertas
                    dSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dPuertas));

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
                ref = seleccionarLugar(ref, item);

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
        ref = seleccionarLugar(ref ,item);

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

        ref = seleccionarLugar(ref, item);
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

    public DatabaseReference seleccionarLugar(DatabaseReference ref, String item)
    {
        if (item.equals("almacen")) // Almacen
            ref = ref.child("Almacen");
        else if (item.equals("tunel")) // Tunel
            ref = ref.child("Tunel");
        else if (item.substring(0, 1).equals("m")) // Mostradores
            ref = ref.child("Mostradores");
        else if (item.substring(0, 1).equals("t")) // Transitos
            ref = ref.child("Transitos");
        else // Puertas
            ref = ref.child("Puertas");

        return ref;
    }
}
