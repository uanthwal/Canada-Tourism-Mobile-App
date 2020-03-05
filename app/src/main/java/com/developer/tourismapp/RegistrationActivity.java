package com.developer.tourismapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG ="RegisterActivity";
    EditText etName, etEmail, etPassword, etConfirmPassword;
    TextView tvDOB;
    int currentYear =0;
    Button btnRegister;
    Spinner spinnerGender;
    String gender="";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        spinnerGender=findViewById(R.id.spinner_gender);

        tvDOB= findViewById(R.id.tv_dob_select);
        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                currentYear = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistrationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        currentYear,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                String date=month+"/"+dayOfMonth+"/"+ year;
                if( (year+10) >= currentYear)
                {
                    tvDOB.setError("User must at least be 10 years old.");
                    Toast.makeText(getApplicationContext(),"User must at least be 10 years old!", Toast.LENGTH_LONG).show();
                }
                else
                    tvDOB.setText(date);
            }
        };
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
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(this);
    }

    /**
     * Method to handle registration of the user.
     */
    private void registerUser() {
        final String email =etEmail.getText().toString();
        final String name= etName.getText().toString();
        final String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();
        final String dob = tvDOB.getText().toString();

        if(email.isEmpty() || name.isEmpty() || password.isEmpty() || confirm.isEmpty())
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
        if(!dob.contains("/"))
        {
            tvDOB.setError("Select birth date");
            return;
        }


//        btnRegister.setEnabled(false);
//        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this, R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();
//        btnRegister.setEnabled(true);

        Intent intent = new Intent(RegistrationActivity.this, TwoFactorAuthActivity.class );
        intent.putExtra("name", name);
        intent.putExtra("email",email);
        intent.putExtra("password", password);
        intent.putExtra("dob", dob);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
