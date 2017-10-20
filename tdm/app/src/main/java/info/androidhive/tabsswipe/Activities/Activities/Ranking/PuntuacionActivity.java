package info.androidhive.tabsswipe.Activities.Activities.Ranking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import info.androidhive.tabsswipe.Activities.Dao.ComentarioDao;
import info.androidhive.tabsswipe.Activities.Entities.Comentario;
import info.androidhive.tabsswipe.R;

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
        _tvProfe= (TextView) findViewById(R.id.lblNombreProfesor);
        _tvPuntos= (TextView) findViewById(R.id.lblPuntuacion);
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
                _tvPuntos.setText(_ratingBar.getRating()+"");
            }
        });
    }

    private void mostrarDatos() {
        float rating = 0;
        String profe = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rating = extras.getFloat("rating");
            _idProfe = extras.getInt("idProfe");
            profe= extras.getString("nombreProfe");
        }
        _ratingBar.setRating(rating);
        _tvProfe.setText(profe);
        _tvPuntos.setText(rating+"");
    }

    private void enviarComentarios() {


        EditText etDescripcion = (EditText) findViewById(R.id.txtComentario);
        String descripcion = etDescripcion.getText().toString();
        ComentarioDao comentarioDao = new ComentarioDao(this);
        Comentario comentario = new Comentario();
        comentario.setDescripcion(descripcion);
        comentario.setPuntaje(_ratingBar.getRating());
        comentario.setId_profesor(_idProfe);


        comentario.setFecha(new Date());

        int resultado = comentarioDao.insertarComentario(comentario);


        if (resultado > 0) {
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

        }
    }


}
