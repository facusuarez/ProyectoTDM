package info.androidhive.masterlist.activities;

import android.support.v4.app.ListFragment;
import android.arch.persistence.room.Room;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.masterlist.adapter.ProfesorAdapter;
import info.androidhive.masterlist.activities.Ranking.DatosProfesorActivity;
import info.androidhive.masterlist.Constants;
import info.androidhive.masterlist.dao.AppDatabase;
import info.androidhive.masterlist.entities.Profesor;
import info.androidhive.masterlist.R;

public class SearchFragment extends ListFragment {

    ListView list;
    ProfesorAdapter adapter;
    EditText editsearch;
    ArrayList<Profesor> arraylist = new ArrayList<Profesor>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        // Generate sample data

        /*animalNameList = new String[]{"Lion", "Tiger", "Dog",
                "Cat", "Tortoise", "Rat", "Elephant", "Fox",
                "Cow","Donkey","Monkey"};*/

        // Locate the ListView in listview_main.xml
        /*list = (ListView) getListView().findViewById(R.id.)*/

        /*ProfesorDao profesorDao= new ProfesorDao(getActivity());
        List<Profesor> listaProfesores = profesorDao.obtenerProfesores();*/


        getListView().setEmptyView(getView().findViewById(R.id.empty2));
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Profesor profesor = adapter.getProfesores(position);

                Intent i = new Intent(getActivity(), DatosProfesorActivity.class);
                i.putExtra("nombreProfe", profesor.getApellido() + ", " + profesor.getNombre());
                i.putExtra("idProfe", profesor.getId_profesor());
                i.putExtra("rating", profesor.getPuntaje());
                startActivity(i);
            }

        });
        // Locate the EditText in listview_main.xml
        editsearch = (EditText) getView().findViewById(R.id.search);
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                AppDatabase database = Room.databaseBuilder(getActivity(), AppDatabase.class, Constants.DB_NAME)
                        .allowMainThreadQueries()   //Allows room to do operation on main thread
                        .build();

                List<Profesor> listaProfesores = database.getProfesorDao().getProfesoresByApellido(arg0 + "%");
                adapter = new ProfesorAdapter(getActivity(), listaProfesores);
                setListAdapter(adapter);
                //adapter.getFilter().filter(arg0);
                getListView().setEmptyView(getView().findViewById(R.id.empty1));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0 == null || arg0.length() == 0) {
                    getListView().setEmptyView(getView().findViewById(R.id.empty2));
                    setListAdapter(null);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//TODO necesito saber si puedo ver el profile desde cualquier lado. buscar un metdo del fragment que se ejecute
        try {
            Log.e("", Profile.getCurrentProfile().getLastName());
        } catch (FacebookSdkNotInitializedException e) {

        }
    }


}
