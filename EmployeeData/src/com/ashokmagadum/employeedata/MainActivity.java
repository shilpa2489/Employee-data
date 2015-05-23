package com.ashokmagadum.employeedata;

import java.util.ArrayList;

import java.util.List;

import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.AsyncTask;

import android.os.Bundle;

import android.util.Log;

import android.view.View;


import android.widget.Button;

import android.widget.EditText;

public class MainActivity extends Activity {

    

    // Progress Dialog

    private ProgressDialog pDialog;

 
// Create the object of JsonParser class

    JSONParser jParser = new JSONParser();

  
    EditText inputName;

    EditText inputId;

 

    // url to create send data. This contains the ip address of my machine on which the local server is running. You will write the IP address of your machine

      private static String url = "http://10.0.2.2:8888/emp/storeing.php";

 

    // JSON Node names

    private static final String TAG_SUCCESS = "success";

 

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

 

        // Edit Text

        inputName = (EditText) findViewById(R.id.inputName);

        inputId = (EditText) findViewById(R.id.inputId);

 

        // Create button

        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

 

        // button click event

        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

 
            @Override

            public void onClick(View view) {

                // creating new product in background thread

                new CreateNewId().execute();

            }

        });

    }

 

    class CreateNewId extends AsyncTask<String, String, String> {

 

        @SuppressWarnings("unused")

              @Override

        protected void onPreExecute() {

            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);

            pDialog.setMessage("Creating Data..");

            pDialog.setIndeterminate(false);

            pDialog.setCancelable(true);

            pDialog.show();

        }

 

        protected String doInBackground(String... args) {

            String name = inputName.getText().toString();

            String id = inputId.getText().toString();

          

            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("name", name));

            params.add(new BasicNameValuePair("id", id));

 
            JSONObject json = jParser.makeHttpRequest(url,

                    "POST", params);

          

            Log.d("Create Response", json.toString());

 
            try {

                int success = json.getInt(TAG_SUCCESS);

 

                if (success == 1) {

         
                      finish();

                  
                } else {       

                }

            } catch (JSONException e) {

                e.printStackTrace();

            }

            return null;

        }


        protected void onPostExecute(String file_url) {

            // dismiss the dialog once done

            pDialog.dismiss();

        }

 

    }

}

 