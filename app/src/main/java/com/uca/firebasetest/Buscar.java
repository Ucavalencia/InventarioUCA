package com.uca.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Buscar extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Button botonBuscar;
    TextView textoCodigo;
    TextView textUbicacion;
    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        botonBuscar = (Button) findViewById(R.id.botonBuscarPorCodigo);
        textoCodigo = (TextView) findViewById(R.id.textCodigo);
        textUbicacion = (TextView) findViewById(R.id.textUbicacion);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textoCodigo.getText().toString().isEmpty())
                    return;

                textUbicacion.setText("Cargando..");
                if (textoCodigo.getText().toString().matches("\\d+"))
                    buscarCodigo("VLC-" + textoCodigo.getText().toString());
                else
                    buscarCodigo(textoCodigo.getText().toString().toUpperCase());
            }
        });
    }

    public void startScanner(View view) {
        Intent intent = new Intent(getApplicationContext(), QRScanner.class);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Check which request we're responding to
        if (requestCode== REQUEST){
            //Now make sure the request was successful
            if (resultCode == RESULT_OK) {
                textoCodigo.setText(data.getStringExtra("result"));
            }
        }
    }

    public void buscarCodigo(final String codigo)
    {
        // Busca en cada uno de los hijos que hay (deep nest iteration)
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot firstSnapshot) {
                // Hijos de root ("inventario-uca")
                for (DataSnapshot child : firstSnapshot.getChildren())
                {
                    // Entramos en el hijo (ej.: "inventario-uca/almacen")
                    final DatabaseReference innerRef = FirebaseDatabase.getInstance().getReference().child(child.getKey());

                    // Almacen / Tunel tienen una estructura diferente
                    if (child.getKey().equals("Almacen")
                            || child.getKey().equals("Tunel"))
                    {
                        innerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot secondSnapshot) {
                                for (DataSnapshot id : secondSnapshot.getChildren())
                                {
                                    // Entramos en el hijo del hijo anterior (ej.: "inventario-uca/almacen/ATB")
                                    DatabaseReference subRef = innerRef.child(id.getKey());
                                    subRef.orderByKey().equalTo(codigo).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot thirdSnapshot) {
                                            if (thirdSnapshot.getChildrenCount() > 0)
                                            {
                                                String res = thirdSnapshot.getRef().getParent().getKey() + " -> " + thirdSnapshot.getKey();
                                                textUbicacion.setText(res);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            textUbicacion.setText("Error de conexión");
                                        }
                                    });
                                }
                                textUbicacion.setText("No encontrado.");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                textUbicacion.setText("Error de conexión");
                            }
                        });
                    }
                    else // Mostradores / Puertas / Transitos
                    {
                        innerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot secondSnapshot) {
                                for (DataSnapshot id : secondSnapshot.getChildren())
                                {
                                    // Entramos en el hijo del hijo anterior (ej.: "inventario-uca/almacen/ATB")
                                    DatabaseReference subRef = innerRef.child(id.getKey());
                                    subRef.orderByValue().equalTo(codigo).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot thirdSnapshot) {
                                            if (thirdSnapshot.getChildrenCount() > 0)
                                            {
                                                String res = thirdSnapshot.getRef().getParent().getKey() + " -> " + thirdSnapshot.getKey();
                                                textUbicacion.setText(res);
                                            }
                                        }

                                        @Override
                                       public void onCancelled(DatabaseError databaseError) {
                                            textUbicacion.setText("Error de conexión");
                                        }
                                    });
                                }
                                textUbicacion.setText("No encontrado.");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                textUbicacion.setText("Error de conexión");
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textUbicacion.setText("Error de conexión");
            }
        });
    }
}
