package Food.Truck.indep_classes;

public class FoodTruck {
    private int id;
    private int imagen;
    private String Nombre;
    private String Descripcion;
    public FoodTruck(int id, int imagen, String nombre, String descripcion){
        this.id = id;
        this.Descripcion = descripcion;
        this.imagen = imagen;
        this.Nombre = nombre;
    }
    public int getId() {return id;}
    public String getNombre() {return Nombre;}

    public int getImagen() {return imagen;}

    public String getDescripcion() {return Descripcion;}

}

