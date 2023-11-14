package Food.Truck.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
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
<<<<<<< Updated upstream

        // Inicialización de vistas
=======
>>>>>>> Stashed changes
        NombreFT = findViewById(R.id.txtNombreFT);
        DetailsFT = findViewById(R.id.txtDescripcionFT);
        TelefonoFT = findViewById(R.id.txtTelefono);
        deleteBT = findViewById(R.id.btnEliminar);
        editBT = findViewById(R.id.btnEditar);

        // Obtener datos del Intent
        Bundle bundle = getIntent().getExtras();
        ImageView imageView = findViewById(R.id.imageView);
<<<<<<< Updated upstream

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
=======
        String imageUrl = bundle.getString("Image");
        Glide.with(this).load(imageUrl).into(imageView);

        // Obtener los datos pasados en el Intent
        if (bundle != null) {
            key = bundle.getString("Key"); // Asegúrate de que "Key" sea el mismo nombre usado al pasar los datos
            imageUrl = bundle.getString("Image");
            // ... [tu código existente para manejar la imagen] ...
        } else {
            // Manejar el caso donde el bundle o los datos son null
            Toast.makeText(this, "Error: datos no disponibles", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si los datos esenciales no están disponibles
        }

        key = bundle.getString("Key");
        imageUrl = bundle.getString("Image");

        // Configurar el botón de eliminar
        deleteBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFoodTruck();
            }
        });

        // Configurar el botón de editar
        editBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
>>>>>>> Stashed changes
                Intent intent = new Intent(Details_activity.this, editar_activity.class);
                intent.putExtra("Key", key);
                // Puedes pasar otros datos si es necesario
                startActivity(intent);
            }
        });
    }
<<<<<<< Updated upstream
}
=======

    private void deleteFoodTruck() {
        // Eliminar el food truck de la base de datos de Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Foodtruck").child(key);
        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Eliminar la imagen del food truck de Firebase Storage si es necesario
                if (!imageUrl.isEmpty()) {
                    StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Details_activity.this, "Food Truck eliminado", Toast.LENGTH_SHORT).show();
                            finish(); // Cerrar la actividad actual
                        }
                    });
                } else {
                    Toast.makeText(Details_activity.this, "Food Truck eliminado", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad actual
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Details_activity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
>>>>>>> Stashed changes
