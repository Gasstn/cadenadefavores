package com.example.cadena_de_favores;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import java.util.Map;

/**
 * Created by Gasti on 26/8/2020.
 */

public class Submit {
    private String url;
    private Context context;

    public Submit(String url, Context context) {
        this.url = url;
        this.context = context;
    }
    public Submit(Context context) {
        this.url = "";
        this.context = context;
    }

    public void JSONArrayRequest(String archivo_query, Response.Listener<JSONArray> onResponse){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(this.url + archivo_query, onResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Utilidades(context).alert(error.toString() + error.getMessage());
                        //new Utilidades(context).alert("Algo a salido mal :(, vuelva a intentarlo mas tarde");
                    }
                }
        );

        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void JSONArrayRequest_op(String archivo_query, final String msj_error, Response.Listener<JSONArray> onResponse){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(this.url + archivo_query, onResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Utilidades(context).alert(msj_error);
                    }
                }
        );

        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void StringRequest(String archivo_query, final Map<String, String> datos){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url + archivo_query,
            new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new Utilidades(context).alert("Operacion exitosa");
            }}
            , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Utilidades(context).alert("Algo a salido mal :(, vuelva a intentarlo mas tarde");
            }
        }){
            public Map<String, String> getParams() throws AuthFailureError {
                return datos;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void StringRequest_op(String archivo_query, final String msj_bien, final String msj_error, final Map<String, String> datos){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url + archivo_query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Utilidades(context).alert(msj_bien);
                    }}
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Utilidades(context).alert(msj_error);
            }
        }){
            public Map<String, String> getParams() throws AuthFailureError {
                return datos;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void StringRequest_onResponse(String archivo_query, final Map<String, String> datos, Response.Listener<String> onResponse){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url + archivo_query, onResponse,
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Utilidades(context).alert("Algo a salido mal :(, vuelva a intentarlo mas tarde");
            }
        }){
            public Map<String, String> getParams() throws AuthFailureError {
                return datos;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void StringRequest_op_onResponse(String archivo_query, final String msj_error, final Map<String, String> datos, Response.Listener<String> onResponse){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url + archivo_query, onResponse
            , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Utilidades(context).alert(msj_error);
            }
        }){
            public Map<String, String> getParams() throws AuthFailureError {
                return datos;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }
}