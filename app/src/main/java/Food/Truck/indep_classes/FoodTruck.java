package Food.Truck.indep_classes;

public class FoodTruck {
    private String Key;
    private String Nombre;
    private String Patente;
    private String Descripcion;
    private String Telefono;
    private String Imagen;

    public FoodTruck() {
        this.Key = "";
        this.Nombre = "";
        this.Patente = "";
        this.Descripcion = "";
        this.Telefono = "";
        this.Imagen = "";
    }
    public FoodTruck(String nombre, String patente, String descripcion, String telefono, String imagen) {
        Nombre = nombre;
        Patente = patente;
        Descripcion = descripcion;
        Telefono = telefono;
        Imagen = imagen;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getPatente() {
        return Patente;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getImagen() {
        return Imagen;
    }
}

