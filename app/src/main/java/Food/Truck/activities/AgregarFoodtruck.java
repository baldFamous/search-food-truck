package Food.Truck.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Food.Truck.R;
import Food.Truck.indep_classes.FoodTruck;

public class AgregarFoodtruck extends AppCompatActivity {
    ImageView imgFT;
    EditText nombre_FT, patente_FT, descripcion_FT, telefono_FT;
    Button add_Ft;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foodtruck);

        nombre_FT = findViewById(R.id.editTxtNombre);
        patente_FT = findViewById(R.id.editTxtNumPatente);
        descripcion_FT = findViewById(R.id.edittxtDescripcion);
        telefono_FT = findViewById(R.id.editTxtTelefono);
        imgFT = findViewById(R.id.imageButton);
        add_Ft = findViewById(R.id.btn_Agregar);

        // Lanzador de actividad para seleccionar imágenes
        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();
                        imgFT.setImageURI(uri);
                    } else {
                        Toast.makeText(AgregarFoodtruck.this, "No has seleccionado ninguna imagen.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        imgFT.setOnClickListener(view -> {
            Intent seleccionarIMG = new Intent(Intent.ACTION_PICK);
            seleccionarIMG.setType("image/*");
            imagePickerLauncher.launch(seleccionarIMG);
        });

        add_Ft.setOnClickListener(view -> {
            if (uri != null) {
                uploadImageToFirebaseStorage();
            } else {
                Toast.makeText(AgregarFoodtruck.this, "Por favor, selecciona una imagen para el food truck.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference refAlmacenamiento = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        refAlmacenamiento.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
            urlTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String imageURL = task.getResult().toString();
                    uploadFoodTruckData(imageURL);

                } else {
                    Toast.makeText(AgregarFoodtruck.this, "La imagen se subió pero no se pudo obtener la URL.", Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(e -> Toast.makeText(AgregarFoodtruck.this, "Error al cargar la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void uploadFoodTruckData(String imageURL) {
        String nom = nombre_FT.getText().toString().trim();
        String pat = patente_FT.getText().toString().trim();
        String des = descripcion_FT.getText().toString().trim();
        String tel = telefono_FT.getText().toString().trim();

        if (nom.isEmpty() || pat.isEmpty() || des.isEmpty() || tel.isEmpty()) {
            Toast.makeText(AgregarFoodtruck.this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }


        FoodTruck foodTruck = new FoodTruck(nom, pat, des, tel, imageURL);
        FirebaseDatabase.getInstance().getReference("Foodtruck").push().setValue(foodTruck).addOnCompleteListener(task -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("FoodTruck");
            String key = ref.push().getKey();
            ref.child(key).setValue(foodTruck);
            if(task.isSuccessful()){
                Toast.makeText(AgregarFoodtruck.this, "Food Truck agregado exitosamente.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AgregarFoodtruck.this, Inicio.class));
                finish();
            } else {
                Toast.makeText(AgregarFoodtruck.this, "Error al guardar los datos.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AgregarFoodtruck.this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
