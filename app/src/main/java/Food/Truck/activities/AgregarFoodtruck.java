package Food.Truck.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Food.Truck.R;
import Food.Truck.indep_classes.FoodTruck;

public class AgregarFoodtruck extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView imgFT;
    EditText nombre_FT, patente_FT, descripcion_FT, telefono_FT;
    Button add_Ft;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_foodtruck);

        // Binds View
        nombre_FT = (EditText) findViewById(R.id.editTextNombre);
        patente_FT = (EditText)findViewById(R.id.editTextNumPatente);
        descripcion_FT = (EditText)findViewById(R.id.editDescripcion);
        telefono_FT = (EditText)findViewById(R.id.editTextTelefono);
        imgFT = (ImageView) findViewById(R.id.imageButton);
        add_Ft = (Button) findViewById(R.id.btn_Agregar);

        ActivityResultLauncher<Intent> ActivityResoultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            imgFT.setImageURI(uri);
                        } else{
                            Toast.makeText(AgregarFoodtruck.this, "Debe ingresar una imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imgFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seleccionarIMG = new Intent(Intent.ACTION_PICK);
                seleccionarIMG.setType("image/*");
                ActivityResoultLauncher.launch(seleccionarIMG);
            }
        });
        add_Ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();

            }

        });
    }

    private void savedata() {
        StorageReference refAlmacenamiento = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        refAlmacenamiento.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void uploadData() {
        String nom = nombre_FT.getText().toString();
        String pat = patente_FT.getText().toString();
        String des = descripcion_FT.getText().toString();
        String tel = telefono_FT.getText().toString();

        FoodTruck foodTruck = new FoodTruck(nom, pat, des, tel ,imageURL);
        FirebaseDatabase.getInstance().getReference("Foodtruck").child(nom)
                .setValue(foodTruck).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AgregarFoodtruck.this, "Guardado", Toast.LENGTH_SHORT).show();

                        }
                    }public void onFailure(@NonNull Exception e){
                        Toast.makeText(AgregarFoodtruck.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
}