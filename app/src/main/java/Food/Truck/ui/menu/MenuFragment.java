package Food.Truck.ui.menu;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import Food.Truck.R;

public class MenuFragment extends Fragment {

    private RecyclerView rvMenuItems;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menufoodtruck, container, false);
        rvMenuItems = view.findViewById(R.id.rvMenuItems);
        // Suponiendo que tienes un método para obtener los ítems del menú y configurar el adaptador
        // setupRecyclerView();
        return view;
    }

    // Método para configurar el RecyclerView (ejemplo)
    private void setupRecyclerView() {
        // Configura tu RecyclerView con un LayoutManager y un adaptador aquí
    }
}

