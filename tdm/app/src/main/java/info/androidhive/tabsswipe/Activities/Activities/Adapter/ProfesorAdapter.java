package info.androidhive.tabsswipe.Activities.Activities.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import info.androidhive.tabsswipe.Activities.Entities.Profesor;
import info.androidhive.tabsswipe.R;

/**
 * Created by USUARIO on 10/10/2017.
 */

public class ProfesorAdapter extends BaseAdapter {

    private ArrayList<Profesor> _profesores;
    private LayoutInflater _inflater;

    public ProfesorAdapter(Context context) {
        _profesores = new ArrayList<Profesor>();
        _inflater = LayoutInflater.from(context);
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
                    .findViewById(R.id.lblPuntuacion);
            holder.txtNombreProfesor = (TextView) view
                    .findViewById(R.id.lblNombreProfesor);
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
