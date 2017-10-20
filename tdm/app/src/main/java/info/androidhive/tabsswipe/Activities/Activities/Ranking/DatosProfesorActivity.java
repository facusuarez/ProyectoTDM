package info.androidhive.tabsswipe.Activities.Activities.Ranking;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import info.androidhive.tabsswipe.Activities.Activities.Adapter.ComentariosAdapter;
import info.androidhive.tabsswipe.Activities.Dao.ComentarioDao;
import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Catedra;
import info.androidhive.tabsswipe.Activities.Entities.Comentario;
import info.androidhive.tabsswipe.Activities.Entities.Comision;
import info.androidhive.tabsswipe.R;

/**
 * Created by USUARIO on 10/10/2017.
 */

public class DatosProfesorActivity extends ListActivity {

    private ComentariosAdapter _adapter;

    private int _idProfe;
    private TextView _tvNombre;
    private RatingBar _rbProfe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_profesor);

        mostrarDetalle();
        _rbProfe = (RatingBar) findViewById(R.id.ratingProfe);
        LayerDrawable stars = (LayerDrawable) _rbProfe.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#0521F9"), PorterDuff.Mode.SRC_ATOP);
        _rbProfe.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                puntuar(rating);
            }
        });
    }

    private void puntuar(float rating) {
        Intent i = new Intent(this, PuntuacionActivity.class);
        i.putExtra("idProfe", _idProfe);
        i.putExtra("rating", rating);
        i.putExtra("nombreProfe", _tvNombre.getText());
        startActivity(i);
    }

    public void mostrarDetalle() {
        _tvNombre = (TextView) findViewById(R.id.txtNombre);
        String nombreProfe;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            nombreProfe = "Ningún profesor seleccionado";
        } else {
            nombreProfe = extras.getString("nombreProfe");
            _idProfe = extras.getInt("idProfe");
        }
        _tvNombre.setText(nombreProfe);

        ProfesorDao profesorDao = new ProfesorDao(this);

        TextView tvMaterias = (TextView) findViewById(R.id.lblMaterias);
        List<Catedra> catedras = profesorDao.ObtenerCatedrasPorProfesor(_idProfe);
        for (int i = 0; i < catedras.size(); i++) {
            if (i == 0) tvMaterias.setText(" " + catedras.get(i).getNombre());
            else tvMaterias.setText(tvMaterias.getText() + "\n" + catedras.get(i).getNombre());
        }

        TextView tvComisiones = (TextView) findViewById(R.id.lblComisiones);
        List<Comision> comisiones = profesorDao.ObtenerComisionesPorProfesor(_idProfe);
        for (int i = 0; i < comisiones.size(); i++) {
            if (i == 0) tvComisiones.setText(" " + comisiones.get(i).getNombre());
            else
                tvComisiones.setText(tvComisiones.getText() + " - " + comisiones.get(i).getNombre());
        }

        actualizarComentarios(profesorDao);
    }

    private void actualizarComentarios(ProfesorDao profesorDao) {
        TextView tvCantComentarios = (TextView) findViewById(R.id.lblCantidadComentarios);
        long count = profesorDao.CantidadComentariosPorProfesor(_idProfe);
        if (count == 0) {
            tvCantComentarios.setText("Sin comentarios");
        } else if (count == 1) {
            tvCantComentarios.setText("1 comentario");
        } else if (count > 1) {
            tvCantComentarios.setText(count + " comentarios");
        }

        cargarComentarios();
    }

    private void cargarComentarios() {
        _adapter = new ComentariosAdapter(this);
        ComentarioDao comentarioDao= new ComentarioDao(this);
        List<Comentario> comentarioLis= comentarioDao.obtenerComentariosPorProfesor(_idProfe);

        _adapter.setLista(comentarioLis);
        setListAdapter(_adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //_rbProfe.setRating(0);
        actualizarComentarios(new ProfesorDao(this));
    }
}
