package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 09/10/2017.
 */

public class ProfesorDao {
    private DBHelper dbHelper;

    public ProfesorDao(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insertProfesor (Profesor profesor){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("apellido", profesor.getApellido());
        values.put("nombre", profesor.getNombre());
        int id = (int)db.insert("profesores", null, values);
        db.close();
        profesor.setId_profesor(id);
        return id;
    }

    public List<Profesor> obtenerProfesores(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("profesores",null,null,null,null,null,null);
        List<Profesor> profesores = null;
        if (cursor.isBeforeFirst()) {
            Profesor profe = null;
            int indexNombre = -1;
            int indexApellido = -1;
            int indexID=-1;
            profesores = new ArrayList<Profesor>();
            indexNombre = cursor.getColumnIndex("nombre");
            indexApellido = cursor.getColumnIndex("apellido");
            indexID= cursor.getColumnIndex("id_profesor");
            while (cursor.moveToNext()) {
                profe = new Profesor();
                profe.setNombre(cursor.getString(indexNombre));
                profe.setApellido(cursor.getString(indexApellido));
                profe.setId_profesor(cursor.getInt(indexID));
                profesores.add(profe);
            }
        }
        cursor.close();
        db.close();
        return profesores;
    }
}
