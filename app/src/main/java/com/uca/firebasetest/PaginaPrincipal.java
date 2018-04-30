package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
//TODO    CREAR USUARIOS, MEDIANTE FIREBASE AUTHENTICATION. USR Y PASS.

public class PaginaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        ImageButton manual = (ImageButton) findViewById(R.id.manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Manual.class);
                startActivity(intent);
            }
        });

        ImageButton escanear = (ImageButton) findViewById(R.id.intercambiar);
        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Intercambiar.class);
                startActivity(intent);
            }
        });

        ImageButton buscar = (ImageButton) findViewById(R.id.buscar);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Buscar.class);
                startActivity(intent);
            }
        });

        ImageButton informes = (ImageButton) findViewById(R.id.informes);
        informes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Informes.class);
                startActivity(intent);
            }
        });
    }
}
