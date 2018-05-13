package com.uca.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;


public class PaginaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        ImageButton manual = (ImageButton) findViewById(R.id.botonPaginaPrincipal1);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Manual.class);
                startActivity(intent);
            }
        });

        ImageButton intercambiar  = (ImageButton) findViewById(R.id.botonPaginaPrincipal2);
        intercambiar .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Intercambiar.class);
                startActivity(intent);
            }
        });

        ImageButton darDeAlta   = (ImageButton) findViewById(R.id.botonPaginaPrincipal5);
        darDeAlta  .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AltaBaja.class);
                startActivity(intent);
            }
        });

        ImageButton buscarPorUbi  = (ImageButton) findViewById(R.id.botonPaginaPrincipal3);
        buscarPorUbi .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Informes.class);
                startActivity(intent);
            }
        });

        ImageButton buscarPorCodigo  = (ImageButton) findViewById(R.id.botonPaginaPrincipal4);
        buscarPorCodigo .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Buscar.class);
                startActivity(intent);
            }
        });

        ImageButton altaBajaUbicaciones  = (ImageButton) findViewById(R.id.botonPaginaPrincipal6);
        altaBajaUbicaciones .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AltaBajaUbicaciones.class);
                startActivity(intent);
            }
        });
    }
}
