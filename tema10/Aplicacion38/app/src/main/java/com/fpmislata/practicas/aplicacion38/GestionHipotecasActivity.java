package com.fpmislata.practicas.aplicacion38;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class GestionHipotecasActivity extends AppCompatActivity {

    private HipotecasDAO hipotecasDAO;
    private Cursor cursor;

    //
    // Modo del formulario
    //
    private int modo ;

    //
    // Identificador del registro que se edita cuando la opción es MODIFICAR
    //
    private long id ;

    //
    // Elementos de la vista
    //
    private EditText nombre;
    private EditText condiciones;
    private EditText contacto;
    private EditText telefono;
    private EditText email;
    private EditText observaciones;
    private CheckBox pasivo ;

    private Button boton_guardar;
    private Button boton_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_hipotecas);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        //
        // Obtenemos los elementos de la vista
        //
        nombre = (EditText) findViewById(R.id.nombre);
        condiciones = (EditText) findViewById(R.id.condiciones);
        contacto = (EditText) findViewById(R.id.contacto);
        telefono = (EditText) findViewById(R.id.telefono);
        email = (EditText) findViewById(R.id.email);
        observaciones = (EditText) findViewById(R.id.observaciones);
        pasivo = (CheckBox) findViewById(R.id.pasivo);

        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        //
        // Creamos el DAO
        //
        hipotecasDAO = new HipotecasDAO(this);
        hipotecasDAO.abrir();

        //
        // Obtenemos el identificador del registro si viene indicado
        //
        if (extra.containsKey(HipotecasDAO.C_COLUMNA_ID)){
            id = extra.getLong(HipotecasDAO.C_COLUMNA_ID);
            consultar(id);
        }

        //
        // Establecemos el modo del formulario
        //
        establecerModo(extra.getInt(Constantes.C_MODO));

        //
        // Definimos las acciones para los dos botones
        //
        boton_guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                guardar();
            }
        });

        boton_cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void establecerModo(int m) {
        this.modo = m ;

        // Si estamos solamente visualizando establecemos el modo edicion desactivado a todo el formulario
        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(nombre.getText().toString());
            this.setEdicion(false);
        }else if (modo == Constantes.C_CREAR){
            this.setTitle(R.string.hipoteca_crear_titulo);
            this.setEdicion(true);
        }else if (modo == Constantes.C_EDITAR) {
            this.setTitle(R.string.hipoteca_editar_titulo);
            this.setEdicion(true);
        }
    }

    private void consultar(long id) {
        //
        // Consultamos el centro por el identificador
        //
        cursor = hipotecasDAO.getRegistro(id);

        nombre.setText(cursor.getString(
                cursor.getColumnIndex(HipotecasDAO.C_COLUMNA_NOMBRE)));
        condiciones.setText(cursor.getString(
                cursor.getColumnIndex(HipotecasDAO.C_COLUMNA_CONDICIONES)));
        contacto.setText(cursor.getString(
                cursor.getColumnIndex(HipotecasDAO.C_COLUMNA_CONTACTO)));
        telefono.setText(cursor.getString(
                cursor.getColumnIndex(HipotecasDAO.C_COLUMNA_TELEFONO)));
        email.setText(cursor.getString(
                cursor.getColumnIndex(HipotecasDAO.C_COLUMNA_EMAIL)));
        observaciones.setText(cursor.getString(
                cursor.getColumnIndex(HipotecasDAO.C_COLUMNA_OBSERVACIONES)));
        pasivo.setChecked(cursor.getString(
                cursor.getColumnIndex(hipotecasDAO.C_COLUMNA_PASIVO)).equals("S"));
    }

    private void setEdicion(boolean opcion) {
        nombre.setEnabled(opcion);
        condiciones.setEnabled(opcion);
        contacto.setEnabled(opcion);
        telefono.setEnabled(opcion);
        email.setEnabled(opcion);
        observaciones.setEnabled(opcion);
        pasivo.setEnabled(opcion);
    }

    private void guardar() {

        //
        // Obtenemos los datos del formulario
        //
        ContentValues reg = new ContentValues();

        //
        // Si estamos en modo edición añadimos el identificador del registro que se utilizará en el update
        //
        if (modo == Constantes.C_EDITAR) {
            reg.put(HipotecasDAO.C_COLUMNA_ID, id);
        }

        reg.put(HipotecasDAO.C_COLUMNA_NOMBRE, nombre.getText().toString());
        reg.put(HipotecasDAO.C_COLUMNA_CONDICIONES, condiciones.getText().toString());
        reg.put(HipotecasDAO.C_COLUMNA_CONTACTO, contacto.getText().toString());
        reg.put(HipotecasDAO.C_COLUMNA_TELEFONO, telefono.getText().toString());
        reg.put(HipotecasDAO.C_COLUMNA_EMAIL, email.getText().toString());
        reg.put(HipotecasDAO.C_COLUMNA_OBSERVACIONES, observaciones.getText().toString());
        reg.put(HipotecasDAO.C_COLUMNA_PASIVO, (pasivo.isChecked())?"S":"N");


        if (modo == Constantes.C_CREAR) {
            hipotecasDAO.insert(reg);
            Toast.makeText(GestionHipotecasActivity.this, R.string.hipoteca_crear_confirmacion,
                    Toast.LENGTH_SHORT).show();
        } else if (modo == Constantes.C_EDITAR) {
            Toast.makeText(GestionHipotecasActivity.this, R.string.hipoteca_editar_confirmacion,
                    Toast.LENGTH_SHORT).show();
            hipotecasDAO.update(reg);
        }

        //
        // Devolvemos el control
        //
        setResult(RESULT_OK);
        finish();
    }

    private void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        if (modo == Constantes.C_VISUALIZAR)
            getMenuInflater().inflate(R.menu.menu_hipotecas_ver, menu);

        else
            getMenuInflater().inflate(R.menu.menu_hipotecas_editar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_editar:
                establecerModo(Constantes.C_EDITAR);
                return true;

            case R.id.menu_eliminar:
                borrar(id);
                return true;

            case R.id.menu_cancelar:
                cancelar();
                return true;

            case R.id.menu_guardar:
                guardar();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void borrar(final long id) {
        /**
         * Borramos el registro con confirmación
         */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.hipoteca_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(R.string.hipoteca_eliminar_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int boton) {
                hipotecasDAO.delete(id);
                Toast.makeText(GestionHipotecasActivity.this, R.string.hipoteca_eliminar_confirmacion, Toast.LENGTH_SHORT).show();
                /**
                 * Devolvemos el control
                 */
                setResult(RESULT_OK);
                finish();
            }
        });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();
    }
}
