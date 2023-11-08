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
     El archivo list_foodtruck_cardview.xml contiene las vistas que se utilizan para representar un elemento de la lista. Estas vistas incluyen una imagen, un texto y una descripción.
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodtruck_cardview, parent, false);
        return new ViewHolder(v);
    }

    // Enlaza los datos del contacto con el ViewHolder
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FoodTruck foodTuck = items.get(position);
        viewHolder.imagen.setImageResource(items.get(position).getImagen());
        viewHolder.nombre.setText("Nombre:" + items.get(position).getNombre());
        viewHolder.descripcion.setText("Descripcion:" + items.get(position).getDescripcion());
    }

    // Devuelve el total de foodtrucks en la lista
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;


        public ViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.txtNombre);
            descripcion = (TextView) v.findViewById(R.id.txtDescripcion);
        }
    }
}
