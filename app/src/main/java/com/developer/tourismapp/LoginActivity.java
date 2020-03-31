package com.developer.tourismapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvRegister;
    Button btnLogin;
    EditText etEmail, etPassword;
    private static final String TAG = "LoginActivity";

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_login:
                if(checkDetails())
                    signIn(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
        }
    }
    /**
     * Method to handle login validations.
     */
    private Boolean checkDetails()
    {
        String email, password;
        email=etEmail.getText().toString();
        password = etPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill in all the details!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        if(password.length()<6)
        {
            etPassword.setError("Passwords should be at least of six characters");
            return false;
        }
        return true;
    }

    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        makeLoginRequest(email, password);
    }

    private void makeLoginRequest(final String email, String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        String url = "https://cloud-5409.herokuapp.com/login";
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                String rc="", message="";
                try {
                    rc=response.getString("code");
                    message = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (rc.equals("200"))
                {
                    Toast.makeText(LoginActivity.this, "Transferring to Authentication Screen..", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+ "Login Success.");
                    Intent intent = new Intent(LoginActivity.this, TwoFactorAuthActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "onResponse: Server failure "+ message);
                    Toast.makeText(LoginActivity.this, "Server Message: "+message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.e(TAG, "errorResponse:" , error);
                Toast.makeText(LoginActivity.this, "Login Failed! Try again.", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail= (EditText)findViewById(R.id.et_email);
        etPassword= (EditText)findViewById(R.id.et_password);
        btnLogin =(Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    /**
     * Handles event when one preses back button.
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quit")
                .setMessage("Are you sure you want quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAndRemoveTask();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }

}
