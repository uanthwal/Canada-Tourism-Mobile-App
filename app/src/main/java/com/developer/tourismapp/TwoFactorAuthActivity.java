package com.developer.tourismapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TwoFactorAuthActivity extends AppCompatActivity {

    private static final String TAG = "TwoFactorAuthActivity";
    TextView tvEmail;
    Button btnverify;
    EditText etOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2fa);
        tvEmail = findViewById(R.id.tv_email);
        btnverify = findViewById(R.id.btn_verify);
        etOTP = findViewById(R.id.et_otp);
        tvEmail.setText(AppGlobalVars.EMAIL_ID);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etOTP.getText().toString().equals(""))
                    validateOTP(tvEmail.getText().toString(), etOTP.getText().toString());
            }
        });
    }

    private void validateOTP(final String emailid, String otp) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", emailid);
        params.put("otp", otp);
        String url = AppConstants.VERIFY_OTP;
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                String rc = "", sessionid = "", message = "";
                try {
                    rc = response.getString("code");
                    sessionid = response.getString("session_id");
                    message = response.getString("message");
                    JSONObject jsonObject = response.getJSONObject("data");
                    AppGlobalVars.USER_NAME = jsonObject.getString("username");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (rc.equals("200")) {
                    Log.d(TAG, "onResponse: " + "Auth Success.");
                    Intent intent = new Intent(TwoFactorAuthActivity.this, BookTicketActivity.class);
                    AppGlobalVars.SESSION_ID = sessionid;
                    startActivity(intent);
                } else {
                    Log.d(TAG, "onResponse: Auth Server failure " + message);
                    Toast.makeText(TwoFactorAuthActivity.this, "Server Message: " + message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.e(TAG, "errorResponse:", error);
                Toast.makeText(TwoFactorAuthActivity.this, "Auth Failed! Try again.", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
