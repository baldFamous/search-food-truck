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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import Food.Truck.R;
import Food.Truck.indep_classes.FoodTruck;

public class adaptadorFoodtruck extends RecyclerView.Adapter<adaptadorFoodtruck.ViewHolder> {

    private Context context;
    private List<FoodTruck> items;

    // Constructor para inicializar el adaptador con una lista de FoodTrucks
    public adaptadorFoodtruck(FragmentActivity activity, List<FoodTruck> items) {
        this.context = activity;
        this.items = items;
    }

    // Este método se llama cuando se necesita crear un nuevo ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla el diseño del elemento de la lista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodtruck_cardview, parent, false);

        // Crea un nuevo ViewHolder y lo devuelve
        return new ViewHolder(v);
    }

    // Este método enlaza los datos del FoodTruck con el ViewHolder para cada cardview
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Carga la imagen del FoodTruck utilizando Glide para la eficiencia
        Glide.with(context).load(items.get(position).getImagen()).into(viewHolder.imagen);

        // Establece los datos del FoodTruck en las vistas del ViewHolder
        viewHolder.nombre.setText("Nombre: " + items.get(position).getNombre());
        viewHolder.descripcion.setText("Descripción: " + items.get(position).getDescripcion());

        // Configura un OnClickListener para abrir detalles cuando se hace clic en una tarjeta
        viewHolder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un intent para abrir la actividad de detalles
                Intent intent = new Intent(context, Details_activity.class);
                // Pasa información adicional a la actividad de detalles
                intent.putExtra("Image", items.get(viewHolder.getAdapterPosition()).getImagen());
                intent.putExtra("Nombre", items.get(viewHolder.getAdapterPosition()).getNombre());
                intent.putExtra("Descripcion", items.get(viewHolder.getAdapterPosition()).getDescripcion());
                intent.putExtra("Telefono", items.get(viewHolder.getAdapterPosition()).getTelefono());
                intent.putExtra("key", items.get(viewHolder.getAdapterPosition()).getKey());
                // Inicia la actividad de detalles
                context.startActivity(intent);
            }
        });
    }

    // Este método devuelve el total de foodtrucks en la lista
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Esta clase representa el ViewHolder que contendrá las vistas de un elemento de la lista
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre, descripcion;
        CardView recCard;

        // Constructor que obtiene referencias a las vistas del elemento de la lista
        public ViewHolder(View v) {
            super(v);
            imagen = v.findViewById(R.id.imagen);
            recCard = v.findViewById(R.id.recard);
            nombre = v.findViewById(R.id.txtNombre);
            descripcion = v.findViewById(R.id.txtDescripcion);
        }
    }
}
