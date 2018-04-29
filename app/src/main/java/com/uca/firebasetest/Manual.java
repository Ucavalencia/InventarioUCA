package com.uca.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manual extends AppCompatActivity {

    private Spinner spinner;
    static final int REQUEST = 1;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText);

        String[] ubicaciones = new String[] {"Almacén","Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
                "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
                "Mostrador60","Mostrador61","Mostrador62","Puerta A1/A2","Puerta A3/A4","Puerta B5/B6","Puerta B7/B8","Puerta C9/C10","Puerta C11/C12","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
                "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60","Tránsito T05", "Túnel"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ubicaciones));
    }

    public void startScanner(View view)
    {
        // Start scanner here
        Intent intent = new Intent(getApplicationContext(), QRScanner.class);
        startActivityForResult(intent, REQUEST);
        Toast.makeText(this, intent.getStringExtra("codeResult"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                editText.setText(data.getStringExtra("result"));

            }
        }
    }

    public void buscarCodigo(View view)
    {

    }
}
