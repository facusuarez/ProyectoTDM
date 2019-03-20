package info.androidhive.masterlist.activities.Ranking;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;

import info.androidhive.masterlist.Constants;
import info.androidhive.masterlist.dao.ComentarioDao;
import info.androidhive.masterlist.entities.Comentario;
import info.androidhive.masterlist.json.JsonPostInsertComentario;
import info.androidhive.masterlist.R;

/**
 * Created by USUARIO on 11/10/2017.
 */

public class PuntuacionActivity extends Activity {
    private RatingBar _ratingBar;
    private int _idProfe;
    private TextView _tvProfe;
    private TextView _tvPuntos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);

        _ratingBar = (RatingBar) findViewById(R.id.ratingProfe);

        LayerDrawable stars = (LayerDrawable) _ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#0521F9"), PorterDuff.Mode.SRC_ATOP);
        _tvProfe = (TextView) findViewById(R.id.lblNombreProfesor);
        _tvPuntos = (TextView) findViewById(R.id.lblPuntuacion);
        mostrarDatos();

        Button btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarComentarios();
            }
        });

        _ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                _tvPuntos.setText(_ratingBar.getRating() + "");
            }
        });
    }

    private void mostrarDatos() {

        String profe = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            _idProfe = extras.getInt("idProfe");
            profe = extras.getString("nombreProfe");
        }

        _tvProfe.setText(profe);

    }

    private void enviarComentarios() {


        EditText etDescripcion = (EditText) findViewById(R.id.txtComentario);
        String descripcion = etDescripcion.getText().toString();
        ComentarioDao comentarioDao = new ComentarioDao(this);
        final Comentario comentario = new Comentario();
        comentario.setDescripcion(descripcion);
        comentario.setPuntaje(_ratingBar.getRating());
        comentario.setId_profesor(_idProfe);
        comentario.setFecha(new Date());
        comentario.setId_usuario(1);

        //int resultado = comentarioDao.insertarComentario(comentario);

        JsonPostInsertComentario post = new JsonPostInsertComentario(this, comentario);
        post.execute(Constants.URL_INSERT_COMENTARIO);

        //calcular puntuacion
        /*List<Comentario> comentarioList = comentarioDao.obtenerComentariosPorProfesor(_idProfe);
        Profesor profesor = new Profesor();
        profesor.setId_profesor(_idProfe);
        String puntos = profesor.calcularPuntuacion(comentarioList);
        ProfesorDao profesorDao = new ProfesorDao(this);
        profesorDao.ActualizarPuntaje(Float.parseFloat(puntos), _idProfe);
*/

        //JsonPostUpdatePuntaje postUpdatePuntaje = new JsonPostUpdatePuntaje(this, _idProfe);
        //postUpdatePuntaje.execute(URL_UPDATE_PUNTAJE,URL_AVG_COMENTARIO);


        /*if (resultado > 0) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Info");
            dialogBuilder.setMessage("Comentario enviado");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialogBuilder.create().show();
        }*/
        finish();
    }


}
