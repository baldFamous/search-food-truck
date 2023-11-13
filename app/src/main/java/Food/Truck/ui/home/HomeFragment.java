package Food.Truck.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtiene la referencia al RecyclerView
        recyclerView = root.findViewById(R.id.rvFoodTK);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        // Establece el LayoutManager del RecyclerView
        recyclerView.setLayoutManager(gridLayoutManager);

        dataFT = new ArrayList<>();
        adaptadorFoodtruck adaptador = new adaptadorFoodtruck(getActivity(), dataFT);
        recyclerView.setAdapter(adaptador);

        databaseReference = FirebaseDatabase.getInstance().getReference("Foodtruck");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataFT.clear();
                for (DataSnapshot itemSnapShot: snapshot.getChildren()){
                    FoodTruck foodTruck = itemSnapShot.getValue(FoodTruck.class);
                    foodTruck.setKey(itemSnapShot.getKey());
                    dataFT.add(foodTruck);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    /**
     * Este método se llama cuando se destruye el fragmento.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}