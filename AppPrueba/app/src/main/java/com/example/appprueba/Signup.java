package com.example.appprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText username, password, confirmPassword;
    Button signup;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.txtSUsername);
        password = findViewById(R.id.txtSPassword);
        confirmPassword = findViewById(R.id.txtConfirm);
        signup = findViewById(R.id.btnSignup);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String cPass = confirmPassword.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(cPass)){
                    Toast.makeText(Signup.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                } else {
                    if(checkLength(pass, 6)){
                        if(checkUppercase(pass)){
                            if(pass.equals(cPass)){
                                if (checkLength(user, 8)){
                                    Boolean checkUser = DB.checkUsername(user);
                                    if (checkUser == false){
                                        Boolean insert = DB.insertData(user, pass);
                                        if (insert == true){
                                            Toast.makeText(Signup.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Signup.this, "Intenta de nuevo", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Signup.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Signup.this, "El usuario debe tener al menos 8 caracteres", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(Signup.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Signup.this, "La contraseña debe contener al menos 1 mayúscula", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Signup.this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public Boolean checkUppercase(String password){
        for (int i=0; i<password.length(); i++){
            if(Character.isUpperCase(password.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public Boolean checkLength(String text, int length){
        if(text.length() < length){
            return false;
        }
        return true;
    }
}