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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

                //Si ningun campo esta vacio, se genera la conexion con Firebase
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = db.getReference(FoodTruck.class.getSimpleName());

                //Se declara el evento de la referencia a la db
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //se declara una variable bool de verificacion
                        Boolean existInDb = false;

                        String patenteId = patente_FT.getText().toString();

                        //Este ciclo for recorre todos los objetos guardados en la BD
                        for (DataSnapshot x : snapshot.getChildren()){

                            //La variable x de tipo DataSnapshot, guarda los registros obtenidos de la BD en un array, asi que podemos acceder a los
                            //indices de este si asi lo necesitaramos

                            //Se comprueba si la id ya existe, este validador es temporal ya que es demasiado simple, y estamos ingresando las id de manera manual
                            //La idea final es hacerlo con un metodo

                            if (x.child("id").getValue().toString().equals(patenteId)){
                                Toast.makeText(AgregarFoodtruck.this, "La id de registro ya existe en la BD", Toast.LENGTH_SHORT).show();
                                existInDb = true;
                                break;
                            }
                        }

                        //Si la id no existe en la BD, se registra una propiedad nueva, creando un objeto de la clase, y rellenando su constructor
                        if (existInDb == false){

                            String nom = nombre_FT.getText().toString();
                            String pat = patente_FT.getText().toString();
                            String des = descripcion_FT.getText().toString();
                            String tel = telefono_FT.getText().toString();

                            FoodTruck foodTruck = new FoodTruck(nom, pat, des, tel, imageURL);

                            //Se guarda el objeto en la db usando esta linea de codigo
                            dbRef.push().setValue(foodTruck);

                            //Mensaje de registro exitoso, y se envia al usuario a la pantalla de "menu"
                            Toast.makeText(AgregarFoodtruck.this, "Registro guardado exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AgregarFoodtruck.this, Inicio.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }







    // Método para guardar la imagen seleccionada en Firebase Storage


    // Método para cargar los datos del food truck en la base de datos de Firebase Realtime
    private void uploadData() {
        String nom = nombre_FT.getText().toString();
        String pat = patente_FT.getText().toString();
        String des = descripcion_FT.getText().toString();
        String tel = telefono_FT.getText().toString();

        FoodTruck foodTruck = new FoodTruck(nom, pat, des, tel, imageURL);

        // Obtiene una referencia al nodo 'Foodtruck' y usa 'push()' para crear un nuevo nodo con un ID único
        DatabaseReference newFoodTruckRef = FirebaseDatabase.getInstance().getReference("Foodtruck").push();

        // Almacena el objeto FoodTruck en el nuevo nodo
        newFoodTruckRef.setValue(foodTruck).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AgregarFoodtruck.this, "Guardado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AgregarFoodtruck.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarFoodtruck.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}