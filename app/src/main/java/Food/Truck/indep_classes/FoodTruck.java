package Food.Truck.indep_classes;

public class FoodTruck {

    private String Nombre;
    private String idPatente;
    private String Descripcion;
    private String Telefono;
    private String Imagen; //Se almacena la url
    public FoodTruck() {
        this.Nombre = "";
        this.idPatente = "";
        this.Descripcion = "";
        this.Telefono = "";
        this.Imagen = "";
    }
    public FoodTruck(String nombre, String patente, String descripcion, String telefono, String imagen) {

        Nombre = nombre;
        idPatente = patente;
        Descripcion = descripcion;
        Telefono = telefono;
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPatente() {
        return idPatente;
    }

    public void setPatente(String patente) {
        idPatente = patente;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

}

