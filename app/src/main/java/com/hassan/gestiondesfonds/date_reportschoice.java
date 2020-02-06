package com.hassan.gestiondesfonds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class date_reportschoice extends AppCompatActivity {
    //Définition des éléments de l'activité
    Button btnshowreport;
    TextView date_expenses;
    EditText date_choice;
    Button btnalltime_report;
    //Définition des liens des objets JSON contenants les résultats des requetes requete
    String MyURL="http://192.168.56.1:8080/App/Date_Reports";
    String MyURL2="http://192.168.56.1:8080/App/alltime_report";

    //
    AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_reportschoice);

        //Connection des éléments de l'activité avec le code java
        btnshowreport=findViewById(R.id.btnshowreport);
        date_choice=findViewById(R.id.date_choice);
        date_expenses=findViewById(R.id.date_expenses);
        btnalltime_report=findViewById(R.id.btnalltime_report);


        btnshowreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.setTimeout(20000);
                send_params1();
            }
        });
        btnalltime_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.setTimeout(20000);
                get_alltime_report();
            }
        });
    }

    //Méthode pour récupérer les dépenses d'une date déterminée
    protected  void send_params1(){

        RequestParams params;
        params = new RequestParams();

        //on met la date entrée par l'utilisateur dans la TextView date_choice sous le nom ""date1
        params.put("date1",date_choice.getText().toString());

        try {

            //se connecter au lien MyURL
            client.post(getApplicationContext(),MyURL,params,new JsonHttpResponseHandler(){

                //au cas de réussite de connection
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        //définition des paramètres dont on a besoin
                        int rows;
                        String note, category, amount;

                        //Création de JSONObject dans lequel on met le valeur de chaque noeud de response
                        JSONObject element;


                        //rows correspond au nbr qu'on a ajouté au fichier JSON retourné par la servlet
                        //Date_reports on le prend on se basant sur le noeud "rows"
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
                                depenses.add(row);
                            }

                            // on transforme la tableau en une chaine de caractères bien structurée pour
                            //l'afficher
                            String resultat="";
                            for(int i=0;i<rows;i++){
                                String row="";
                                for(int j=0;j<depenses.get(i).length-1;j++){
                                    row+=depenses.get(i)[j]+" |*| ";

                                }
                                row+=depenses.get(i)[depenses.get(i).length-1];
                                resultat+=row+"\n";
                            }

                            //On affiche le resultat dans le TextView date_expenses
                            date_expenses.setText(resultat);

                            //Mise à jour
                            Donnees1.setDepenses1(depenses);
                            Donnees1.setUpdated1(true);

                        }
                        else{// Si rows == 0, Server error
                            Log.d("hff", "sjkdfh");
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                        }


                    }catch(Exception e){Log.d("errrr", "err");}

                }

                //au cas d'échec de connection

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


    //Méthode pour récupérer toutes les dépenses
    protected  void get_alltime_report(){

        //Il n ya pas de paramètres mais c obligatoire car c un paramètre de la méthode post
        RequestParams params;
        params = new RequestParams();
        try {

            //Connection au lien MyURL2
            client.post(getApplicationContext(),MyURL2,params,new JsonHttpResponseHandler(){

                //au cas de succés
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Toast.makeText(getApplicationContext(),"dekhla",Toast.LENGTH_SHORT).show();
                    try {
                        //définition des paramètres dont on a besoin
                        int rows;
                        String note, category, amount,date;

                        //Création de JSONObject dans lequel on met le valeur de chaque noeud de response
                        JSONObject element;


                        //rows correspond au nbr qu'on a ajouté au fichier JSON retourné par la servlet
                        //alltime_report on le prend on se basant sur le noeud "rows"
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
                                date=element.getString("date");

                                //On met les élement dans une liste
                                String[] row = new String[]{
                                        amount, category, note,date
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
                            //On affiche le resultat dans le TextView date_expenses
                            date_expenses.setText(resultat);

                            //Mise à jour
                            Donnees3.setDepenses3(depenses);
                            Donnees3.setUpdated3(true);

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
