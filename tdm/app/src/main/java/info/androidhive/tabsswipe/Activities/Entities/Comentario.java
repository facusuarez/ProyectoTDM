package info.androidhive.tabsswipe.Activities.Entities;

import java.sql.Time;
import java.util.Date;

/**
 * Created by USUARIO on 11/10/2017.
 */

public class Comentario {

    private String descripcion;
    private float puntaje;
    private Date fecha;
    private Profesor profesor;
    private int id_profesor;
    //private Usuario usuario;


    public Comentario() {
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
