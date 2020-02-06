package com.hassan.gestiondesfonds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class insertnewcategory extends AppCompatActivity {

    //Définition du lien qui effectue l'insertion
    String MyURL = "http://192.168.56.1:8080/App/New_Category";

    //Objet AsyncHttpClient pour communiquer avec le serveur
    AsyncHttpClient client = new AsyncHttpClient();

    //Définition des éléments
    EditText categoryname;
    Button btninsertnewcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertnewcategory);

        //connecter les éléments au code java
        categoryname = findViewById(R.id.categoryname);
        btninsertnewcategory = findViewById(R.id.btninsertnewcategory);

        //définition de role de button btninsertnewcategory
        btninsertnewcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_params1();
            }
        });
    }

    //méthode pour envoyer les paramètres
    protected void send_params1() {

        //objet RequestParams pour recevoir les paramètre entrés par l'utilisateur
        RequestParams params;
        params = new RequestParams();

        //insertion des paramètres
        params.put("categoryname", categoryname.getText().toString());
        try {

            //se connecter au lien
            client.post(getApplicationContext(), MyURL, params, new JsonHttpResponseHandler() {

                //au cas de succès
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);


                    try {
                        String state = response.getString("state");
                        if ("success".equals(state)) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                    }
                }

                //au cas d'échec
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(getApplicationContext(), "Submit Failure: Check Connection", Toast.LENGTH_SHORT).show();
                    Log.d("hdfydt", "hdh");
                }
            });
        } catch (Exception e) {
            Log.d("PostError", e.toString());
        }
    }
}
