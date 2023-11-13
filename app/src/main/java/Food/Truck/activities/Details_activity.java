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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.Reference;

import Food.Truck.R;

public class Details_activity extends AppCompatActivity {

    TextView NombreFT, DetailsFT,TelefonoFT;
    ImageView imagen;
    Button deleteBT;
    String key = "";
    String imageUrl ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_foodtruck);
        NombreFT =findViewById(R.id.txtNombreFT);
        DetailsFT =findViewById(R.id.txtDescripcionFT);
        TelefonoFT =findViewById(R.id.txtTelefono);
        deleteBT = findViewById(R.id.idftEliminar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            DetailsFT.setText(bundle.getString("Descripcion"));
            NombreFT.setText(bundle.getString("Nombre"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(imagen);

        }
        deleteBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("FoodTruck");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
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
            }
        });
    }
}