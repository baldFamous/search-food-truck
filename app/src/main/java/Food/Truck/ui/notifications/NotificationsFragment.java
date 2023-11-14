package Food.Truck.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import Food.Truck.R;
import Food.Truck.activities.AgregarFoodtruck;
import Food.Truck.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RelativeLayout rlInfoPersonal = root.findViewById(R.id.rlAddFT);
        rlInfoPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la nueva actividad aquí
                Intent intent = new Intent(requireContext(), AgregarFoodtruck.class);
                startActivity(intent);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}