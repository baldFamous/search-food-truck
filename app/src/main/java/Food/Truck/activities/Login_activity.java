package Food.Truck.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Food.Truck.R;

public class Login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button abrirInicio = findViewById(R.id.btn_inicio);
        TextView abrirRegistro = findViewById(R.id.registro);
        TextView abrirTerminos = findViewById(R.id.terms);


        abrirRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la actividad de registro
                Intent intent = new Intent(getApplicationContext(), Registro_activity.class);
                startActivity(intent);
            }
        });

        abrirInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la actividad de inicio
                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                startActivity(intent);
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
    }
}