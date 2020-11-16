package com.example.cadena_de_favores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;

public class Login extends AppCompatActivity {

    EditText etEmail,etPass;
    RequestQueue requestQueue;
    Utilidades utilidades = new Utilidades(Login.this);
    //Submit submit = new Submit("https://cadefavores.000webhostapp.com/scripts/login/", Login.this);
    CookieManager cookieManager = CookieManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(utilidades.existe_cookie_de_id())
            cambiar_activity(Home.class);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);

        ((TextView) findViewById(R.id.tvRegistrarse)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar_activity(Registro.class);
            }
        });

        findViewById(R.id.btnIniciar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEmail.getText().toString().equals("") && !etPass.getText().toString().equals(""))
                    if (utilidades.es_un_email(etEmail.getText().toString()))
                        logUser("https://cadefavores.000webhostapp.com/scripts/registro/login.php?email=" + etEmail.getText() + "&pass=" + etPass.getText());
                    else
                        utilidades.alert_short("Debe ingresar un mail válido");
                else
                    utilidades.alert("Debe llenar todos los campos");
            }
        });
    }

    @Override
    public void onBackPressed(){

    }

    private void logUser(String url){
        new Submit(Login.this).JSONArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0){
                    try {
                        cookieManager.setCookie("id", response.getJSONObject(0).getString("id_usuario"));

                        cambiar_activity(Home.class);
                    } catch (JSONException e) {
                        utilidades.alert("Algo salió mal, intentelo de nuevo mas tarde");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    etPass.setText("");
                }
            }
        });
    }

    private void cambiar_activity(Class a){
        startActivity(new Intent(getApplicationContext(), a));
        finish();
    }
}
