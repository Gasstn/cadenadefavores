package com.example.cadena_de_favores;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Registro extends AppCompatActivity {
    View form_datos_1, form_datos_2;

    EditText etEmail, etNombre, etApellido, etPass, etPass2, etFechaNac, etNro_tel;
    Spinner sOcupaciones;

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
        sOcupaciones = (Spinner) findViewById(R.id.sOcupaciones);
        etPass = (EditText) findViewById(R.id.etPass);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        etFechaNac = (EditText) findViewById(R.id.etFechaNac);
        etNro_tel = (EditText) findViewById(R.id.etNro_tel);

        form_datos_1 = (View) findViewById(R.id.sVdatos_1);
        form_datos_2 = (View) findViewById(R.id.sVdatos_2);

        setearSpinnerOcupacion("../get_ocupaciones.php");

        etFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        final String selectedDate = year + "-" + (month+1) + "-" + day;
                        etFechaNac.setText(selectedDate);
                    }
                }).show(getSupportFragmentManager(), "datePicker");
            }
        });
        etFechaNac.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return true;
            }
        });

        findViewById(R.id.btnSiguiente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form_datos_1.setVisibility(View.GONE);
                form_datos_2.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btnVolver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form_datos_2.setVisibility(View.GONE);
                form_datos_1.setVisibility(View.VISIBLE);
                utilidades.alert(sOcupaciones.getSelectedItem().toString());
            }
        });

        findViewById(R.id.btnRegistrarse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNombre.getText().toString().equals("") && !etApellido.getText().toString().equals("") && !sOcupaciones.getSelectedItem().toString().equals("") && !etFechaNac.getText().toString().equals("") && !etEmail.getText().toString().equals("") && !etPass.getText().toString().equals("") && !etPass2.getText().toString().equals("") && !sOcupaciones.getSelectedItem().toString().equals("Selecciones una ocupacion:"))
                    if (utilidades.es_un_email(etEmail.getText().toString()))
                        emailRegistrado("confirmarMail.php?email=" + etEmail.getText().toString());
                    else
                        utilidades.alert_short("Debe ingresar un mail válido");
                else
                    utilidades.alert_short("Debe llenar todos los campos");
            }
        });

        findViewById(R.id.tvIniciar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar_activity(Login.class);
            }
        });
        findViewById(R.id.tvIniciar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiar_activity(Login.class);
            }
        });
    }

    private void setearSpinnerOcupacion(String url) {
        submit.JSONArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> ocupaciones = new ArrayList<>();
                ocupaciones.add("Selecciones una ocupacion:");

                for(int x = 0;x < response.length();x++){
                    try {
                        ocupaciones.add(response.getJSONObject(x).getString("ocupacion"));
                    } catch (JSONException e) {
                        utilidades.alert(e.getMessage());
                    }
                }

                sOcupaciones.setAdapter(new ArrayAdapter(Registro.this, android.R.layout.simple_spinner_item, ocupaciones));
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
                        parametros.put("ocupacion", sOcupaciones.getSelectedItem().toString());
                        parametros.put("nro_tel", etNro_tel.getText().toString());

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
