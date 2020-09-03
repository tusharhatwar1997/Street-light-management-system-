package com.hell.hell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    //public static final String KEY_LEMAIL ="email";
   // public static final String KEY_LPASSWORD ="password";
    private EditText ET_LEMAIL;
    private EditText ET_LPASSWORD;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        ET_LEMAIL = findViewById(R.id.et_lemail);
        ET_LPASSWORD = findViewById(R.id.et_lpassword);

if(SharedPrefManager.getInstance(this).isLogIn()){
    startActivity(new Intent(this,Home.class));
finish();
}
    }
    public void onLogin(View view)

    {
        LoginUser();
    }
    public void LoginUser(){



        progressDialog.setMessage("please wait.....");
        progressDialog.show();
        final String email = ET_LEMAIL.getText().toString().trim();
        final String password = ET_LPASSWORD.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("email"),
                                        jsonObject.getLong("mobile_no")
                                        );

                                startActivity(new Intent(getApplicationContext(),Home.class));
                                finish();

                            }else
                                {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                            Toast.LENGTH_SHORT).show();
                                }



                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("email",email);
                map.put("password",password);
                return map;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
    public void onRegister(View view){

        Intent intent = new Intent(this,Register.class);

        startActivity(intent);

    }
}


