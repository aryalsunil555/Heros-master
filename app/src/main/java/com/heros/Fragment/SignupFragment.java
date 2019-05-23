package com.heros.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.heros.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener  {
    private EditText txtfname,txtlname,txtusername,txtpassword,txtrepassword;
    private Button btnregister;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_signup, container, false);
        txtfname=view.findViewById(R.id.txtfname);
        txtlname=view.findViewById(R.id.txtlname);
        txtusername=view.findViewById(R.id.txtusername);
        txtpassword=view.findViewById(R.id.txtpassword);
        txtrepassword=view.findViewById(R.id.txtrepassword);

        btnregister=view.findViewById(R.id.btnregister);
        btnregister.setOnClickListener(this);
        return  view;

    }

    @Override
    public void onClick(View v) {
        if(validate()){
            signup();
        }

    }
    private void signup(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("firstname",txtfname.getText().toString());
        editor.putString("lastname",txtlname.getText().toString());
        editor.putString("username",txtusername.getText().toString());
        editor.putString("password",txtpassword.getText().toString());
        editor.commit();

        Toast.makeText(getActivity(),"Successfull ",Toast.LENGTH_SHORT).show();
    }
    private boolean validate(){
        boolean checkvalidate=true;
        if(TextUtils.isEmpty(txtfname.getText().toString())){
            txtfname.setError("First Name is required");
            txtfname.requestFocus();
            checkvalidate=false;
        }
        if(TextUtils.isEmpty(txtlname.getText().toString())){
            txtlname.setError("Last Name is required");
            txtlname.requestFocus();
            checkvalidate=false;
        }
        if(TextUtils.isEmpty(txtusername.getText().toString())){
            txtusername.setError("Username is required");
            txtusername.requestFocus();
            checkvalidate=false;
        }
        if(TextUtils.isEmpty(txtpassword.getText().toString())){
            txtpassword.setError("Password is required");
            txtpassword.requestFocus();
            checkvalidate=false;
        }
        if(TextUtils.isEmpty(txtrepassword.getText().toString())){
            txtrepassword.setError("Password is required");
            txtrepassword.requestFocus();
            checkvalidate=false;
        }
        String pwd=txtpassword.getText().toString();
        String repwd=txtrepassword.getText().toString();
        if(pwd.equals(repwd)){
            checkvalidate=true;
        }else{
            txtpassword.setError("Invalid Match");
            txtrepassword.setError("Invalid Match");
            txtpassword.requestFocus();
            checkvalidate=false;
        }
        return  checkvalidate;

    }
}
