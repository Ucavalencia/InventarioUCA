package com.uca.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

//TODO    IMPLEMENTAR LAS INSTANCIAS DE LOS ELEMENTOS DEL LAYOUT, LOS LISTENERS Y EL LISTENER DEL BOTON.


public class Buscar extends AppCompatActivity {

    Button botonBuscar;
    TextView textoCodigo;
    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        botonBuscar = (Button) findViewById(R.id.botonBuscarPorCodigo);
        textoCodigo = (TextView) findViewById(R.id.textCodigo);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO implementar el código que busca en toda la base de datos el contenido del campo textoCodigo

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


}
