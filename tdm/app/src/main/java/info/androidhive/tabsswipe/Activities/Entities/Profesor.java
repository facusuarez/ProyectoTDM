package info.androidhive.tabsswipe.Activities.Entities;

/**
 * Created by USUARIO on 07/10/2017.
 */

public class Profesor {
    private String nombre;
    private String apellido;
    private int id_profesor;

    public Profesor(String nombre, String apellido, int id_profesor) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_profesor=id_profesor;
    }

    public Profesor() {
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
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
