package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import info.androidhive.tabsswipe.Activities.Entities.Comision;

/**
 * Created by USUARIO on 12/11/2017.
 */

public class ComisionDao {
    private DBHelper dbHelper;

    public ComisionDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insertComision (Comision comision){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre",comision.getNombre());
        values.put("id_comision",comision.getId_comision());
        int id = (int) db.insert("comisiones", null, values);
        db.close();
        return id;
    }

}
