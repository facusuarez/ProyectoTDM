package info.androidhive.masterlist.activities.Ranking;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import info.androidhive.masterlist.R;

/**
 * Created by USUARIO on 15/11/2017.
 */

public class MateriasActivity extends Activity {

    private TextView _tvMaterias;
    private TextView _tvNombreProfesor;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);
        _tvMaterias= (TextView) findViewById(R.id.lblMaterias2);
        _tvNombreProfesor=(TextView) findViewById(R.id.lblProfesor);

        cargarDatos();
    }

    private void cargarDatos() {

        String nombreProfe;
        String materias="";
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            nombreProfe = "Ningún profesor seleccionado";
        } else {
            nombreProfe = extras.getString("nombreProfe");
            materias = extras.getString("materias");

        }
        _tvNombreProfesor.setText(nombreProfe);
        _tvMaterias.setText(materias);
    }
}
