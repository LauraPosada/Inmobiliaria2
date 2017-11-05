package com.example.tecnosystem.inmobiliaria.controlador;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tecnosystem.inmobiliaria.InicioSesion;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TecnoSystem on 2/11/2017.
 */

public class Servicio extends AsyncTask<Void, String, Boolean> {




    private Object object;

    private ListView listView;


    //Varable interface
    public InicioSesion delegate = null;

    //Objeto Json para cargar la respuesta
    private  JSONObject jsonobject;

    /*Variable que almacenara el resultado del servidor*/
    private StringBuffer buffer = null;

    /*Variable que almacenara la accion que se realizara en el servidor*/
    private String accion;

    /*URL a la que se le realizara la peticion*/
    private final String rutas = "http://192.168.1.76/proyectoFinalAndroid/controlador/";
    private String ruta;

    /*Referencia de la activity que envio la solicitud para mostrar los TOAST*/
    Activity activity;

    /*Referencia a la barra de carga*/
    ProgressBar carga;


    //constru gen
    public Servicio(Object object, String accion, String ruta, Activity activity, ProgressBar carga) {
        this.object = object;
        this.accion = accion;
        this.ruta = rutas + ruta;
        this.activity = activity;
        this.carga = carga;

    }

    @Override
    protected void onPreExecute() {
        //Se activa la barra de carga
        carga.setVisibility(View.VISIBLE);
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        /*Se define un objeto para la conexion*/
        HttpURLConnection conn = null;
        /*Se define un buffer para leer los resultados de la conexion*/
        BufferedReader reader = null;
        try {
            /*Se crea la conexion*/
            /*Se establece un Objeto URL con la ruta definida*/
            URL url = new URL(ruta);
            /*Se añade la URL a la conexion*/
            conn = (HttpURLConnection) url.openConnection();
            /*Se define el tipo de conexion (GET - POST)*/
            conn.setRequestMethod("POST");

            Gson gson = new Gson();
            String stringson = gson.toJson(object);

            /*Se añaden datos*/
            /*Se define en un objeto Uri.Builder para añadirle los datos que seran enviados*/
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("object", stringson)
                    .appendQueryParameter("action", accion);




            /*Se codifican los datos añadidos con &&*/
            String query = builder.build().getEncodedQuery();

            /*Se define un OutputStream para añadir los datos definidos a la conexion como datos
            * de salida*/
            OutputStream os = conn.getOutputStream();

            /*Se pasan a un Buffer*/
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            /*Se añaden*/
            writer.write(query);

            /*Se confirma*/
            writer.flush();

            /*Se cierra la adicion*/
            writer.close();
            os.close();


            /*Se conecta, recibe datos y los lee*/

            /*Se ejecuta la conexion*/
            publishProgress("Se envian los datos");
            conn.connect();

            /*Con un InputStream Se obtienen los datos de la conexion*/
            InputStream stream = conn.getInputStream();

            /*Se define un reader para leer los datos, asociandolo al inputStream*/
            reader = new BufferedReader(new InputStreamReader(stream));

            /*Se inicializa el buffer para almacenar como String los resultados*/
            buffer = new StringBuffer();

            /*Variable temporal para concatenar los datos leidos*/
            String line;

            /*Mientas lo que lea es diferente de vacio*/
            while ((line = reader.readLine()) != null) {
                /*Añadalos al buffer global*/
                buffer.append(line);
            }

        } catch (MalformedURLException e) {
            publishProgress("Error mal estructura URL " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            publishProgress("Error IO " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            /*Desconecta la conexion activa*/
            if (conn != null) {
                conn.disconnect();
            }
            try {
                /*Cerramos los readers*/
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                publishProgress("Error al final " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(activity, values[0],
                Toast.LENGTH_SHORT).show();
    }

    /**
     * pasa un array json a un list
     */
    public List<Object> jsonToList(String json) throws JSONException {
        JSONArray arrayJson = new JSONArray(json);
        List<Object> lista = new ArrayList<Object>();
        for (int i = 0; i < arrayJson.length(); i++) {
            lista.add((Object) arrayJson.getJSONObject(i));
        }
        return lista;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        /*Se oculta la barra de carga*/
        carga.setVisibility(View.INVISIBLE);
        try {
            if (result) {
                /*Como el resultado obtenido es un array JSON, se pasa el string JSON al array*/
                JSONArray jsonarray = new JSONArray(buffer.toString());

                /*Por cada elemento del JSON*/
                for (int i = 0; i < jsonarray.length(); i++) {
                    /*Se saca el objeto del array y se pasa a un objeto JSON*/

                    jsonobject = jsonarray.getJSONObject(i);

                    delegate.processFinish(jsonobject);

                }
            } else {
                Toast.makeText(activity, "Error en la operacion",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(activity, "Error tratanto el resultado " +
                            e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public JSONObject getJsonobject() {
        return jsonobject;
    }

    public void setJsonobject(JSONObject jsonobject) {
        this.jsonobject = jsonobject;
    }


}
