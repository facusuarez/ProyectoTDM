package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.androidhive.tabsswipe.Activities.Entities.Comentario;

/**
 * Created by USUARIO on 11/10/2017.
 */

public class ComentarioDao {

    private DBHelper dbHelper;

    public ComentarioDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insertarComentario(Comentario comentario) {

        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss",Locale.getDefault());
        Date date = comentario.getFecha();
        String fechaString = dateFormat.format(date);




        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", comentario.getDescripcion());
        values.put("puntos", comentario.getPuntaje());
        values.put("fecha_hora", fechaString);
        values.put("id_usuario", 1);
        values.put("id_profesor", comentario.getId_profesor());
        int id = (int) db.insert("comentario", null, values);
        //db.execSQL("CREATE TABLE \"comentario\" (\"id_comentario\" INTEGER PRIMARY KEY AUTOINCREMENT,
        // \"id_profesor\" INTEGER NOT NULL,\"descripcion\" TEXT NOT NULL,\"puntos\" FLOAT NOT NULL,
        // \"fecha_hora\" TEXT NOT NULL,\"id_usuario\" INTEGER NOT NULL,
        // FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),
        // FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        db.close();
        return id;
    }

    public List<Comentario> obtenerComentariosPorProfesor(int pIdProfesor) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //String query = "SELECT * FROM come";
        Cursor cursor = db.query("comentario",null,"id_profesor=?",new String [] {pIdProfesor+""},null,null,"fecha_hora");
        List<Comentario> comentarios = null;
        if (cursor.isBeforeFirst()) {
            Comentario comentario ;
            int indexDescripcion;
            int indexPuntaje;
            int indexID;
            int indexProfesor;
            int indexFecha;
            comentarios = new ArrayList<Comentario>();
            indexDescripcion = cursor.getColumnIndex("descripcion");
            indexFecha = cursor.getColumnIndex("fecha_hora");
            indexProfesor = cursor.getColumnIndex("id_profesor");
            indexPuntaje = cursor.getColumnIndex("puntos");
            indexID = cursor.getColumnIndex("id_comentario");
            while (cursor.moveToNext()) {
                comentario = new Comentario();

                String fechaString = cursor.getString(indexFecha);
                Date fecha;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());
                try {
                    fecha=dateFormat.parse(fechaString);
                    System.out.println(fecha);
                } catch (ParseException ex) {
                    fecha=null;
                    ex.printStackTrace();
                }

                comentario.setFecha(fecha);
                comentario.setId_profesor(cursor.getInt(indexProfesor));
                comentario.setPuntaje(cursor.getFloat(indexPuntaje));
                comentario.setDescripcion(cursor.getString(indexDescripcion));
                comentarios.add(comentario);
            }
        }
        cursor.close();
        db.close();
        return comentarios;
    }
}
