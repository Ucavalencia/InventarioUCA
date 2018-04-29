package com.uca.firebasetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Informes extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private DatabaseReference databaseReference;
    private RadioGroup radioGroup;
    private RadioButton rbMostradores, rbPuertas;
    String[] vMostradores, vPuertas;
    private RelativeLayout rlMostradores;
    private RelativeLayout rlPuertas;
    private Button botonBuscar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        // Instanciamos las variables

        spinner = (Spinner) findViewById(R.id.spinner2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbMostradores = (RadioButton) findViewById(R.id.rbMostradores);
        rbPuertas = (RadioButton) findViewById(R.id.rbPuertas);
        rlMostradores = (RelativeLayout) findViewById(R.id.rlMostradores);
        rlPuertas = (RelativeLayout) findViewById(R.id.rlPuertas);
        botonBuscar = (Button) findViewById(R.id.botonBuscar);

        vMostradores = new String[] {"Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
                "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
                "Mostrador60","Mostrador61","Mostrador62"};
        vPuertas = new String[] {"Puerta A1/A2","Puerta A3/A4","Puerta B5/B6","Puerta B7/B8","Puerta C9/C10","Puerta C11/C12","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
                "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60"};



        //Listeners

        rbMostradores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, vMostradores));
                rlMostradores.setVisibility(View.VISIBLE);
                rlPuertas.setVisibility(View.INVISIBLE);
            }
        });

        rbPuertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, vPuertas));
                rlMostradores.setVisibility(View.INVISIBLE);
                rlPuertas.setVisibility(View.VISIBLE);
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem() != null) {
                    if (rbMostradores.isChecked()) {
                        String pos = " "+spinner.getSelectedItemPosition();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mostradores");
                        ValueEventListener l = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Toast.makeText(Informes.this, dataSnapshot.child("01").child("ATB").getValue().toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };

                        databaseReference.addListenerForSingleValueEvent(l);

                    }
                }
            }
        });



        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mostradores");




    }
}
