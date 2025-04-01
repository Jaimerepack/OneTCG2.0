package com.example.onetcg20;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Actividad de loggin.
 */
public class LogginActivity extends AppCompatActivity {
    /**
     * Declaraciones de Botones.
     */
    private Button btnRegistrarse,btnIngresar;
    /**
     * Declaraciones de los EditText.
     */
    private EditText etUsuario, etEmail, etContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loggin);
        SQLiteOpenHelper dbHelper = new bdonetcg(LogginActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        referencias();
        eventos();
        incertar_cartas(db);
        incertar_edicion(db);

        /**
         * Quitar la barra de accion.
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        };

    /**
     * Referencias de Botones y EditText.
     */
    public void referencias(){
        /**
         * Botones
         */
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        /**
         * EditText
         */
        etUsuario = findViewById(R.id.etUsuario);
        etEmail = findViewById(R.id.etEmail);
        etContrasena = findViewById(R.id.etContrasena);

    }

    /**
     * Eventos.
     */
    public void eventos(){
        /**
         * Evento del Boton Registrarse
         */
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String email = etEmail.getText().toString();
                String contrasena = etContrasena.getText().toString();
                if (usuario.isEmpty() || email.isEmpty()|| contrasena.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campos no completados",Toast.LENGTH_SHORT).show();
                    if (usuario.isEmpty()) {
                        etUsuario.setError("Campo vacio");
                    }
                    if (email.isEmpty()) {
                        etEmail.setError("Campo vacio");
                    }
                    if (contrasena.isEmpty()) {
                        etContrasena.setError("Campo vacio");
                    }
                } else {
                    registerUser(usuario, email, contrasena);
                }
            }
        });

        /**
         * Evento del Boton Ingresar
         */
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String email = etEmail.getText().toString();
                String contrasena = etContrasena.getText().toString();
                logginUser(usuario, email, contrasena);
            }
        });
    }

    /**
     * Incertar automaticamente cartas en base de datos desde txt.
     *
     * @param db db = dbHelper.getWritableDatabase
     */
    public void incertar_cartas(SQLiteDatabase db) {
        try {
            InputStream is = getResources().openRawResource(R.raw.cartas); // Archivo en res/raw/cartas.txt
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("'"); // Separador de los datos
                String card_id = parts[0];
                String rarity = parts[1];
                String designation = parts[2];
                String name = parts[3];
                String life = parts[4];
                String attribute = parts[5];
                String power = parts[6];
                String counter = parts[7];
                String color = parts[8];
                String type = parts[9];
                String effect = parts[10];
                String _trigger = parts[11]; // el nombre es trigger pero es palabra reservada
                String card_set = parts[12];
                String notes = parts[13];

                /**
                 * Comparar registros para insertar los nuevos
                 */
                Cursor cursor = db.rawQuery("SELECT card_id FROM Cartas WHERE card_id = ?", new String[]{card_id});
                /**
                 * Inserción en la base de datos
                 * */
                if (cursor.getCount() == 0) {
                    db.execSQL("INSERT INTO Cartas (card_id, rarity, designation, name, life, " +
                                    "attribute, power, counter, color, type, effect, _trigger, card_set, notes) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            new Object[]{card_id, rarity, designation, name, life, attribute, power, counter,
                                    color, type, effect, _trigger, card_set, notes});
                }
                cursor.close();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Incertar ediciones en base de datos.
     *
     * @param db db = dbHelper.getWritableDatabase
     */
    public void incertar_edicion(SQLiteDatabase db) {
        try {
            InputStream is = getResources().openRawResource(R.raw.ediciones); // Archivo en res/raw/ediciones.txt
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("'"); // Separador de los datos
                String id_edicion = parts[0];
                String nombre_edicion = parts[1];
                String año_lanzamiento = parts[2];

                //Comparar registros para insertar los nuevos
                Cursor cursor = db.rawQuery("SELECT id_edicion FROM Edicion WHERE id_edicion = ?", new String[]{id_edicion});
                // Inserción en la base de datos
                if (cursor.getCount() == 0) {
                    db.execSQL("INSERT INTO Edicion (id_edicion, nombre_edicion, año_lanzamiento) " +
                                    "VALUES (?, ?, ?)",
                            new Object[]{id_edicion, nombre_edicion, año_lanzamiento});
                }
                cursor.close();

            }
            is.close();
            reader.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registrar Usuario en la base de datos.
     *
     * @param usuario    Nombre de usuario extraido del editText correspondiente
     * @param email      Email de usuario extraido del editText correspondiente
     * @param contrasena Contraseña de usuario extraido del editText correspondiente
     */
    public void registerUser(String usuario, String email, String contrasena) {
        SQLiteOpenHelper dbHelper = new bdonetcg(LogginActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /**
         * Verificar si el usuario o el email ya existen
         */
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE nombre = ? OR correo = ?", new String[]{usuario, email});
        if (cursor.getCount() > 0) {
            /**
             * El usuario o el email ya existen
             */
            Toast.makeText(this, "El usuario o el email ya existen", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * El usuario y el email no existen, insertar el nuevo usuario
             */
            db.execSQL("INSERT INTO Usuario (nombre, correo, contraseña) VALUES (?, ?, ?)",
                    new Object[]{usuario, email, contrasena});
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }

    /**
     * Ingreso de Usuario.
     *
     * @param usuario    Nombre de usuario extraido del editText correspondiente
     * @param email      Email de usuario extraido del editText correspondiente
     * @param contrasena Contraseña de usuario extraido del editText correspondiente
     */
    public void logginUser(String usuario, String email, String contrasena) {
        SQLiteOpenHelper dbHelper = new bdonetcg(LogginActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /**
         * Verificar si el usuario o el email ya existen
         */
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE nombre = ? AND correo = ? AND contraseña = ?", new String[]{usuario, email,contrasena});
        if (cursor.getCount() > 0) {
            /**
             * El usuario o el email ya existen
             */
            Intent i = new Intent(LogginActivity.this, MainActivity.class);
            startActivity(i);
        } else {
            /**
             * El usuario o el email no existen
             */
            Toast.makeText(this, "Datos no validos", Toast.LENGTH_SHORT).show();
            etUsuario.setText("");
            etContrasena.setText("");
            etEmail.setText("");
        }
        cursor.close();
        db.close();
    }

    /**
     * Clase para llamar la base de datos desde otra actividad.
     *
     * @param context el contexto de donde se llama
     * @return regresa la base de datos
     */
    @NotNull
    public static bdonetcg bdonetcg(@NotNull Context context) {
        return null;
    }


    /**
     * Creacion de Base de datos.
     */
    public static class bdonetcg extends SQLiteOpenHelper {
        /**
         * Asignacion de nombre a la base de datos.
         */
        public static final String DATABASE_NAME = "OneTCG.db";
        /**
         * Version de la base de datos.
         */
        public static final int DATABASE_VERSION = 1;

        /**
         * Instantiates a new Bdonetcg.
         *
         * @param context the context
         */
        public bdonetcg(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Creacion y Update de Tablas
         * @param db db = dbHelper.getWritableDatabase
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Cartas (card_id TEXT PRIMARY KEY, rarity TEXT, designation TEXT," +
                    " name TEXT, life TEXT, attribute TEXT, power TEXT, counter TEXT, color TEXT," +
                    "type TEXT, effect TEXT, _trigger TEXT, card_set TEXT, notes TEXT)");
            db.execSQL("CREATE TABLE Usuario (id_usuario INTEGER PRIMARY KEY, nombre TEXT, correo TEXT," +
                    " contraseña TEXT)");
            db.execSQL("CREATE TABLE Mazo (id_mazo INTEGER PRIMARY KEY, nombre_mazo TEXT)");
            db.execSQL("CREATE TABLE Coleccion (id_coleccion INTEGER PRIMARY KEY, nombre_coleccion TEXT)");
            db.execSQL("CREATE TABLE Cartas_obtenidas (id_carta_obtenida TEXT PRIMARY KEY, cantidad INTEGER)");
            db.execSQL("CREATE TABLE Edicion (id_edicion TEXT PRIMARY KEY, nombre_edicion TEXT, año_lanzamiento TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Cartas");
            onCreate(db);
        }
    }
}