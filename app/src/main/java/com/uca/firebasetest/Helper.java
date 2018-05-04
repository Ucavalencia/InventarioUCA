package com.uca.firebasetest;

import com.google.firebase.database.DatabaseReference;

public class Helper {
    public String[] ubicacionesAT = new String[] { "Almacen", "Tunel", "Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
            "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
            "Mostrador60","Mostrador61","Mostrador62","Puerta 01","Puerta 03","Puerta 05","Puerta 07","Puerta 09","Puerta 11","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
            "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60","Transito T05" };

    public String[] ubicacionesNoAT = new String[] { "Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
            "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
            "Mostrador60","Mostrador61","Mostrador62","Puerta 01","Puerta 03","Puerta 05","Puerta 07","Puerta 09","Puerta 11","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
            "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60","Transito T05"};

    public String[] mostradores = new String[] { "Mostrador01","Mostrador02","Mostrador03","Mostrador04","Mostrador05","Mostrador06","Mostrador07","Mostrador08","Mostrador09","Mostrador10","Mostrador11","Mostrador12","Mostrador13","Mostrador14","Mostrador15","Mostrador16","Mostrador17","Mostrador18","Mostrador19","Mostrador20","Mostrador21","Mostrador22","Mostrador23","Mostrador24","Mostrador25","Mostrador26","Mostrador27",
            "Mostrador28","Mostrador29","Mostrador30","Mostrador31","Mostrador32","Mostrador33","Mostrador34","Mostrador35","Mostrador36","Mostrador37","Mostrador38","Mostrador39","Mostrador40","Mostrador41","Mostrador42","Mostrador43","Mostrador44","Mostrador45","Mostrador46","Mostrador47","Mostrador48","Mostrador49","Mostrador50","Mostrador51","Mostrador52","Mostrador53","Mostrador54","Mostrador55","Mostrador56","Mostrador57","Mostrador58","Mostrador59",
            "Mostrador60","Mostrador61","Mostrador62" };

    public String[] puertas = new String[] { "Puerta 01","Puerta 03","Puerta 05","Puerta 07","Puerta 09","Puerta 11","Puerta D13","Puerta D14","Puerta E15","Puerta E16","Puerta F17","Puerta F18",
            "Puerta R52","Puerta R53","Puerta R54","Puerta R55","Puerta R56","Puerta R57","Puerta R58","Puerta R59","Puerta R60" };

    public String[] transitos = new String[] { "Transito T05 " };

    public String[] almacenes = new String[] { "Almacen", "Tunel" };

    public String[] dispositivos = new String[] { "ATB", "BTP", "LSR", "BGR", "DCP", "Monitor", "Teclado", "Torre" };

    public String[] dispositivosM = new String[] { "ATB", "BTP", "LSR", "Monitor", "Teclado", "Torre" };

    public String[] dispositivosP = new String[] { "BGR", "DCP", "Monitor", "Teclado", "Torre" };

    public String[] dispositivosT = new String[] { "ATB", "DCP", "Monitor", "Torre" };

    // Devuelve una referencia a el lugar seleccionado
    public DatabaseReference seleccionarLugar(DatabaseReference ref, String item)
    {
        item = item.toLowerCase();

        if (item.equals("almacen")) // Almacen
            ref = ref.child("Almacen");
        else if (item.equals("tunel")) // Tunel
            ref = ref.child("Tunel");
        else if (item.substring(0, 1).equals("m")) // Mostradores
            ref = ref.child("Mostradores");
        else if (item.substring(0, 1).equals("t")) // Transitos
            ref = ref.child("Transitos");
        else // Puertas
            ref = ref.child("Puertas");

        return ref;
    }
}