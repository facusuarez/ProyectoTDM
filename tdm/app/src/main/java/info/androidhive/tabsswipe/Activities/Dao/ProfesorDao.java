package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.tabsswipe.Activities.Entities.Catedra;
import info.androidhive.tabsswipe.Activities.Entities.Comision;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 09/10/2017.
 */

public class ProfesorDao {
    private DBHelper dbHelper;

    public ProfesorDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insertProfesor(Profesor profesor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("apellido", profesor.getApellido());
        values.put("nombre", profesor.getNombre());
        int id = (int) db.insert("profesores", null, values);
        db.close();
        profesor.setId_profesor(id);
        return id;
    }

    public List<Profesor> obtenerProfesores() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("profesores", null, null, null, null, null, "puntaje desc");
        List<Profesor> profesores = null;
        if (cursor.isBeforeFirst()) {
            Profesor profe = null;
            int indexNombre = -1;
            int indexApellido = -1;
            int indexPuntaje=-1;
            int indexID = -1;
            profesores = new ArrayList<Profesor>();
            indexNombre = cursor.getColumnIndex("nombre");
            indexPuntaje=cursor.getColumnIndex("puntaje");
            indexApellido = cursor.getColumnIndex("apellido");
            indexID = cursor.getColumnIndex("id_profesor");
            while (cursor.moveToNext()) {
                profe = new Profesor();
                profe.setNombre(cursor.getString(indexNombre));
                profe.setPuntaje(cursor.getFloat(indexPuntaje));
                profe.setApellido(cursor.getString(indexApellido));
                profe.setId_profesor(cursor.getInt(indexID));
                profesores.add(profe);
            }
        }
        cursor.close();
        db.close();
        return profesores;
    }

    public List<Catedra> ObtenerCatedrasPorProfesor(int pIdProfesor) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String query = "SELECT c.*  FROM catedras AS c " +
                "INNER JOIN profe_catedra_comision AS pcc ON c.id_catedra == pcc.id WHERE pcc.id_profesor=?";
        Cursor cursor = db.rawQuery(query, new String[]{pIdProfesor + ""});
        List<Catedra> catedras = null;
        if (cursor.isBeforeFirst()) {
            Catedra catedra = null;
            int indexNombre = -1;
            int indexID = -1;
            catedras = new ArrayList<Catedra>();
            indexNombre = cursor.getColumnIndex("nombre");
            indexID = cursor.getColumnIndex("id_catedra");
            while (cursor.moveToNext()) {
                catedra = new Catedra();
                catedra.setNombre(cursor.getString(indexNombre));
                catedra.setId_catedra(cursor.getInt(indexID));
                catedras.add(catedra);
            }
        }
        cursor.close();
        db.close();
        return catedras;
    }

    public List<Comision> ObtenerComisionesPorProfesor(int pIdProfesor) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String query = "SELECT c.*  FROM comisiones AS c " +
                "INNER JOIN profe_catedra_comision AS pcc ON c.id_comision == pcc.id WHERE pcc.id_profesor=?";
        Cursor cursor = db.rawQuery(query, new String[]{pIdProfesor + ""});
        List<Comision> comisiones = null;
        if (cursor.isBeforeFirst()) {
            Comision comision = null;
            int indexNombre = -1;
            int indexID = -1;
            comisiones = new ArrayList<Comision>();
            indexNombre = cursor.getColumnIndex("nombre");
            indexID = cursor.getColumnIndex("id_comision");
            while (cursor.moveToNext()) {
                comision = new Comision();
                comision.setNombre(cursor.getString(indexNombre));
                comision.setId_comision(cursor.getInt(indexID));
                comisiones.add(comision);
            }
        }
        cursor.close();
        db.close();
        return comisiones;
    }

    //public List<String> ObtenerCatedraComision (int pIDProfesor){

    //}

    public long CantidadComentariosPorProfesor (int pIdProfesor){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        long count =DatabaseUtils.queryNumEntries(db,"comentario","id_profesor=?",new String[] {pIdProfesor+""});
        db.close();
        return count;
    }

    public int ActualizarPuntaje(float pPuntaje, int pIDProfe){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas;
        ContentValues cv=new ContentValues();
        cv.put("puntaje",pPuntaje);
        //db.execSQL("UPDATE profesores SET puntaje="+pPuntaje+" WHERE id_profesor=?",new Object[]{pIDProfe});
        filas=db.update("profesores", cv,"id_profesor=?",new String[]{pIDProfe+""});
        return filas;
    }

}
