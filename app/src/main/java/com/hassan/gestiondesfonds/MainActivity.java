package com.hassan.gestiondesfonds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;


import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    //Définition des éléments de l'activité
    Button btnConnect;
    Button sender;
    Button btnreports;
    Button gotocategory;
    TextView donnees;

    //Définition du lien de l'objet JSON contenant les résultats de la requete
    String MyURL = "http://192.168.56.1:8080/App/get";

    //Objet AsyncHttpClient pour communiquer avec le serveur
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connection des éléments de l'activité avec le code java
        btnConnect=findViewById(R.id.btnConnect);
        donnees = findViewById(R.id.text);
        sender = findViewById(R.id.sender);
        btnreports=findViewById(R.id.btnreports);
        gotocategory=findViewById(R.id.gotocategory);

        //définition de role du button btnConnect
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Patienter 20 s avant d'évoquer l'échec de connexion
                client.setTimeout(20000);

                //fonction pour récupérer les résultats
                get_today_expenses();

            }
        });

        //définition de role de button sender
        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Création d'un objet Intent qui aide d'aller de l'activité en cours à l'activité inserter
                Intent n = new Intent(getApplicationContext(), inserter.class);
                startActivity(n);

            }
        });

        //définition de role de button btnreports
        btnreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),date_reportschoice.class);
                startActivity(intent);
            }
        });

        //définition de role de button gotocategory
        gotocategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),categories.class);
                startActivity(intent);
            }
        });

    }

    //Méthode pour avoir les résulats de la part du serveur
    protected  void get_today_expenses(){

        //params nous aide au cas où le serveur a besoin d'un paramètre pour retourner le résultat
        RequestParams params;
        params = new RequestParams();
        try {

            //se connecter au lien
            client.post(getApplicationContext(),MyURL,params,new JsonHttpResponseHandler(){

                //au cas de réussite de connection

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Toast.makeText(getApplicationContext(),"dekhla",Toast.LENGTH_SHORT).show();
                    try {
                        //définition des paramètres dont on a besoin
                        int rows;
                        String note, category, amount;

                        //Création de JSONObject dans lequel on met le valeur de chaque noeud de response
                        JSONObject element;

                        //rows correspond au nbr qu'on a ajouté au fichier JSON retourné par la servlet
                        //Today_expenses on le prend on se basant sur le noeud "rows"
                        rows = Integer.parseInt(response.getString("rows"));

                        //ArrayList de String[] dans lequel on met toutes les lignes retournées
                        //dans response
                        ArrayList<String[]> depenses =  new ArrayList<String[]>();

                        //si rows!=0 dans il ya des lignes alors on fait le parcours
                        if(rows != 0){

                            for(int i = 0; i < rows; i++){

                                //element est un fichier JSON qui correspond à la valeur
                                //d'un noeud de response
                                element = response.getJSONObject(String.valueOf(i));

                                //On récupère les éléments
                                note = element.getString("note");
                                category = element.getString("category");
                                amount = element.getString("amount");

                                //On met les élement dans une liste
                                String[] row = new String[]{
                                        amount, category, note
                                };
                                //On ajoute la liste au tableau depenses
                                depenses.add(row);
                            }

                            // on transforme la tableau en une chaine de caractères bien structurée pour
                            //l'afficher
                            String resultat="";


                            for(int i=0;i<rows;i++) {
                                String row="";

                                for (int j = 0; j < depenses.get(i).length-1; j++) {

                                    row += depenses.get(i)[j] + " |*| ";
                                }

                                row+=depenses.get(i)[depenses.get(i).length-1];
                                resultat+=row+"\n";



                            }

                            //On affiche le resultat dans le TextView donnees
                            donnees.setText(resultat);


                            //Mise à jour
                            Donnees.setDepenses(depenses);
                            Donnees.setUpdated(true);

                        }
                        else{// Si rows == 0, Server error
                            Log.d("hff", "sjkdfh");
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                        }


                    }catch(Exception e){Log.d("errrr", "err");}


                }
                //au cas d'échec de la connection

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(getApplicationContext(),"Submit Failure: Check Connection",Toast.LENGTH_SHORT).show();
                    Log.d("hdfydt", "hdh");
                }

            });
        }
        catch(Exception e){
            Log.d("PostError", e.toString());
        }


    }

}
