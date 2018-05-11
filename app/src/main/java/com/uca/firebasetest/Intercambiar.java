package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Intercambiar extends AppCompatActivity {
    private Helper helper;
    private Spinner spinnerOrigin;
    private Spinner spinnerDest;
    private Spinner spinnerDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambiar);

        helper = new Helper();
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
        final String origin = spinnerOrigin.getSelectedItem().toString();
        final String dest = spinnerDest.getSelectedItem().toString();
        if (origin.equals(dest))
        {
            Toast.makeText(getApplicationContext(), "No se puede intercambiar al mismo sitio", Toast.LENGTH_SHORT).show();
            return;
        }


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference refOrigin = helper.seleccionarLugar(ref, origin).child(origin.substring(origin.length() - 2, origin.length())).child(spinnerDisp.getSelectedItem().toString().toUpperCase());
        refOrigin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String dispOrigin = dataSnapshot.getValue(String.class);

                if (!dataSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "Ese dispositivo no existe en " + origin, Toast.LENGTH_SHORT).show();
                    return;
                }

                final DatabaseReference refDest = helper.seleccionarLugar(ref, dest).child(dest.substring(dest.length() - 2, dest.length())).child(spinnerDisp.getSelectedItem().toString().toUpperCase());
                refDest.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        final String dispDest = dataSnapshot1.getValue(String.class);

                        if (!dataSnapshot1.exists())
                        {
                            Toast.makeText(getApplicationContext(), "Ese dispositivo no existe en " + dest, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        refOrigin.setValue(dispDest);
                        refDest.setValue(dispOrigin);
                        Toast.makeText(getApplicationContext(), "Dispositivos intercambiados", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
