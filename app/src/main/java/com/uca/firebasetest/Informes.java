package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

//TODO ARRAY PARA LOS ALMACENES (OFICINA, ALMACÉN Y TUNEL) Y OTRO PARA LOS TRÁNSITOS (T05)
//TODO AÑADIR TEXTO MULTILINEA PARA EL RELATIVE LAYOUT DE LOS ALMACENES

public class Informes extends AppCompatActivity {

    private Spinner spinner;
    private DatabaseReference databaseReference;
    String[] vMostradores, vPuertas, vTransitos, vAlmacenes;
    private RelativeLayout rlMostradores, rlPuertas, rlAlmacenes, rlTransitos;
    private Button botonMostradores, botonPuertas, botonAlmacenes, botonTransitos;
    int botonPressed;

    // Text Views Mostradores
    private TextView textViewATB, textViewBTP, textViewMonitor, textViewTeclado, textViewCPU, textViewLSR;

    // Text Views Puertas
    private TextView textViewBGR, textViewDCP, textViewMonitorp, textViewCPUp, textViewTecladop;

    // Text View Transitos
    private TextView txtATBT, txtMonitorT, txtTecladoT, txtCPUT, txtDCPT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        // Instanciamos las variables

        spinner = (Spinner) findViewById(R.id.spinner2);
        rlMostradores = (RelativeLayout) findViewById(R.id.rlMostradores);
        rlPuertas = (RelativeLayout) findViewById(R.id.rlPuertas);
        rlAlmacenes = (RelativeLayout) findViewById(R.id.rlAlmacenes);
        rlTransitos = (RelativeLayout) findViewById(R.id.rlTransitos);
        botonMostradores = (Button) findViewById(R.id.botonMostradores);
        botonPuertas = (Button) findViewById(R.id.botonPuertas);
        botonAlmacenes = (Button) findViewById(R.id.botonAlmacenes);
        botonTransitos = (Button) findViewById(R.id.botonTransitos);

        // Text Views Mostradores
        textViewATB = (TextView) findViewById(R.id.textViewATB);
        textViewBTP = (TextView) findViewById(R.id.textViewBTP);
        textViewLSR = (TextView) findViewById(R.id.textViewPistola);
        textViewCPU = (TextView) findViewById(R.id.textViewCPU);
        textViewMonitor = (TextView) findViewById(R.id.textViewMonitor);
        textViewTeclado = (TextView) findViewById(R.id.textViewTeclado);

        // Text Views Puertas
        textViewBGR = (TextView) findViewById(R.id.txtBGR);
        textViewDCP = (TextView) findViewById(R.id.txtDCP);
        textViewCPUp = (TextView) findViewById(R.id.txtCPUP);
        textViewTecladop = (TextView) findViewById(R.id.txtTecladoP);
        textViewMonitorp = (TextView) findViewById(R.id.txtMonitorP);

        //Text Views Transitos
        txtATBT = (TextView) findViewById(R.id.txtATBT);
        txtMonitorT = (TextView) findViewById(R.id.txtMonitorT);
        txtTecladoT = (TextView) findViewById(R.id.txtTecladoT);
        txtCPUT = (TextView) findViewById(R.id.txtCPUT);
        txtDCPT = (TextView) findViewById(R.id.txtDCPT);

        vMostradores = new String[] {"Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
                "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
                "Mostrador60","Mostrador61","Mostrador62"};
        vPuertas = new String[] {"Puerta A01","Puerta A03","Puerta B05","Puerta B07","Puerta C09","Puerta C11","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
                "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60"};
        vAlmacenes = new String[] {"Almacen", "Oficina", "Tunel"};
        vTransitos = new String[] {"Transito 05"};



        //Listeners

