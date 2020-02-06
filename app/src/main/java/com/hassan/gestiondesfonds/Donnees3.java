package com.hassan.gestiondesfonds;

import java.util.ArrayList;

//Une classe pour que les résultats retournés dans la TextView date_expenses soient tjrs à jour

public class Donnees3 { public static void setDepenses3(ArrayList<String[]> depenses3) {
    Depenses3 = depenses3;
}

    public static void setUpdated3(boolean updated) {
        Donnees3.updated3 = updated;
    }

    public static boolean updated3 = false;
    public static ArrayList<String[]> Depenses3;
}