package com.developer.tourismapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG ="RegisterActivity";
    EditText etName, etEmail, etPassword, etConfirmPassword, etContact;
    Button btnRegister;
    String gender="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_contact);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        //Gender drop down dynamic values addition
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    /**
     * Method to handle registration of the user.
     */
    private void registerUser() {
        final String email =etEmail.getText().toString();
        final String name= etName.getText().toString();
        final String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();
        final String contact = etContact.getText().toString();
        if(email.isEmpty() || name.isEmpty() || password.isEmpty() || confirm.isEmpty() || contact.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill in all the details!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }

        if(password.length()<6 || confirm.length()<6)
        {
            etPassword.setError("Passwords should be at least of six characters");
            return;
        }
        if(!password.equals(confirm))
        {
            etPassword.setError("Passwords should match");
            etConfirmPassword.setError("Passwords should match");
            return;
        }
        if(contact.length()!=10){
            etContact.setError("Contact must be of 10 digits only!");
            return;
        }
        if(!contact.matches("[0-9]+"))
        {
            etContact.setError("Contact shouldn't contain anything other than digits");
            return;
        }

        doRegister(name, email, password, contact);

    }

    private void doRegister(String name, final String email, String password, String contact) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", name);
        params.put("email", email);
        params.put("mobile",contact);
        params.put("password",password);
        String url = "https://cloud-5409.herokuapp.com/register";
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                String rc="", message="";
                try {
                    rc = response.getString("code");
                    message = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (rc.equals("200"))
                {
                    Toast.makeText(RegistrationActivity.this, "Registration Successful! ", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+ "Registration Success.");
                    Intent intent = new Intent(RegistrationActivity.this, TwoFactorAuthActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "onResponse: Registration Server failure: "+ message);
                    Toast.makeText(RegistrationActivity.this, "Server Message: "+message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.e(TAG, "errorResponse:" , error);
                Toast.makeText(RegistrationActivity.this, "Registration Failed! Try again.", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
