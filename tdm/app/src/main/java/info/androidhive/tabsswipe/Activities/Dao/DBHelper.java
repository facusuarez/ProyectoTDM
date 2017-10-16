package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USUARIO on 09/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME="TDM_MasterList";
    static final int DATABASE_VERSION=1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"profesores\" (\"id_profesor\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL,\"apellido\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"comisiones\" (\"id_comision\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"catedras\" (\"id_catedra\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"usuarios\" (\"id_usuario\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL,\"password\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"profe_catedra_comision\" (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"id_catedra\" INTEGER NOT NULL,\"id_comision\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_comision) REFERENCES comisiones(id_comision),FOREIGN KEY (id_catedra) REFERENCES catedras(id_catedra))");
        db.execSQL("CREATE TABLE \"comentario\" (\"id_comentario\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"descripcion\" TEXT NOT NULL,\"puntos\" FLOAT NOT NULL,\"fecha_hora\" TEXT NOT NULL,\"id_usuario\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        //db.execSQL("CREATE TABLE \"puntuacion\" (\"id_puntuacion\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"puntos\" INTEGER NOT NULL,\"fecha_hora\" DATETIME NOT NULL,\"id_usuario\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        db.execSQL("CREATE TABLE \"profesores_favoritos\" (\"id_favorito\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"id_usuario\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        cargarDatosIniciales(db);
    }

    private void cargarDatosIniciales(SQLiteDatabase db) {
        //profesores
        ContentValues values = new ContentValues();
        values.put("nombre","Cecilia");
        values.put("apellido","Sanchez");
        db.insert("profesores",null,values);
        values.clear();
        values.put("nombre","Valerio");
        values.put("apellido","Fritelli");
        db.insert("profesores",null,values);
        values.clear();
        values.put("nombre","Jose Luis");
        values.put("apellido","Galoppo");
        db.insert("profesores",null,values);
        values.clear();

        values.put("nombre","Redes de Información");
        db.insert("catedras",null,values);
        values.clear();
        values.put("nombre","Algoritmos y Estructuras de Datos");
        db.insert("catedras",null,values);
        values.clear();

        values.put("nombre","4K1");
        db.insert("comisiones",null,values);
        values.clear();
        values.put("nombre","4K4");
        db.insert("comisiones",null,values);
        values.clear();
        values.put("nombre","1K1");
        db.insert("comisiones",null,values);
        values.clear();

        values.put("id_profesor","1");
        values.put("id_comision","1");
        values.put("id_catedra","1");
        db.insert("profe_catedra_comision",null,values);
        values.clear();
        values.put("id_profesor","2");
        values.put("id_comision","3");
        values.put("id_catedra","2");
        db.insert("profe_catedra_comision",null,values);
        values.clear();
        values.put("id_profesor","3");
        values.put("id_comision","2");
        values.put("id_catedra","1");
        db.insert("profe_catedra_comision",null,values);
        values.clear();

        values.put("nombre","admin");
        values.put("password","Admin00");
        db.insert("usuarios",null,values);
        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

