package com.example.gross.mobileclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gross.mobileclient.R;
import com.example.gross.mobileclient.controller.RestManager;
import com.example.gross.mobileclient.model.RegistrationResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gross.mobileclient.ui.MainActivity.token;

/**
 * Created by Gross on 18.01.2017.
 */

public class LoginActivity extends AppCompatActivity {
    private RestManager mManager;

    public EditText username, password;
    public TextView goRegistrAcc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editLogin);
        password = (EditText) findViewById(R.id.editPassword);
        goRegistrAcc = (TextView) findViewById(R.id.textViewAccountCreate);
    }

    public void onLoginBtnClick(View view) {
        mManager = new RestManager();

        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username.getText().toString());
        map.put("password",password.getText().toString());

        Call<RegistrationResponse> call = mManager.getApiService().loginUser(map);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.body().isSuccess()) {
                    token=response.body().getToken();
                    Toast toast = Toast.makeText(getApplicationContext(), response.message() + "\nWellcome, "+username.getText().toString(), Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent;
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter correct data please", Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {

            }
        });
    }

    public void onTextRegistrationClick(View view) {
        Intent intent;
        intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}
