package com.example.cadena_de_favores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.webkit.CookieManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class Perfil extends AppCompatActivity {
    ImageView iVboton_opciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final CookieManager cookieManager = CookieManager.getInstance();

        ArrayList<HashMap<String, String>> datos_perfil;

        try {
            if(cookieManager.getCookie(/*"https://cadefavores.000webhostapp.com/"*/"id") != null)
                getDatosPerfil(cookieManager.getCookie("id"));//imprime los datos del usuario de la base de datos en el perfil
        }catch(Exception ex){
            new Utilidades(Perfil.this).alert("Ocurrio un error innesperado.\nVuelva a loguearse.");
        }

        //Boton que despliega el menú lateral
        findViewById(R.id.iVboton_opciones).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View iLmenu_desplegable = (View) findViewById(R.id.iLmenu_desplegable);
                //View sVinfo_perfil = (View) findViewById(R.id.sVinfo_perfil);

                ViewGroup.LayoutParams layoutParams_menu_desplegable = iLmenu_desplegable.getLayoutParams();
                if(iLmenu_desplegable.getVisibility() == View.GONE) {
                    int tamaño_menu_desplegable = new Utilidades().dpToPx(230);
                    layoutParams_menu_desplegable.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    iLmenu_desplegable.setVisibility(View.VISIBLE);
                    iLmenu_desplegable.setLayoutParams(layoutParams_menu_desplegable);
                    //sVinfo_perfil.setPadding(0, 0, tamaño_menu_desplegable, 0);
                }else{
                    layoutParams_menu_desplegable.width = 0;
                    iLmenu_desplegable.setLayoutParams(layoutParams_menu_desplegable);
                    //sVinfo_perfil.setPadding(0, 0, 0, 0);
                    iLmenu_desplegable.setVisibility(View.GONE);
                }
            }
        });

        //Inicio opciones del menú desplegable

        //Opcion de configuracion
        findViewById(R.id.tVconfig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiar_activity(Config.class);
                new Utilidades(Perfil.this).alert("Opcion inhabilitada por el momento.");
            }
        });
        //Opcion de ver favores propios
        findViewById(R.id.tVmis_favores).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar_activity(MisFavores.class);
                //new Utilidades(Perfil.this).alert("Opcion inhabilitada por el momento.");
            }
        });
        //Opcion de pedir un favor
        findViewById(R.id.tVpedir_favor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiar_activity(Pedir_favor.class);
                //new Utilidades(Perfil.this).alert("Opcion inhabilitada por el momento.");
            }
        });
        //Opcion de cerrar sesión
        findViewById(R.id.tVcerrar_sesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookieManager.removeAllCookie();
                cambiar_activity(Login.class);
            }
        });

        //Fin opciones del menu desplegable
    }

    private void getDatosPerfil(String id){
        new Submit(Perfil.this).JSONArrayRequest("https://cadefavores.000webhostapp.com/scripts/perfil/datos_del_perfil.php?id=" + id,
            new Response.Listener<JSONArray>(){
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> campos = new ArrayList<>();
                    campos.add("nombres");
                    campos.add("apellidos");
                    campos.add("ocupacion");
                    campos.add("nivel_karmico");
                    campos.add("cant_favores_pedidos");
                    campos.add("cant_favores_cumplidos");

                    ArrayList<HashMap<String, String>> datos_del_usuario = new Utilidades().conertir_JSONArray_ArrayHashMap(response, campos);
                    ((TextView) findViewById(R.id.tVnombresyapellidos)).setText(datos_del_usuario.get(0).get("nombres").concat(" ".concat(datos_del_usuario.get(0).get("apellidos"))));
                    ((TextView) findViewById(R.id.tVocupacion)).setText(datos_del_usuario.get(0).get("ocupacion"));
                    ((ProgressBar) findViewById(R.id.pBkarma)).setProgress(Integer.parseInt(datos_del_usuario.get(0).get("nivel_karmico")));
                    ((TextView) findViewById(R.id.tVnum_favores_pedidos)).setText(datos_del_usuario.get(0).get("cant_favores_pedidos"));
                    ((TextView) findViewById(R.id.tVnum_favores_cumplidos)).setText(datos_del_usuario.get(0).get("cant_favores_cumplidos"));
                }
            }
        );
    }

    private void cambiar_activity(Class a) {
        startActivity(new Intent(getApplicationContext(), a));
        finish();
    }
}
