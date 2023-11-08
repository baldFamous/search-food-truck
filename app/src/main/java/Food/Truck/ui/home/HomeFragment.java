package Food.Truck.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Food.Truck.R;
import Food.Truck.activities.adaptadorFoodtruck;
import Food.Truck.databinding.FragmentHomeBinding;
import Food.Truck.indep_classes.FoodTruck;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager lManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<Food.Truck.indep_classes.FoodTruck> items = new ArrayList<>();
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Don Humbreto", "Aquí venden buenas papas fritas"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Altillo", "Aquí venden buenas chorrillanas"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Rodriguez hot dogs", "Aquí venden buenos completos"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Foodie Express", "Descripción de Foodie Express"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Food Truck X", "Descripción del Food Truck X"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Sabor Latino", "Descripción de Sabor Latino"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Pizza Paradise", "Descripción de Pizza Paradise"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Taco Town", "Descripción de Taco Town"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Burger Bistro", "Descripción de Burger Bistro"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Sushi Delight", "Descripción de Sushi Delight"));
        items.add(new Food.Truck.indep_classes.FoodTruck(R.drawable.icons8_travel_64, "Burrito Express", "Descripción de Burrito Express"));
        items.add(new FoodTruck(R.drawable.icons8_travel_64, "Sweet Treats", "Descripción de Sweet Treats"));

        recyclerView = root.findViewById(R.id.rvFoodTK);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Food.Truck.activities.adaptadorFoodtruck adapter = new adaptadorFoodtruck(items);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}