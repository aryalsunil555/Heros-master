package com.heros;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import MyAPI.MyAPI;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    private static final String BASE_URL="http://10.0.2.2:3000/";
    private EditText etName,etDescription;
    private Button btnUpload,btnShowDetails;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imgProfile = findViewById(R.id.imgPhoto);
        loadFromURL();

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        btnUpload = findViewById(R.id.btnUpload);
        btnShowDetails = findViewById(R.id.btnShowDetails);

        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }

            private  void RegisterUser(){
                String image = (" ");
                String id = (" ");
                String name = etName.getText().toString();
                String desc = etDescription.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put("name",name);
                map.put("desc",desc);

                User user = new User(image,id,name,desc);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                 MyAPI myAPI = retrofit.create(MyAPI.class);

                Call<Void> voidCall = myAPI.addHero(name, desc);

                voidCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(Register.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                        etName.setText(" ");
                        etDescription.setText(" ");

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(Register.this,"Error : " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
    private void StrictMode()
    {
        android.os.StrictMode.ThreadPolicy policy =
                new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

    private void loadFromURL()
    {
        StrictMode();
        try{
            String imgURl = "http://10.0.2.2:3000/uploads/download.jpeg";
            URL url = new URL(imgURl);
            imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        }
        catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
