package info.androidhive.tabsswipe.Activities.Entities;

/**
 * Created by USUARIO on 09/10/2017.
 */

public class Catedra {

    private String nombre;
    private int id_catedra;


    public Catedra(String nombre, int id_catedra) {
        this.nombre = nombre;
        this.id_catedra=id_catedra;
    }

    public Catedra() {
    }


    public int getId_catedra() {
        return id_catedra;
    }

    public void setId_catedra(int id_catedra) {
        this.id_catedra = id_catedra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
