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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    // Constante para la solicitud de selección de imagen
    private static final int PICK_IMAGE_REQUEST = 1;
    // Declaración de variables de vistas
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
        nombre_FT = (EditText) findViewById(R.id.editTxtNombre);
        patente_FT = (EditText)findViewById(R.id.editTxtNumPatente);
        descripcion_FT = (EditText)findViewById(R.id.edittxtDescripcion);
        telefono_FT = (EditText)findViewById(R.id.editTxtTelefono);
        imgFT = (ImageView) findViewById(R.id.imageButton);
        add_Ft = (Button) findViewById(R.id.btn_Agregar);

        // Configuración del lanzador de actividad para seleccionar imágenes
        ActivityResultLauncher<Intent> ActivityResoultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Este método se llama cuando la actividad de selección de imágenes ha terminado
                        if(result.getResultCode() == Activity.RESULT_OK){
                            // Se obtiene la información de la intención devuelta
                            Intent data = result.getData();
                            // Se obtiene la Uri de la imagen seleccionada
                            uri = data.getData();
                            // Se establece la Uri en el ImageView para mostrar la imagen seleccionada
                            imgFT.setImageURI(uri);
                        } else {
                            Toast.makeText(AgregarFoodtruck.this, "No has seleccionado ninguna Imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // que permite al usuario seleccionar una imagen
        imgFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se crea una nueva intención para seleccionar imágenes
                Intent seleccionarIMG = new Intent(Intent.ACTION_PICK);
                // Se establece el tipo de datos que se pueden seleccionar (en este caso, cualquier imagen)
                seleccionarIMG.setType("image/*");
                // Se lanza la actividad de selección de imágenes con el lanzador configurado previamente
                ActivityResoultLauncher.launch(seleccionarIMG);
            }
        });

        // Manejador de clics para el botón de agregar food truck
        add_Ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Este método se llama cuando el usuario hace clic en el botón de agregar food truck
                savedata();
            }
        });


    }

    // Método para guardar la imagen seleccionada en Firebase Storage
    private void savedata() {
        // Referencia de almacenamiento en Firebase con el nombre de la carpeta y la última parte del path de la Uri como nombre del archivo
        StorageReference refAlmacenamiento = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());

        // Subir la imagen al almacenamiento de Firebase
        refAlmacenamiento.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Obtener la URL de descarga de la imagen almacenada en Firebase Storage
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                // Almacenar la URL de la imagen en imageURL y llamar al método para cargar los datos en la base de datos
                imageURL = urlImage.toString();
                uploadData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Manejar el fallo en la carga de la imagen
                Toast.makeText(AgregarFoodtruck.this, "Error al cargar la Imagem}n", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para cargar los datos del food truck en la base de datos de Firebase Realtime
    private void uploadData() {

        String nom = nombre_FT.getText().toString();
        String pat = patente_FT.getText().toString();
        String des = descripcion_FT.getText().toString();
        String tel = telefono_FT.getText().toString();

        FoodTruck foodTruck = new FoodTruck(nom, pat, des, tel ,imageURL);
        // Almacenar el objeto FoodTruck en la base de datos de Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("Foodtruck").child(nom)
                .setValue(foodTruck).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Verificar si la operación fue exitosa y mostrar un mensaje al usuario
                        if(task.isSuccessful()){
                            Toast.makeText(AgregarFoodtruck.this, "Guardado", Toast.LENGTH_SHORT).show();
                        } else {
                            // Manejar el caso en que la operación no fue exitosa (puedes agregar lógica adicional aquí)
                            Toast.makeText(AgregarFoodtruck.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }public void onFailure(@NonNull Exception e){
                        Toast.makeText(AgregarFoodtruck.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
        });
    }
}