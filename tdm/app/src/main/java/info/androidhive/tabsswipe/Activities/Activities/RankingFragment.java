package info.androidhive.tabsswipe.Activities.Activities;

import android.annotation.SuppressLint;
import android.support.v4.app.ListFragment;

import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;
import info.androidhive.tabsswipe.R;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RankingFragment extends ListFragment {

    private ProfesorAdapter _adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

            return  inflater.inflate(R.layout.fragment_ranking, container, false);

	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        _adapter = new ProfesorAdapter();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                //onCountrySelected(position);
            }

        });

        ProfesorDao profesorDao= new ProfesorDao(getActivity());
        List<Profesor> listaProfesores = profesorDao.obtenerProfesores();
        _adapter.setLista(listaProfesores);
        setListAdapter(_adapter);

}

    private void onCountrySelected(int position) {

        //Profesor profesor = _adapter.getProfesores(position);
        //if (profesor != null) {
        //	_countrySelectedHandler.onCountrySelected(country, _continent);
        //}
    }

    private class ProfesorAdapter extends BaseAdapter {

        private ArrayList<Profesor> _profesores;
        private LayoutInflater _inflater;

        public ProfesorAdapter() {
            _profesores = new ArrayList<Profesor>();
            _inflater = LayoutInflater.from(getActivity());
        }

        public void setProfesores(Set<Profesor> profesores) {
            _profesores.clear();
            for (Profesor country : profesores) {
                _profesores.add(country);
            }
            //Settings.sortCountries(_profesores, getActivity());
        }

        public Profesor getProfesores(int position) {
            Profesor profesor = (Profesor) getItem(position);
            return profesor;
        }

        @Override
        public int getCount() {
            return _profesores.size();
        }

        @Override
        public Object getItem(int arg0) {
            return _profesores.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = null;
            if (view == null) {
                view = _inflater.inflate(R.layout.item_dialog_info, null);
                holder = new Holder();
                holder.txtPuntuacion = (TextView) view
                        .findViewById(R.id.txtPuntuacion);
                holder.txtNombreProfesor = (TextView) view
                        .findViewById(R.id.txtNombreProfesor);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            Profesor profesor = _profesores.get(position);

            //holder.iconCountry.setImageResource(profesor.getFlagImage());
            String nombreProfesor = profesor.getApellido()+", "+profesor.getNombre();
            holder.txtNombreProfesor.setText(nombreProfesor);

            holder.txtPuntuacion.setText((position+1)+"");


            return view;

        }

        public void setLista(List<Profesor> listaProfesores) {
            _profesores.clear();
            _profesores.addAll(listaProfesores);
        }

        class Holder {
            //ImageView iconCountry;
            TextView txtPuntuacion;
            //ImageView iconBigPopulation;
            TextView txtNombreProfesor;
        }
    }
}
