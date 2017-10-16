package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", comentario.getDescripcion());
        values.put("puntos", comentario.getPuntaje());
        values.put("fecha_hora",comentario.getFecha().toString());
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
}
