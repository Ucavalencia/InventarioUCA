package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AltaBajaUbicaciones extends AppCompatActivity {
    private Spinner spinner;
    private EditText nombreID;
    private CheckBox[] checkBoxes = new CheckBox[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_baja_ubicaciones);

        spinner = (Spinner) findViewById(R.id.spinnerAltaBajaUbi1);
        nombreID = (EditText) findViewById(R.id.textAltaBajaUbiNombreID);
        checkBoxes[0] = (CheckBox) findViewById(R.id.checkBoxATB);
        checkBoxes[1] = (CheckBox) findViewById(R.id.checkBoxBTP);
        checkBoxes[2] = (CheckBox) findViewById(R.id.checkBoxDCP);
        checkBoxes[3] = (CheckBox) findViewById(R.id.checkBoxLSR);
        checkBoxes[4] = (CheckBox) findViewById(R.id.checkBoxBGR);
        checkBoxes[5] = (CheckBox) findViewById(R.id.checkBoxMon);
        checkBoxes[6] = (CheckBox) findViewById(R.id.checkBoxTec);
        checkBoxes[7] = (CheckBox) findViewById(R.id.checkBoxCPU);

        String[] s = new String[] { "Mostradores", "Puertas", "Transitos", "Almacén" };
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, s));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nombreID.setText("");
                for (CheckBox box : checkBoxes)
                    box.setChecked(false);

                checkBoxes[5].setChecked(true);
                checkBoxes[6].setChecked(true);
                checkBoxes[7].setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void botonCrear(View view)
    {
        if (nombreID.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Debes introducir un nombre/ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Al menos un checkbox
        boolean checked = false;
        for (CheckBox box : checkBoxes)
        {
            if (!box.isChecked())
                continue;

            checked = true;
            break;
        }

        if (!checked)
        {
            Toast.makeText(getApplicationContext(), "Selecciona al menos un dispositivo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Almacen
        if (spinner.getSelectedItem().toString().toLowerCase().substring(0, 1).equals("a"))
        {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(nombreID.getText().toString());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        Toast.makeText(getApplicationContext(),  nombreID.getText().toString() + " ya existe", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ref.push();
                    for (CheckBox box : checkBoxes)
                    {
                        if (!box.isChecked())
                            continue;

                        // Monitor / Teclado / Torre
                        String disp = box.getText().toString().toUpperCase();
                        switch (disp)
                        {
                            case "MON":
                                disp = "MONITOR";
                                break;
                            case "TEC":
                                disp = "TECLADO";
                                break;
                            case "CPU":
                                disp = "TORRE";
                                break;

                            default:
                                break;
                        }

                        ref.child(disp).push();
                        ref.child(disp).child("VLC-00000").push();
                        ref.child(disp).child("VLC-00000").setValue(disp);
                    }
                    Toast.makeText(getApplicationContext(), nombreID.getText().toString() + " creado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(spinner.getSelectedItem().toString()).child(nombreID.getText().toString());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        Toast.makeText(getApplicationContext(),  nombreID.getText().toString() + " ya existe", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ref.push();
                    for (CheckBox box : checkBoxes)
                    {
                        if (!box.isChecked())
                            continue;

                        // Monitor / Teclado / Torre
                        String disp = box.getText().toString().toUpperCase();
                        switch (disp)
                        {
                            case "MON":
                                disp = "MONITOR";
                                break;
                            case "TEC":
                                disp = "TECLADO";
                                break;
                            case "CPU":
                                disp = "TORRE";
                                break;

                            default:
                                break;
                        }

                        ref.child(disp).push();
                        ref.child(disp).child("VLC-00000").push();
                        ref.child(disp).child("VLC-00000").setValue(disp);
                    }
                    Toast.makeText(getApplicationContext(), nombreID.getText().toString() + " creado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
