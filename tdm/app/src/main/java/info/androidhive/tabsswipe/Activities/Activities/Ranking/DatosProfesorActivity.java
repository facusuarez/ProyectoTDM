package info.androidhive.tabsswipe.Activities.Activities.Ranking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import info.androidhive.tabsswipe.R;

/**
 * Created by USUARIO on 10/10/2017.
 */

public class DatosProfesorActivity extends Activity {

    private int _idProfe;
    private TextView _tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_profesor);

        mostrarDetalle();
        RatingBar rb = (RatingBar) findViewById(R.id.ratingProfe);
        LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#0521F9"), PorterDuff.Mode.SRC_ATOP);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                puntuar(rating);
            }
        });
    }

    private void puntuar(float rating) {
        Intent i = new Intent(this,PuntuacionActivity.class);
        i.putExtra("idProfe",_idProfe);
        i.putExtra("rating",rating);
        i.putExtra("nombreProfe",_tvNombre.getText());
        startActivity(i);
    }

    public void mostrarDetalle() {
        _tvNombre = (TextView) findViewById(R.id.txtNombre);
        String nombreProfe = "";
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            nombreProfe = "Ningún profesor seleccionado";
        } else {
            nombreProfe = extras.getString("nombreProfe");
            _idProfe = extras.getInt("idProfe");
        }
        _tvNombre.setText(nombreProfe);
    }


}
