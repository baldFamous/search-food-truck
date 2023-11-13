package Food.Truck.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Food.Truck.R;
import Food.Truck.database.DatabaseManager;
import Food.Truck.indep_classes.usuario_consum;
import Food.Truck.indep_classes.usuario_propi;

public class Registro_activity extends AppCompatActivity {
    Button btn_consumidor;
    Button btn_propietario;
    Button btn_registrar;
    TextView abrirTerminos;
    EditText nombre_edit;
    EditText apellido_edit;
    EditText correo_edit;
    EditText contrasena_edit;
    EditText patente_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_activity);

        btn_consumidor = findViewById(R.id.btn_consumidor);
        btn_propietario = findViewById(R.id.btn_propietario);
        btn_registrar = findViewById(R.id.btn_Agregar);
        abrirTerminos = findViewById(R.id.terms);
        nombre_edit = findViewById(R.id.editTextNombre);
        apellido_edit = findViewById(R.id.editTextNumPatente);
        correo_edit = findViewById(R.id.editDescripcion);
        contrasena_edit = findViewById(R.id.editTextTelefono);
        patente_edit = findViewById(R.id.edittxtImagen);

        btn_consumidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar colores de los botones
                btn_consumidor.setBackgroundColor(Color.WHITE);
                btn_consumidor.setTextColor(Color.BLACK);
                btn_propietario.setBackgroundColor(Color.GRAY);
                btn_propietario.setTextColor(Color.BLACK);

                patente_edit.setEnabled(false);
                patente_edit.setText("");
            }
        });

        btn_propietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_propietario.setBackgroundColor(Color.WHITE);
                btn_consumidor.setBackgroundColor(Color.GRAY);
                patente_edit.setEnabled(true);
            }
        });

        abrirTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la otra actividad
                Intent intent = new Intent(getApplicationContext(), Terminos.class);
                startActivity(intent);
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombre_edit.getText().toString();
                String apellido = apellido_edit.getText().toString();
                String correo = correo_edit.getText().toString();
                String contrasena = contrasena_edit.getText().toString();
                String patente = patente_edit.getText().toString();


                if (btn_consumidor.isSelected()){
                    capturarDatos_us_cons(nombre, apellido, correo, contrasena);
                } else if (btn_propietario.isSelected()){
                    capturarDatos_us_prop(nombre, apellido, correo, contrasena, patente);
                }

                Intent intent = new Intent(getApplicationContext(), Login_activity.class);
                startActivity(intent);
                Toast.makeText(Registro_activity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void capturarDatos_us_cons(String nombre, String apellido, String correo, String contrasena) {
        usuario_consum usuario_cons = new usuario_consum(nombre, apellido, correo, contrasena);
        DatabaseManager dbManager = new DatabaseManager(this, null);
        dbManager.open();
        dbManager.insert_us_cons(
                usuario_cons.getNombre(),
                usuario_cons.getApellido(),
                usuario_cons.getCorreo(),
                usuario_cons.getContraseña()
        );
        dbManager.close();
    }

    public void capturarDatos_us_prop(String nombre, String apellido, String correo, String contrasena, String patente) {
        usuario_propi usuario_prop = new usuario_propi(nombre, apellido, correo, contrasena, patente);
        DatabaseManager dbManager = new DatabaseManager(this, null);
        dbManager.open();
        dbManager.insert_us_prop(
                usuario_prop.getNombre(),
                usuario_prop.getApellido(),
                usuario_prop.getCorreo(),
                usuario_prop.getContraseña(),
                usuario_prop.getReg_foodtruck()
        );
        dbManager.close();
    }
}
