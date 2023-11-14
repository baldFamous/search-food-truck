package Food.Truck.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Food.Truck.R;
import Food.Truck.activities.Inicio;
import Food.Truck.activities.adaptadorFoodtruck;
import Food.Truck.databinding.FragmentHomeBinding;
import Food.Truck.indep_classes.FoodTruck;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    RecyclerView recyclerView;
    List<FoodTruck> dataFT;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    /**
     * Este método se llama cuando se crea el fragmento.
     *
     * @param inflater           El inflador de diseño.
     * @param container          El contenedor de vistas en el que se creará el fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si lo hay.
     * @return La vista raíz del fragmento.
     **/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Se infla el diseño del fragmento utilizando View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtiene la referencia al RecyclerView del diseño
        recyclerView = root.findViewById(R.id.rvFoodTK);

        // Se crea un GridLayoutManager con una sola columna
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);

        // Establece el LayoutManager del RecyclerView
        recyclerView.setLayoutManager(gridLayoutManager);
        // Se inicializa la lista de FoodTrucks y se crea un adaptador para el RecyclerView
        dataFT = new ArrayList<>();
        adaptadorFoodtruck adaptador = new adaptadorFoodtruck(getActivity(), dataFT);
        recyclerView.setAdapter(adaptador);

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodTruck");

        // Se agrega un ValueEventListener para escuchar cambios en los datos de la base de datos
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Se limpia la lista de FoodTrucks
                dataFT.clear();
                // Se recorre cada elemento en el snapshot de la base de datos
                for (DataSnapshot itemSnapShot: snapshot.getChildren()){
                    // Se obtiene un objeto FoodTruck a partir de los datos en el snapshot
                    FoodTruck foodTruck = itemSnapShot.getValue(FoodTruck.class);
                    // Se establece la clave (key) del objeto FoodTruck
                    foodTruck.setPatente(itemSnapShot.getKey());
                    // Se agrega el FoodTruck a la lista
                    dataFT.add(foodTruck);
                }
                // Se notifica al adaptador que los datos han cambiado
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores de lectura de la base de datos
                String errorMessage = "Error al leer la base de datos: " + error.getMessage();
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        // Se retorna la vista raíz del fragmento
        return root;
    }

    /**
     * Este método se llama cuando se destruye el fragmento.
     */
    @Override
    public void onDestroyView() {
        // Se anula la variable de View Binding cuando el fragmento es destruido
        super.onDestroyView();
        binding = null;
    }
}