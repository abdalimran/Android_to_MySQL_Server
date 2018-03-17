package com.github.imran.app2db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    // Creating EditText.
    EditText etId, etName, etMobile ;

    // Creating button;
    Button InsertButton;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String idHolder, nameHolder, mobileHolder ;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://10.0.3.2/app2db/add_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning ID's to EditText.
        etId = (EditText) findViewById(R.id.editTextId);
        etName = (EditText) findViewById(R.id.editTextName);
        etMobile = (EditText) findViewById(R.id.editTextMobile);

        // Assigning ID's to Button.
        InsertButton = (Button) findViewById(R.id.ButtonInsert);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        progressDialog = new ProgressDialog(MainActivity.this);

        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are Inserting Data");
                progressDialog.show();

                // Calling method to get value from EditText.
                GetValueFromEditText();

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing response message coming from server.
                                Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing error message if something goes wrong.
                                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();

                        // Adding All values to Params.
                        params.put("id", idHolder);
                        params.put("name", nameHolder);
                        params.put("mobile", mobileHolder);

                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }
        });

    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

        idHolder = etId.getText().toString().trim();
        nameHolder = etName.getText().toString().trim();
        mobileHolder = etMobile.getText().toString().trim();
    }

}
