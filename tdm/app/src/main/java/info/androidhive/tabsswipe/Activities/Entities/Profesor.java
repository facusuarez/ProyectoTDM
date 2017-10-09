package info.androidhive.tabsswipe.Activities.Entities;

/**
 * Created by USUARIO on 07/10/2017.
 */

public class Profesor {
    private String nombre;
    private String apellido;

    public Profesor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Profesor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
