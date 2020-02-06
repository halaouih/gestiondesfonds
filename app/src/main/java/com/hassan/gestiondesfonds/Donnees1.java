package com.hassan.gestiondesfonds;

import java.util.ArrayList;
//Une classe pour que les résultats retournés dans la TextView donnees soient tjrs à jour

public class Donnees1 {
    public static void setDepenses1(ArrayList<String[]> depenses1) {
        Depenses1 = depenses1;
    }

    public static void setUpdated1(boolean updated) {
        Donnees1.updated1 = updated;
    }

    public static boolean updated1 = false;
    public static ArrayList<String[]> Depenses1;
}
