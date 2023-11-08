package Food.Truck.indep_classes;

public class FoodTruck {
    private int imagen;
    private String Nombre;
    private String Descripcion;
    public FoodTruck(int i, String n, String d){
        this.Descripcion = d;
        this.imagen = i;
        this.Nombre = n;
    }

    public String getNombre() {return Nombre;}

    public int getImagen() {return imagen;}

    public String getDescripcion() {return Descripcion;}

}

