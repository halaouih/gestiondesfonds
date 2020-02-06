package com.hassan.gestiondesfonds;

import java.util.ArrayList;

//Une classe pour que les résultats retournés dans la TextView categoryview soient tjrs à jour

public class Donnees2 {
    public static void setCategories(ArrayList<String[]> categories) {
        Categories= categories;
    }

    public static void setUpdated2(boolean updated) {
        Donnees2.updated2 = updated;
    }

    public static boolean updated2 = false;
    public static ArrayList<String[]> Categories;
}
