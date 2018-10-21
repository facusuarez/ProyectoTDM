package info.androidhive.tabsswipe.Activities.Activities.Ranking;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import info.androidhive.tabsswipe.Activities.Activities.Adapter.ProfesorAdapter;
import info.androidhive.tabsswipe.Activities.ConstantesGenerales;
import info.androidhive.tabsswipe.Activities.Dao.AppDatabase;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;
import info.androidhive.tabsswipe.Activities.JSON.JsonReaderGetProfesores;
import info.androidhive.tabsswipe.R;


public class RankingFragment extends ListFragment {

    private ProfesorAdapter _adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ranking, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        _adapter = new ProfesorAdapter(getActivity());

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Profesor profesor = _adapter.getProfesores(position);

                Intent i = new Intent(getActivity(), DatosProfesorActivity.class);
                i.putExtra("nombreProfe", profesor.getApellido() + ", " + profesor.getNombre());
                i.putExtra("idProfe", profesor.getId_profesor());
                i.putExtra("rating",profesor.getPuntaje());
                startActivity(i);
            }

        });

        mostrarListViewProfesores();
    }

    @Override
    public void onResume() {
        super.onResume();

        mostrarListViewProfesores();
    }

    private void mostrarListViewProfesores() {
        JsonReaderGetProfesores reader = new JsonReaderGetProfesores(getActivity());
        //reader.execute(ConstantesGenerales.URL_GET_PROFESORES);
        reader.execute("https://api.myjson.com/bins/1393s8");

        AppDatabase database = Room.databaseBuilder(getActivity(), AppDatabase.class, ConstantesGenerales.DB_NAME)
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        database.getProfesorDao().obtenerProfesores();

        List<Profesor> listaProfesores = database.getProfesorDao().obtenerProfesores();
        _adapter.setLista(listaProfesores);
        setListAdapter(_adapter);
    }


}
