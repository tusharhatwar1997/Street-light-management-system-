package com.hell.hell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MapComplaint extends AppCompatActivity
{


    String register_url = "https://tushardemoprojects.000webhostapp.com/Android/scripts/complaint.php";
    public static final String KEY_Email = "email";
    public static final String KEY_PoleNumber = "polenumber";
    public static final String KEY_PoleLocation = "polelocation";
    public static final String KEY_ComplaintType = "complaint";
    Spinner s1;
    public  String complaint ;

    private TextView ET_POLENUMBER;
    private TextView ET_POLELOCATATION;

    private ProgressDialog progressDialog;
    private TextView Email;
    private Button buttonScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_complaint);

        buttonScan = (Button) findViewById(R.id.buttonScan);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);

        progressDialog = new ProgressDialog(this);
        Email = findViewById(R.id.Email);

        ET_POLENUMBER = findViewById(R.id.pole_number);
        ET_POLELOCATATION = findViewById(R.id.pole_location);


        Intent intent = getIntent();
        String PoleNumber = intent.getStringExtra("Key_PoleNumber");
        String PoleLocation = intent.getStringExtra("Key_PoleLocation");
        ET_POLENUMBER.setText(PoleLocation);
        ET_POLELOCATATION.setText(PoleNumber);

        s1 = (Spinner) findViewById(R.id.spinner_complaint);

        Email.setText(SharedPrefManager.getInstance(this).getEmail());
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String com = String.valueOf(s1.getSelectedItem());
                complaint = com;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });}





    public  void submitComplaint(View view){
        progressDialog.setMessage("please wait.....");
        progressDialog.show();

        final String email = Email.getText().toString().trim();
        final String polenumber = ET_POLENUMBER.getText().toString().trim();
        final String polelocation = ET_POLELOCATATION.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(MapComplaint.this, response, Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(MapComplaint.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_Email, email);
                params.put(KEY_PoleNumber,polenumber);
                params.put(KEY_PoleLocation, polelocation);
                params.put(KEY_ComplaintType, complaint);
                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }





}

