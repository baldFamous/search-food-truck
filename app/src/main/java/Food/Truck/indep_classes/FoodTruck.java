package Food.Truck.indep_classes;

import android.net.Uri;

public class FoodTruck {

    private String Nombre;
    private String Patente;
    private String Descripcion;
    private String Telefono;
    private String Imagen;
    public FoodTruck(String nombre, String patente, String descripcion, String telefono, String imagen) {
        Nombre = nombre;
        Patente = patente;
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
        return Patente;
    }

    public void setPatente(String patente) {
        Patente = patente;
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

