package com.example.cadena_de_favores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;


public class MisFavores extends AppCompatActivity {
    private RecyclerView recyclerviewfavor;

    CookieManager cookieManager = CookieManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misfavores);

        if(new Utilidades(MisFavores.this).existe_cookie_de_id()){
            recyclerviewfavor = (RecyclerView) findViewById(R.id.viewFavor);
            recyclerviewfavor.setLayoutManager(new LinearLayoutManager(this));

            mostrarFavores();

            findViewById(R.id.ivButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent (Home.this, CLASEDESTINO.class));
                }
            });

            findViewById(R.id.ivButton3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(Home.this, CLASEDESTINO.class));
                }
            });
        }else
            cambiar_activity(Login.class);
    }

    public void mostrarFavores(){
        new Submit("https://cadefavores.000webhostapp.com/scripts/", MisFavores.this).JSONArrayRequest("ListarMisFavores.php?idusuario=".concat(cookieManager.getCookie("id")),
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;

                        List<Modelofavor> favor = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++){
                            try{
                                jsonObject = response.getJSONObject(i);
                                favor.add(new Modelofavor(jsonObject.getString("nombres"), jsonObject.getString("fecha_hora_pedido"), jsonObject.getString("titulo"), jsonObject.getString("descripcion")));
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
