package Food.Truck.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.Reference;

import Food.Truck.R;

public class Details_activity extends AppCompatActivity {

    TextView NombreFT, DetailsFT, TelefonoFT;
    ImageView imagen;
    Button deleteBT;
    Button editBT;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_foodtruck);

        // Inicialización de vistas
        NombreFT = findViewById(R.id.txtNombreFT);
        DetailsFT = findViewById(R.id.txtDescripcionFT);
        TelefonoFT = findViewById(R.id.txtTelefono);
        deleteBT = findViewById(R.id.btnEliminar);
        editBT = findViewById(R.id.btnEditar);

        // Obtener datos del Intent
        Bundle bundle = getIntent().getExtras();
        ImageView imageView = findViewById(R.id.imageView);

        // Obtener la URL de la imagen del FoodTruck
        imageUrl = bundle.getString("Image");

        // Cargar la imagen usando Glide o establecer una imagen por defecto si la URL es nula o vacía
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.user);
        }

        // Configurar el botón de eliminación
        deleteBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("FoodTruck");
                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    // Obtener referencia de almacenamiento para la imagen
                    StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

                    // Eliminar la imagen del almacenamiento y los datos del FoodTruck de la base de datos
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            reference.child(key).removeValue();
                            reference.child(key).child("imageUrl").removeValue();
                            Toast.makeText(Details_activity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Inicio.class));
                            finish();
                        }
                    });
                } catch (Exception e) {
                    // Manejar errores al eliminar
                    Toast.makeText(Details_activity.this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el botón de edición
        editBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad de edición y pasar datos adicionales
                Intent intent = new Intent(Details_activity.this, editar_activity.class);
                intent.putExtra("key", key);
                intent.putExtra("Nombre", NombreFT.getText().toString());
                intent.putExtra("Descripcion", DetailsFT.getText().toString());
                intent.putExtra("Telefono", TelefonoFT.getText().toString());
                intent.putExtra("Image", imageUrl);
                startActivity(intent);
            }
        });
    }
}
