package info.androidhive.tabsswipe.Activities.Activities.Ranking;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.tabsswipe.Activities.Activities.Adapter.ComentariosAdapter;
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
    private TextView _tvMaterias;
    private Button _btnOpinar;
    private Button _btnMaterias;
    private String _materias;

    private final static String URL_COMENTARIO = "http://www.masterlist.somee.com/WebService.asmx/getComentarios?idProfe=";
    private final static String URL_MATERIAS = "http://www.masterlist.somee.com/WebService.asmx/getProfeCatedraComision?idProfe=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_profesor);


        _btnOpinar = (Button) findViewById(R.id.btnOpinar);

        _rbProfe = (RatingBar) findViewById(R.id.ratingProfe);
        LayerDrawable stars = (LayerDrawable) _rbProfe.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#0521F9"), PorterDuff.Mode.SRC_ATOP);
        _rbProfe.setIsIndicator(true);

        _btnOpinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puntuar();
            }
        });
        mostrarDetalle();

        _btnMaterias= (Button) findViewById(R.id.btnMaterias);
        _btnMaterias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirMaterias();
            }
        });

    }

    private void puntuar() {
        Intent i = new Intent(this, PuntuacionActivity.class);
        i.putExtra("idProfe", _idProfe);
        i.putExtra("nombreProfe", _tvNombre.getText());
        startActivity(i);
    }

    private void abrirMaterias(){
        Intent i = new Intent(this, MateriasActivity.class);
        i.putExtra("materias",_materias);
        i.putExtra("nombreProfe",_tvNombre.getText());
        startActivity(i);
    }

    public void mostrarDetalle() {
        _tvNombre = (TextView) findViewById(R.id.txtNombre);
        String nombreProfe;
        float rating = 0;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            nombreProfe = "Ningún profesor seleccionado";
        } else {
            nombreProfe = extras.getString("nombreProfe");
            _idProfe = extras.getInt("idProfe");
            rating = (float) extras.getDouble("rating");
        }
        _tvNombre.setText(nombreProfe);
        _rbProfe.setRating(rating);

        JsonReaderMaterias reader = new JsonReaderMaterias(this);
        reader.execute(URL_MATERIAS);
    }

    private void cargarComentarios() {
        _adapter = new ComentariosAdapter(this);
        JsonReaderComentarios reader = new JsonReaderComentarios(this);
        reader.execute(URL_COMENTARIO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //_rbProfe.setRating(0);
        cargarComentarios();
    }

    class JsonReaderComentarios extends AsyncTask<String, Void, JSONObject> {
        private Exception error;
        private Context _context;

        public JsonReaderComentarios(Context context) {
            _context = context;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String outputData;
            try

            {
                HttpClient client = new DefaultHttpClient();
                HttpUriRequest request = new HttpGet(strings[0] + _idProfe);
                HttpResponse response = client.execute(request);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    StringBuilder builder = new StringBuilder();
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    //59,2
                    outputData = builder.toString();
                    try {
                        return new JSONObject(outputData);
                    } catch (JSONException e) {
                        error = e;
                    }
                } else {
                    throw new Exception("Status code != 200: " + statusCode);
                }
            } catch (UnknownHostException e) {
                //  Toast.makeText(context, "Asegúrese de tener conexión", Toast.LENGTH_LONG).show();
                error = new UnknownHostException("Asegúrese de tener conexión");
            } catch (
                    Exception e)

            {
                error = e;
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (error == null) {
                try {
                    parseJSONComentario(jsonObject.getJSONArray("Table"));
                } catch (JSONException e) {
                    Toast.makeText(_context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(_context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        private void parseJSONComentario(JSONArray jsonArray) throws JSONException {
            List<Comentario> comentarioList = new ArrayList<Comentario>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Comentario c = new Comentario();
                c.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                c.setPuntaje((float) jsonArray.getJSONObject(i).getDouble("puntos"));
                c.setId_profesor(jsonArray.getJSONObject(i).getInt("id_profesor"));
                comentarioList.add(c);
            }
            _adapter.setLista(comentarioList);
            setListAdapter(_adapter);
        }
    }

    class JsonReaderMaterias extends AsyncTask<String, Void, JSONObject> {
        private Exception error;
        private Context context;

        public JsonReaderMaterias(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String outputData;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpUriRequest request = new HttpGet(strings[0] + _idProfe);
                HttpResponse response = client.execute(request);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    StringBuilder builder = new StringBuilder();
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    outputData = builder.toString();
                    try {
                        return new JSONObject(outputData);

                    } catch (JSONException e) {
                        error = e;
                    }
                } else {
                    throw new Exception("Status code != 200: " + statusCode);
                }


            } catch (UnknownHostException e) {
                //  Toast.makeText(context, "Asegúrese de tener conexión", Toast.LENGTH_LONG).show();
                error = new UnknownHostException("Asegúrese de tener conexión");
            } catch (Exception e) {
                error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (error == null) {
                try {
                    parseJSONMaterias(result.getJSONArray("Table"));
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        private void parseJSONMaterias(JSONArray array) throws JSONException {
            List<String> lista = new ArrayList<String>();

            for (int i = 0; i < array.length(); i++) {
                Catedra c = new Catedra();
                c.setNombre(array.getJSONObject(i).getString("catedra"));
                c.setAlias(array.getJSONObject(i).getString("alias"));

                Comision com = new Comision();
                com.setNombre(array.getJSONObject(i).getString("comision"));

                lista.add(c.getNombre() + "(" + c.getAlias() + ") - " + com.getNombre());

            }

            for (int i = 0; i < lista.size(); i++) {
                //if (i == 0) _tvMaterias.setText(" " + lista.get(i));
                //else _tvMaterias.setText(_tvMaterias.getText() + "\n " + lista.get(i));
                if (i == 0) _materias= lista.get(i);
                else _materias+="\n" + lista.get(i);
            }

        }


    }

}
