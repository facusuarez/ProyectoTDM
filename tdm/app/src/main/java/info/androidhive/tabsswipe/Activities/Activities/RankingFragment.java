package info.androidhive.tabsswipe.Activities.Activities;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import info.androidhive.tabsswipe.Activities.Activities.Adapter.ProfesorAdapter;
import info.androidhive.tabsswipe.Activities.Activities.Adapter.ProfesorSelectedHandler;
import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;
import info.androidhive.tabsswipe.R;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RankingFragment extends ListFragment {

    private ProfesorAdapter _adapter;
    private ProfesorSelectedHandler _profesorSelected;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

            return  inflater.inflate(R.layout.fragment_ranking, container, false);

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

                seleccionarProfesor(position);
            }

        });

        ProfesorDao profesorDao= new ProfesorDao(getActivity());
        List<Profesor> listaProfesores = profesorDao.obtenerProfesores();
        _adapter.setLista(listaProfesores);
        setListAdapter(_adapter);

}

    private void seleccionarProfesor(int position) {
        Profesor profesor = _adapter.getProfesores(position);
        if(profesor!=null){

            ProfesorFragment profesorFragment = new ProfesorFragment();
            Bundle parametro = new Bundle();
            parametro.putString("profesor",profesor.getApellido()+", "+profesor.getNombre());
            profesorFragment.setArguments(parametro);

            FragmentTransaction ft = getFragmentManager()
                    .beginTransaction();
            //ft.hide(this);

            ft.add(R.id.fragment_ranking, profesorFragment);
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }



}
