package info.androidhive.tabsswipe.Activities.Entities;

/**
 * Created by USUARIO on 09/10/2017.
 */

public class Comision {

    private String nombre;
    private int id_comision;

    public Comision(String nombre) {
        this.nombre = nombre;
    }

    public Comision() {
    }


    public int getId_comision() {
        return id_comision;
    }

    public void setId_comision(int id_comision) {
        this.id_comision = id_comision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
