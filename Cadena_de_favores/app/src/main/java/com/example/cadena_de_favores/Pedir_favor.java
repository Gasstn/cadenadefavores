package com.example.cadena_de_favores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class Pedir_favor extends AppCompatActivity {
    EditText et1,et2,et3;
    CookieManager cookieManager = CookieManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_favor);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);

        findViewById(R.id.bvolver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar_activity(Perfil.class);
            }
        });
    }

    public void ejecutarservicios(View v){
        Map<String,String> datos = new HashMap<>();
        datos.put("id_usuario",cookieManager.getCookie("id"));
        datos.put("titulo",et1.getText().toString());
        datos.put("descripcion",et3.getText().toString());
        datos.put("coordenadas",et2.getText().toString());
        datos.put("estado","0");

        new Submit("https://cadefavores.000webhostapp.com/scripts/", Pedir_favor.this).StringRequest("nuevofavor.php", datos);
    }

    private void cambiar_activity(Class a){
        startActivity(new Intent(getApplicationContext(), a));
    }
}