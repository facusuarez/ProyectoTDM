package info.androidhive.tabsswipe.Activities.Activities;

import android.support.v4.app.ListFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.tabsswipe.Activities.Activities.Adapter.ProfesorAdapter;
import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;
import info.androidhive.tabsswipe.R;

public class SearchFragment extends ListFragment  {

    ListView list;
    ProfesorAdapter adapter;
    EditText editsearch;
    ArrayList<Profesor> arraylist = new ArrayList<Profesor>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_search, container, false);
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
        // Locate the EditText in listview_main.xml
        editsearch = (EditText) getView().findViewById(R.id.search);
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ProfesorDao profesorDao= new ProfesorDao(getActivity());
                List<Profesor> listaProfesores = profesorDao.obtenerProfesores();
                adapter = new ProfesorAdapter(getActivity(),listaProfesores);
                setListAdapter(adapter);
                adapter.getFilter().filter(arg0);
                getListView().setEmptyView(getView().findViewById(R.id.empty1));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0 == null || arg0.length() == 0)
                {
                    getListView().setEmptyView(getView().findViewById(R.id.empty2));
                   setListAdapter(null);

                }

            }
        });
    }

}
