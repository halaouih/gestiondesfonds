package com.hassan.gestiondesfonds;

import java.util.ArrayList;

//Une classe pour que les résultats retournés dans la TextView donnees soient tjrs à jour

public class Donnees {

    public static void setDepenses(ArrayList<String[]> depenses) {
        Depenses = depenses;
    }

    public static void setUpdated(boolean updated) {
        Donnees.updated = updated;
    }

    public static boolean updated = false;
    public static ArrayList<String[]> Depenses;
}
