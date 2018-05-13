package com.uca.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AltaBaja extends AppCompatActivity {
    private Helper helper;
    private Spinner spinnerDispositivo;
    private EditText textCodigo;

    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_baja);

        helper = new Helper();
        spinnerDispositivo = (Spinner) findViewById(R.id.spinnerAltaBajaDispositivo);
        textCodigo = (EditText) findViewById(R.id.codigoAltaBaja);

        spinnerDispositivo.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, helper.dispositivos));
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
                String numCode = data.getStringExtra("result");
                textCodigo.setText(numCode.substring(numCode.indexOf('-')+1));
            }
        }
    }

    public void darAlta(View view)
    {
        if (textCodigo.getText().toString().isEmpty() || textCodigo.getText().toString().length() != 5)
        {
            Toast.makeText(getApplicationContext(), "Código no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        final String codigo = "VLC-" + textCodigo.getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Almacen").child(spinnerDispositivo.getSelectedItem().toString().toUpperCase());
        ref.child(codigo).push();
        ref.child(codigo).setValue(spinnerDispositivo.getSelectedItem().toString().toUpperCase());
        Toast.makeText(getApplicationContext(), "Se ha dado de alta " + codigo, Toast.LENGTH_SHORT).show();
    }

    public void darBaja(View view)
    {
        if (textCodigo.getText().toString().isEmpty() || textCodigo.getText().toString().length() != 5)
        {
            Toast.makeText(getApplicationContext(), "Código no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        final String codigo = "VLC-" + textCodigo.getText().toString();

        // Solo Almacen
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Almacen").child(spinnerDispositivo.getSelectedItem().toString().toUpperCase());

        // Buscar codigo en su grupo (ATB, BTP, etc)
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if (!item.getKey().equals(codigo))
                        continue;

                    // Anti self-delete
                    if (dataSnapshot.getChildrenCount() <= 1)
                    {
                        dataSnapshot.getRef().child("VLC-00000").push();
                        dataSnapshot.getRef().child("VLC-00000").setValue(spinnerDispositivo.getSelectedItem().toString().toUpperCase());
                    }

                    item.getRef().removeValue();
                    Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Codigo no encontrado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
