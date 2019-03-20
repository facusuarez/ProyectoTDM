package info.androidhive.masterlist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import info.androidhive.masterlist.entities.Profesor;
import info.androidhive.masterlist.R;

/**
 * Created by USUARIO on 10/10/2017.
 */

public class ProfesorAdapter extends BaseAdapter {

    private ArrayList<Profesor> _profesores;
    private LayoutInflater _inflater;
    private Context _context;
    private List<Profesor> _proflist = null;
    /* private ValueFilter valueFilter;*/
    ArrayList<Profesor> mOriginalValues;

    public ProfesorAdapter(Context context) {
        _profesores = new ArrayList<Profesor>();
        _inflater = LayoutInflater.from(context);
        _context = context;
    }

    public ProfesorAdapter(Context context, List<Profesor> proflist) {
        this._proflist = proflist;
        _profesores = new ArrayList<Profesor>();
        _inflater = LayoutInflater.from(context);
        this._profesores.addAll(proflist);
        getFilter();
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

    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                _profesores = (ArrayList<Profesor>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Profesor> FilteredArrList = new ArrayList<Profesor>();

                if (mOriginalValues == null) {
                    System.out.println("");
                    mOriginalValues = new ArrayList<Profesor>(_profesores); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;

                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        Profesor data = mOriginalValues.get(i);
                        if (data.getApellido().toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(data);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
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
            view = _inflater.inflate(R.layout.item_profesor, null);
            holder = new Holder();
            holder.txtPuntuacion = (TextView) view
                    .findViewById(R.id.lblPuntuacion);
            holder.txtNombreProfesor = (TextView) view
                    .findViewById(R.id.lblNombreProfesor);
            view.setTag(holder);

            if ( position % 2 == 0) {
                view.setBackgroundResource(R.drawable.listview_selector_even_row);
            } else {
                view.setBackgroundResource(R.drawable.listview_selector_odd_row);
            }
        } else {
            holder = (Holder) view.getTag();
        }

        Profesor profesor = _profesores.get(position);

        //holder.iconCountry.setImageResource(profesor.getFlagImage());
        String nombreProfesor = profesor.getApellido() + ", " + profesor.getNombre();
        holder.txtNombreProfesor.setText(nombreProfesor);

        double puntaje = profesor.getPuntaje();
        holder.txtPuntuacion.setText(String.format(Locale.ENGLISH,"%.1f", puntaje));

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

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _proflist.clear();
        if (charText.length() == 0) {
            _proflist.addAll(_profesores);
        } else {
            for (Profesor wp : _profesores) {
                if (wp.getNombre().toLowerCase(Locale.getDefault()).contains(charText)) {
                    _proflist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
