package Food.Truck.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import Food.Truck.R;

public class Details_activity extends AppCompatActivity {

    TextView NombreFT, DetailsFT, TelefonoFT;
    ImageView imagen;
    String key;
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_foodtruck);

        // Inicialización de vistas
        NombreFT = findViewById(R.id.txtNombreFT);
        DetailsFT = findViewById(R.id.txtDescripcionFT);
        TelefonoFT = findViewById(R.id.txtTelefono);
        imagen = findViewById(R.id.imageView);

        // Obtener datos del Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            NombreFT.setText(bundle.getString("Nombre"));
            DetailsFT.setText(bundle.getString("Descripcion"));
            TelefonoFT.setText(bundle.getString("Telefono"));
            Glide.with(this).load(imageUrl).into(imagen);
        } else {
            Toast.makeText(this, "Error: datos no disponibles.", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay datos.
        }

        // Botón para eliminar FoodTruck
        findViewById(R.id.btnEliminar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFoodTruck();
            }
        });

        // Botón para editar FoodTruck
        findViewById(R.id.btnEditar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Details_activity.this, editar_activity.class);
                intent.putExtra("Key", key);
                // Puedes pasar otros datos si es necesario
                startActivity(intent);
            }
        });
    }

    private void deleteFoodTruck() {
        // Eliminar el food truck de la base de datos de Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FoodTruck").child(key);
        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Si existe una imagen asociada, eliminarla de Firebase Storage
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Imagen eliminada con éxito
                            Toast.makeText(Details_activity.this, "Food Truck eliminado", Toast.LENGTH_SHORT).show();
                            // Cierra la actividad y regresa al inicio
                            startActivity(new Intent(Details_activity.this, Inicio.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar cualquier error que ocurra durante la eliminación de la imagen
                            Toast.makeText(Details_activity.this, "Error al eliminar la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // No hay imagen para eliminar, solo mostrar confirmación de eliminación
                    Toast.makeText(Details_activity.this, "Food Truck eliminado", Toast.LENGTH_SHORT).show();
                    // Cierra la actividad y regresa al inicio
                    startActivity(new Intent(Details_activity.this, Inicio.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Manejar cualquier error que ocurra durante la eliminación de la base de datos
                Toast.makeText(Details_activity.this, "Error al eliminar el Food Truck: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}