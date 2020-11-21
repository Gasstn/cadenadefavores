package com.example.cadena_de_favores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerviewfavor;

    CookieManager cookieManager = CookieManager.getInstance();

    //TextView tvboton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(new Utilidades().existe_cookie_de_id()) {
            recyclerviewfavor = (RecyclerView) findViewById(R.id.viewFavor);
            recyclerviewfavor.setLayoutManager(new LinearLayoutManager(this));

            mostrarFavores();

            findViewById(R.id.ivPerfil).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiar_activity(Perfil.class);
                }
            });
            findViewById(R.id.ivHome).setBackgroundColor(getResources().getColor(R.color.gris2));
        }else
            cambiar_activity(Login.class);
    }

    public void mostrarFavores(){
        new Submit("https://cadefavores.000webhostapp.com/scripts/", Home.this).JSONArrayRequest("listarFavor.php?idusuario=".concat(cookieManager.getCookie("id")),
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;

                        List<Modelofavor> favor = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++){
                            try{
                                jsonObject = response.getJSONObject(i);
                                favor.add(new Modelofavor(jsonObject.getString("nombres"), jsonObject.getString("fecha_hora_pedido"), jsonObject.getString("titulo"), jsonObject.getString("descripcion"), jsonObject.getString("nro_tel")));
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        recyclerviewfavor.setAdapter(new RecyclerViewAdaptador(favor));
                    }
                });
    }

    private void cambiar_activity(Class a){
        startActivity(new Intent(getApplicationContext(), a));
    }
}