        botonMostradores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, vMostradores));
                rlMostradores.setVisibility(View.VISIBLE);
                rlPuertas.setVisibility(View.INVISIBLE);
                rlAlmacenes.setVisibility(View.INVISIBLE);
                rlTransitos.setVisibility(View.INVISIBLE);
                botonPressed= 1;

            }
        });

        botonPuertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, vPuertas));
                rlMostradores.setVisibility(View.INVISIBLE);
                rlPuertas.setVisibility(View.VISIBLE);
                rlTransitos.setVisibility(View.INVISIBLE);
                rlAlmacenes.setVisibility(View.INVISIBLE);
                botonPressed = 2;
            }
        });

        botonAlmacenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, vAlmacenes));
                rlMostradores.setVisibility(View.INVISIBLE);
                rlPuertas.setVisibility(View.INVISIBLE);
                rlAlmacenes.setVisibility(View.VISIBLE);
                rlTransitos.setVisibility(View.INVISIBLE);
                botonPressed = 3;

            }
        });

        botonTransitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, vTransitos));
                rlMostradores.setVisibility(View.INVISIBLE);
                rlPuertas.setVisibility(View.INVISIBLE);
                rlAlmacenes.setVisibility(View.INVISIBLE);
                rlTransitos.setVisibility(View.VISIBLE);
                botonPressed = 4;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem() != null) {
                    if (botonPressed==1) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mostradores");
                        ValueEventListener l = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String item = spinner.getSelectedItem().toString();
                                item = item.substring(item.length() - 2, item.length());

                                textViewATB.setText(dataSnapshot.child(item).child("ATB").getValue().toString());
                                textViewBTP.setText(dataSnapshot.child(item).child("BTP").getValue().toString());
                                textViewLSR.setText(dataSnapshot.child(item).child("LSR").getValue().toString());
                                textViewMonitor.setText(dataSnapshot.child(item).child("MONITOR").getValue().toString());
                                textViewTeclado.setText(dataSnapshot.child(item).child("TECLADO").getValue().toString());
                                textViewCPU.setText(dataSnapshot.child(item).child("TORRE").getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                textViewATB.setText("Error");
                                textViewBTP.setText("Error");
                                textViewLSR.setText("Error");
                                textViewMonitor.setText("Error");
                                textViewTeclado.setText("Error");
                                textViewCPU.setText("Error");
                            }
                        };
                        databaseReference.addListenerForSingleValueEvent(l);
                    } else if (botonPressed == 2){
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Puertas");
                        ValueEventListener l = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String item = spinner.getSelectedItem().toString();
                                item = item.substring(item.length() - 2, item.length());

                                textViewBGR.setText(dataSnapshot.child(item).child("BGR").getValue().toString());
                                textViewDCP.setText(dataSnapshot.child(item).child("DCP").getValue().toString());
                                textViewMonitorp.setText(dataSnapshot.child(item).child("MONITOR").getValue().toString());

                                if (Integer.parseInt(item) <= 18)
                                    textViewTecladop.setText(dataSnapshot.child(item).child("TECLADO").getValue().toString());
                                else
                                    textViewTecladop.setText("");

                                textViewCPUp.setText(dataSnapshot.child(item).child("TORRE").getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                textViewBGR.setText("Error");
                                textViewDCP.setText("Error");
                                textViewMonitorp.setText("Error");
                                textViewTecladop.setText("Error");
                                textViewCPUp.setText("Error");
                            }
                        };

                        databaseReference.addListenerForSingleValueEvent(l);
                    } else if (botonPressed == 3) {  //BOTON3 MUESTRA LOS ALMACENES

                    } else {  //BOTON 4 MUESTRA LOS TRANSITOS
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Transitos");
                        ValueEventListener l = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String item = spinner.getSelectedItem().toString();
                                item = item.substring(item.length() -2, item.length());

                                txtATBT.setText(dataSnapshot.child(item).child("ATB").getValue().toString());
                                txtDCPT.setText(dataSnapshot.child(item).child("DCP").getValue().toString());
                                txtMonitorT.setText(dataSnapshot.child(item).child("MONITOR").getValue().toString());
                                txtCPUT.setText(dataSnapshot.child(item).child("TORRE").getValue().toString());
                                //txtTecladoT.setText(dataSnapshot.child(item).child("TECLADO").getValue().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                txtTecladoT.setText("Error");
                                txtATBT.setText("Error");
                                txtDCPT.setText("Error");
                                txtCPUT.setText("Error");
                                txtMonitorT.setText("Error");
                                }
                        };

                        databaseReference.addListenerForSingleValueEvent(l);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewATB.setText("");
                textViewBTP.setText("");
                textViewLSR.setText("");
                textViewBGR.setText("");
                textViewDCP.setText("");
                textViewMonitor.setText("");
                textViewTeclado.setText("");
                textViewCPU.setText("");
            }
        });
    }
}
