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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QRScanner extends AppCompatActivity implements View.OnClickListener {

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
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);



        buttonScan = (Button) findViewById(R.id.buttonScan);



        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);

        progressDialog = new ProgressDialog(this);
        Email = findViewById(R.id.Email);

        ET_POLENUMBER = findViewById(R.id.pole_number);
        ET_POLELOCATATION = findViewById(R.id.pole_location);

        s1 = (Spinner) findViewById(R.id.spinner_complaint);

        Email.setText(SharedPrefManager.getInstance(this).getEmail());
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

             String com = String.valueOf(s1.getSelectedItem());
 complaint = com;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });




        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                 ET_POLENUMBER.setText(obj.getString("name"));
                ET_POLELOCATATION.setText(obj.getString("address"));


                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

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

                    Toast.makeText(QRScanner.this, response, Toast.LENGTH_LONG).show();


                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();

                    Toast.makeText(QRScanner.this, error.toString(), Toast.LENGTH_LONG).show();
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


