package com.uca.firebasetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

/**
 * Created by Toni on 18/04/2018.
 */

public class Manual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        String[] ubicaciones = new String[] {"Almacén","Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
                "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
                "Mostrador60","Mostrador61","Mostrador62","Puerta A1/A2","Puerta A3/A4","Puerta B5/B6","Puerta B7/B8","Puerta C9/C10","Puerta C11/C12","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
                "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60","Tránsito T05", "Túnel"};

    }
}
