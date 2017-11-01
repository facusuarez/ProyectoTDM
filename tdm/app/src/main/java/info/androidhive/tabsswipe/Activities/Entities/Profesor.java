package info.androidhive.tabsswipe.Activities.Entities;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by USUARIO on 07/10/2017.
 */

public class Profesor {
    private String nombre;
    private String apellido;
    private int id_profesor;
    private float puntaje;

    public Profesor(String nombre, String apellido, int id_profesor, float puntaje) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_profesor = id_profesor;
        this.puntaje = puntaje;
    }

    public Profesor() {
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
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

    public String calcularPuntuacion(List<Comentario> comentarioList) {
        float sum = 0;
        float prom;
        for (Comentario c : comentarioList
                ) {
            sum += c.getPuntaje();
        }
        prom = sum / comentarioList.size();
        this.puntaje=prom;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(prom);

    }

}
