package com.example.cadena_de_favores;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.webkit.CookieManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/********************************
 * Created by Gasti on 1/9/2020.*
 ********************************/

public class Utilidades {
    private static Context context;

    public Utilidades(Context context){
        this.context = context;
    }
    public Utilidades(){
        this.context = null;
    }

    public boolean es_un_email(String email){
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).find();
    }

    public Date convertir_a_fecha(String fecha){
        try {
            return new SimpleDateFormat("YYYY-MM-dd").parse(fecha);
        }catch(Exception ex){return null;}
    }

/*  EN CUARENTENA PORQUE ESTA MALITO
    public void cambiar_activity(Class a, Context context) {
        startActivity(new Intent(context, a));
    }
    REEMPLAZAR A EL ACTIVITY POR:
    private void cambiar_activity(Class a) {
        startActivity(new Intent(getApplicationContext(), a));
    }*/
    public void alert(String texto_alerta){
        if(this.context != null)
            Toast.makeText(this.context, texto_alerta, Toast.LENGTH_LONG).show();
    }
    public void alert_short(String texto_alerta){
        if(this.context != null)
            Toast.makeText(this.context, texto_alerta, Toast.LENGTH_SHORT).show();
    }

    public int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public ArrayList<HashMap<String, String>> conertir_JSONArray_ArrayHashMap(JSONArray response, ArrayList<String> campos){
        ArrayList<HashMap<String, String>> dev = new ArrayList<>();

        JSONObject jsonObject = null;

        boolean ver = true;
        int i = 0, len = response.length();
        while (ver && i < len) {
            try {
                jsonObject = response.getJSONObject(i);

                HashMap<String, String> mapa_aux = new HashMap<>();
                for (String campo : campos) {
                    mapa_aux.put(campo, jsonObject.getString(campo));
                }
                dev.add(mapa_aux);
            } catch (JSONException e) {
                dev = new ArrayList<>();
                ver = false;
            }
            i++;
        }
        //new Utilidades(this.context).alert(this.resultado_json.toString());

        return dev;
    }

    public boolean existe_cookie_de_id(){
        boolean dev = false;

        CookieManager cookieManager = CookieManager.getInstance();

        if(cookieManager.hasCookies()) {
            try {
                if(!cookieManager.getCookie("id").isEmpty())
                    dev = true;
            }catch(Exception ex){}
        }

        return dev;
    }
}
