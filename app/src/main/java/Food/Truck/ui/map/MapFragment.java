package Food.Truck.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import Food.Truck.R;
import Food.Truck.databinding.FragmentMapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;
    private FragmentMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel dashboardViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {

                // Cambio de tipo de mapa
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (checkLocationPermission()) {
                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    requestLocationPermission();
                }

                map.setBuildingsEnabled(true);

                // Añadir marcadores
                MarkerOptions markerOptions1 = new MarkerOptions();
                markerOptions1.position(new LatLng(-29.970997569466732, -71.24633510665917));
                markerOptions1.title("brazilian food");
                markerOptions1.snippet("comida brasileña");
                map.addMarker(markerOptions1);

                MarkerOptions markerOptions2 = new MarkerOptions();
                markerOptions2.position(new LatLng(-29.970605481554266, -71.24784202315534));
                markerOptions2.title("chinese food");
                markerOptions2.snippet("comida china");
                map.addMarker(markerOptions2);

                MarkerOptions markerOptions3 = new MarkerOptions();
                markerOptions3.position(new LatLng(-29.972563938777128, -71.24865542768423));
                markerOptions3.title("italian food");
                markerOptions3.snippet("comida italiana");
                map.addMarker(markerOptions3);

                MarkerOptions markerOptions4 = new MarkerOptions();
                markerOptions4.position(new LatLng(-29.89869062240124, -71.2594874540488));
                markerOptions4.title("el vecino");
                markerOptions4.snippet("comida rapida");
                map.addMarker(markerOptions4);

                MarkerOptions markerOptions5 = new MarkerOptions();
                markerOptions5.position(new LatLng(-29.909766837528732, -71.25677470072809));
                markerOptions5.title("frente la uls");
                markerOptions5.snippet("comida rapida");
                map.addMarker(markerOptions5);


                // Add controls
                map.getUiSettings().setZoomControlsEnabled(true);
                map.getUiSettings().setCompassEnabled(true);

            }
        });

        return root;
    }

    private boolean checkLocationPermission() {
        int permission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de ubicación concedido
                if (mapView != null) {
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            if (checkLocationPermission()) {
                                googleMap.setMyLocationEnabled(true);
                                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                            }
                        }
                    });
                }
            } else {
                // Permiso de ubicación denegado
                Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}