package com.example.cadena_de_favores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;


public class Registro extends AppCompatActivity {


    EditText etEmail,etNombre,etApellido,etOcupacion,etPass,etPass2,etFechaNac;
    TextView tvIniciar;
    Button btnRegistrarse;

    Utilidades utilidades = new Utilidades(Registro.this);
    Submit submit = new Submit("https://cadefavores.000webhostapp.com/scripts/registro/", Registro.this);
    CookieManager cookieManager = CookieManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        if(utilidades.existe_cookie_de_id())
            cambiar_activity(Home.class);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etOcupacion = (EditText) findViewById(R.id.etOcupacion);
        etPass = (EditText) findViewById(R.id.etPass);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        etFechaNac = (EditText) findViewById(R.id.etFechaNac);

        findViewById(R.id.btnRegistrarse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNombre.getText().toString().equals("") && !etApellido.getText().toString().equals("") && !etOcupacion.getText().toString().equals("") && !etFechaNac.getText().toString().equals("") && !etEmail.getText().toString().equals("") && !etPass.getText().toString().equals("") && !etPass2.getText().toString().equals(""))
                    if (utilidades.es_un_email(etEmail.getText().toString()))
                        emailRegistrado("confirmarMail.php?email=" + etEmail.getText().toString());
                    else
                        utilidades.alert_short("Debe ingresar un mail válido");
                else
                    utilidades.alert_short("Debe llenar todos los campos");
            }
        });
        ((TextView) findViewById(R.id.tvIniciar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar_activity(Login.class);
            }
        });
    }

    @Override
    public void onBackPressed(){
        cambiar_activity(Login.class);
    }

    public void registrarUser(String url, Map<String, String> datos){
        submit.StringRequest_op(url, "Usuario registrado correctamente", "Algo salió mal, intentelo nuevamente mas tarde", datos);
        cambiar_activity(Login.class);
    }

    /*private String getNroTel(){
        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String nroTel = tMgr.getLine1Number();
        return nroTel;
    }*/

    private void emailRegistrado(String url){
        submit.JSONArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0)
                    Toast.makeText(Registro.this, "Este email ya está registrado.", Toast.LENGTH_SHORT).show();
                else{
                    if (etPass.getText().toString().equals(etPass2.getText().toString())){
                        Map<String,String> parametros = new HashMap<String, String>();
                        parametros.put("nombres", etNombre.getText().toString());
                        parametros.put("apellidos", etApellido.getText().toString());
                        parametros.put("fechaNac", etFechaNac.getText().toString());
                        parametros.put("email", etEmail.getText().toString());
                        parametros.put("pass", etPass.getText().toString());
                        parametros.put("ocupacion", etOcupacion.getText().toString());
                        parametros.put("nro_tel", "0");

                        registrarUser("registrar.php", parametros);
                    }else
                        Toast.makeText(Registro.this, "No repitió la contraseña correctamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void cambiar_activity(Class a){
        startActivity(new Intent(getApplicationContext(), a));
        finish();
    }
}
