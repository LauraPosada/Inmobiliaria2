package com.example.tecnosystem.inmobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tecnosystem.inmobiliaria.controlador.Servicio;
import com.example.tecnosystem.inmobiliaria.modelo.Persona;
import com.example.tecnosystem.inmobiliaria.modelo.Rol;
import com.example.tecnosystem.inmobiliaria.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistroUsuario extends AppCompatActivity implements InicioSesion{

    private List<String> genero;
    private Spinner spGenero;
    private Servicio servicio;
    ProgressBar progressBarUsu;

    private EditText edtNombreUsu;
    private EditText edtApellidoUsu;
    private EditText edtCedulaUsu;
    private EditText edtEdadUsu;
    private EditText edtTelefonoUsu;
    private EditText edtDireccionUsu;
    private EditText edtEmailUsu;

    private EditText edtNomUsuarioRegistro;
    private EditText edtPasswordRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        genero = new ArrayList<>();
        spGenero = (Spinner)findViewById(R.id.spGenero);
        progressBarUsu = (ProgressBar) findViewById(R.id.progressBarUsu);

        edtNombreUsu = (EditText)findViewById(R.id.edtNombreUsu);
        edtApellidoUsu = (EditText)findViewById(R.id.edtApellidoUsu);
        edtCedulaUsu = (EditText)findViewById(R.id.edtCedulaUsu);
        edtEdadUsu = (EditText)findViewById(R.id.edtEdadUsu);
        edtTelefonoUsu = (EditText)findViewById(R.id.edtTelefonoUsu);
        edtDireccionUsu = (EditText)findViewById(R.id.edtDireccionUsu);
        edtEmailUsu = (EditText)findViewById(R.id.edtEmailUsu);
        edtNomUsuarioRegistro = (EditText)findViewById(R.id.edtNomUsuarioRegistro);
        edtPasswordRegistro = (EditText)findViewById(R.id.edtPasswordRegistro);
        progressBarUsu.setVisibility(View.INVISIBLE);

        cargarGenero();
    }


    public void cargarGenero(){
        genero.add("Seleccione...");
        genero.add("MASCULINO");
        genero.add("FEMENINO");

        ArrayAdapter<String> spinnerArrayAdapter = new  ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,genero);
        spGenero.setAdapter(spinnerArrayAdapter);

    }

    public void registrar(View view) {

        if (edtNombreUsu.getText().toString().isEmpty() || edtApellidoUsu.getText().toString().isEmpty() ||
                edtCedulaUsu.getText().toString().isEmpty() || edtEdadUsu.getText().toString().isEmpty() ||
                edtTelefonoUsu.getText().toString().isEmpty() || edtDireccionUsu.getText().toString().isEmpty() ||
                edtEmailUsu.getText().toString().isEmpty() || edtNomUsuarioRegistro.getText().toString().isEmpty() ||
                edtPasswordRegistro.getText().toString().isEmpty() || spGenero.getSelectedItem() == "Seleccione...") {
            Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_LONG).show();

        } else {

            Persona p = new Persona();

            p.setNombre(edtNombreUsu.getText().toString());
            p.setApellido(edtApellidoUsu.getText().toString());
            p.setCedula(Integer.parseInt(edtCedulaUsu.getText().toString()));
            p.setGenero(spGenero.getSelectedItem().toString());
            p.setEdad(edtEdadUsu.getText().toString());
            p.setTelefono(edtTelefonoUsu.getText().toString());
            p.setDireccion(edtDireccionUsu.getText().toString());
            p.setEmail(edtEmailUsu.getText().toString());

            Rol rol = new Rol();
            rol.setCodigo(1);



            Usuario u = new Usuario();
            u.setNombreUsuario(edtNomUsuarioRegistro.getText().toString());
            u.setPasword(edtPasswordRegistro.getText().toString());
            u.setPersona_cedula(p);
            u.setRol_codigo(rol);

           // Toast.makeText(this, u.toString(),Toast.LENGTH_LONG).show();

            servicio = new Servicio(p,"registrarPersona", "gestion.php", this, progressBarUsu);
            servicio.delegate= this;
            servicio.execute();

            servicio = new Servicio(u,"registrarUsuario", "gestion.php", this, progressBarUsu);
            servicio.delegate= this;
            servicio.execute();



            Toast.makeText(this,"Creado",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {



    }
}
