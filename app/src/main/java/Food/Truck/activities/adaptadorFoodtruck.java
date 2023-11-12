package Food.Truck.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import Food.Truck.R;
import Food.Truck.indep_classes.FoodTruck;

import java.util.List;

public class adaptadorFoodtruck extends RecyclerView.Adapter<adaptadorFoodtruck.ViewHolder> {
    private List<FoodTruck> items;

    public adaptadorFoodtruck(List<FoodTruck> items) {
        this.items = items;
    }

    /***
     Inflar significa crear una vista a partir de un archivo de diseño XML.
     El método onCreateViewHolder() utiliza el método inflate() para inflar el diseño del elemento de la lista.
     El diseño del elemento de la lista se encuentra en el archivo list_foodtruck_cardview.xml.
     * El archivo list_foodtruck_cardview.xml contiene las vistas que se utilizan para representar un elemento de la lista. Estas vistas incluyen una imagen, un texto y una descripción.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla el diseño del elemento de la lista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodtruck_cardview, parent, false);

        // Crea un nuevo ViewHolder y lo devuelve
        return new ViewHolder(v);
    }

    // Enlaza los datos del contacto con el ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Obtiene el FoodTruck actual de la lista
        FoodTruck foodTruck = items.get(position);

        // Establece los datos del FoodTruck en las vistas del ViewHolder
        viewHolder.imagen.setImageResource(foodTruck.getImagen());
        viewHolder.nombre.setText("Nombre: " + foodTruck.getNombre());
        viewHolder.descripcion.setText("Descripción: " + foodTruck.getDescripcion());
    }

    // Devuelve el total de foodtrucks en la lista
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;

        public ViewHolder(View v) {
            super(v);

            // Obtiene las referencias a las vistas del elemento de la lista
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.txtNombre);
            descripcion = (TextView) v.findViewById(R.id.txtDescripcion);
        }
    }
}
