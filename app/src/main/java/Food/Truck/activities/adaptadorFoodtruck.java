package Food.Truck.activities;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import Food.Truck.R;
import Food.Truck.indep_classes.FoodTruck;
import Food.Truck.ui.home.HomeFragment;

public class adaptadorFoodtruck extends RecyclerView.Adapter<adaptadorFoodtruck.ViewHolder> {

    private Context context;
    private List<FoodTruck> items;

    public adaptadorFoodtruck(List<FoodTruck> items) {

        this.items = items;
    }

    /***
     Inflar significa crear una vista a partir de un archivo de diseño XML.
     El método onCreateViewHolder() utiliza el método inflate() para inflar el diseño del elemento de la lista.
     El diseño del elemento de la lista se encuentra en el archivo foodtruck_cardview.xml.
     * El archivo foodtruck_cardview.xml contiene las vistas que se utilizan para representar un elemento de la lista. Estas vistas incluyen una imagen, un texto y una descripción.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla el diseño del elemento de la lista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodtruck_cardview, parent, false);

        // Crea un nuevo ViewHolder y lo devuelve
        return new ViewHolder(v);
    }

    // Enlaza los datos del FoodTruck con el ViewHolder para cada cardview
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Glide.with(context).load(items.get(position).getImagen()).into(viewHolder.imagen);

        // Establece los datos del FoodTruck en las vistas del ViewHolder
        viewHolder.imagen.setImageResource(Integer.parseInt(items.get(position).getImagen()));
        viewHolder.nombre.setText("Nombre: " + items.get(position).getNombre());
        viewHolder.descripcion.setText("Descripción: " + items.get(position).getDescripcion());

        viewHolder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details_activity.class);
                intent.putExtra("Image", items.get(viewHolder.getAdapterPosition()).getImagen());
                intent.putExtra("Nombre", items.get(viewHolder.getAdapterPosition()).getNombre());
                intent.putExtra("Descripcion", items.get(viewHolder.getAdapterPosition()).getDescripcion());

                context.startActivity(intent);
            }
        });
    }

    // Devuelve el total de foodtrucks en la lista
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;  // Cambiado de Uri a ImageView
        public TextView nombre, patente , descripcion, telefono;
        CardView recCard;

        public ViewHolder(View v) {
            super(v);

            // Obtiene las referencias a las vistas del elemento de la lista
            imagen = (ImageView) v.findViewById(R.id.imagen);
            recCard = v.findViewById(R.id.recard);
            nombre = (TextView) v.findViewById(R.id.txtNombre);
            descripcion = (TextView) v.findViewById(R.id.txtDescripcion);
        }
    }
}
