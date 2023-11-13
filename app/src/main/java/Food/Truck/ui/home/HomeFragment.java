package Food.Truck.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Food.Truck.R;
import Food.Truck.activities.adaptadorFoodtruck;
import Food.Truck.databinding.FragmentHomeBinding;
import Food.Truck.indep_classes.FoodTruck;
import Food.Truck.ui.menu.MenuFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    /*
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager lManager;
    */

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
        // Crea una lista de FoodTrucks
        List<Food.Truck.indep_classes.FoodTruck> items = new ArrayList<>();
        items.add(new Food.Truck.indep_classes.FoodTruck(1, R.drawable.icons8_travel_64, "Don Humbreto", "Aquí venden buenas papas fritas"));
        items.add(new Food.Truck.indep_classes.FoodTruck(2, R.drawable.icons8_travel_64, "Altillo", "Aquí venden buenas chorrillanas"));
        items.add(new Food.Truck.indep_classes.FoodTruck(3, R.drawable.icons8_travel_64, "Rodriguez hot dogs", "Aquí venden buenos completos"));
        items.add(new Food.Truck.indep_classes.FoodTruck(4, R.drawable.icons8_travel_64, "Foodie Express", "Descripción de Foodie Express"));
        items.add(new Food.Truck.indep_classes.FoodTruck(5, R.drawable.icons8_travel_64, "Food Truck X", "Descripción del Food Truck X"));
        items.add(new Food.Truck.indep_classes.FoodTruck(6, R.drawable.icons8_travel_64, "Sabor Latino", "Descripción de Sabor Latino"));
        items.add(new Food.Truck.indep_classes.FoodTruck(7, R.drawable.icons8_travel_64, "Pizza Paradise", "Descripción de Pizza Paradise"));
        items.add(new Food.Truck.indep_classes.FoodTruck(8, R.drawable.icons8_travel_64, "Taco Town", "Descripción de Taco Town"));
        items.add(new Food.Truck.indep_classes.FoodTruck(9, R.drawable.icons8_travel_64, "Burger Bistro", "Descripción de Burger Bistro"));
        items.add(new Food.Truck.indep_classes.FoodTruck(10, R.drawable.icons8_travel_64, "Sushi Delight", "Descripción de Sushi Delight"));
        items.add(new Food.Truck.indep_classes.FoodTruck(11, R.drawable.icons8_travel_64, "Burrito Express", "Descripción de Burrito Express"));

        recyclerView = root.findViewById(R.id.rvFoodTK);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Crea un nuevo adaptador para el RecyclerView (Aqui esta el adaptador para el recycler)
        final adaptadorFoodtruck adapter = new adaptadorFoodtruck(items);

        // Establecer el OnItemClickListener que definiste en tu adaptador
        adapter.setOnItemClickListener(new adaptadorFoodtruck.OnItemClickListener() {
            @Override
            public void onItemClick(FoodTruck foodTruck) {
                // Usa el NavController para navegar al MenuFragment
                NavController navController = Navigation.findNavController(getView());
                Bundle args = new Bundle();
                args.putInt("foodTruckId", foodTruck.getId()); // Asegúrate de que FoodTruck tenga un método getId()
                navController.navigate(R.id.action_navigation_home_to_menuFragment, args);}
        });

        recyclerView.setAdapter(adapter);

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