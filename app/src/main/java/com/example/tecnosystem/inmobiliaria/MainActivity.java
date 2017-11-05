package com.example.tecnosystem.inmobiliaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tecnosystem.inmobiliaria.controlador.Servicio;
import com.example.tecnosystem.inmobiliaria.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements  InicioSesion {

    TextView txtUsuario, txtPassword;
    ProgressBar progressBar;
    Servicio servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = (EditText) findViewById(R.id.edtUsuario);
        txtPassword = (EditText) findViewById(R.id.edtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);

    }


    @Override
    public void processFinish(JSONObject output) throws JSONException {

        String nomUsuario = servicio.getJsonobject().getString("nombreUsuario");
                //output.getString("nombreUsuario");
        String contr = servicio.getJsonobject().getString("pasword");

        txtPassword.setText(nomUsuario);
        txtPassword.setText(contr);


      //  Toast.makeText(this,"Por favor llene todos los campos" + output.toString() ,Toast.LENGTH_LONG).show();

        if(output != null){
            Intent i = new Intent(this,AgregarInmueble.class);
            startActivity(i);
        }else{

        }


    }

    public void iniciar(View view){

        try {
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(txtUsuario.getText().toString());
            usuario.setPasword(txtPassword.getText().toString());

            servicio = new Servicio(usuario,"listar","gestion.php",this,progressBar);
            servicio.delegate= this;
            servicio.execute();
        }catch (Exception e){

        }


    }

    public void irRegistrarU(View view){
        Intent i = new Intent(this,RegistroUsuario.class);
        startActivity(i);
    }




}
