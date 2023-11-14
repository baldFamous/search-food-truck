package Food.Truck.indep_classes;

public class usuario_propi {

    String nombre;
    String apellido;
    String correo;
    String contraseña;
    String reg_foodtruck;

    public usuario_propi(String nombre, String apellido, String correo, String contraseña, String reg_foodtruck) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.reg_foodtruck = reg_foodtruck;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getReg_foodtruck() {
        return reg_foodtruck;
    }
}
