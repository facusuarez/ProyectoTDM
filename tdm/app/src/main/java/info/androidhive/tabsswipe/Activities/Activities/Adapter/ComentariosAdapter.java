package info.androidhive.tabsswipe.Activities.Activities.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import info.androidhive.tabsswipe.Activities.Entities.Comentario;
import info.androidhive.tabsswipe.R;

/**
 * Created by USUARIO on 20/10/2017.
 */

public class ComentariosAdapter extends BaseAdapter {
    private ArrayList<Comentario> _comentario;
    private LayoutInflater _inflater;

    public ComentariosAdapter(Context context) {
        _comentario = new ArrayList<Comentario>();
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _comentario.size();
    }

    @Override
    public Object getItem(int arg0) {
        return _comentario.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder;
        if (view == null) {
            view = _inflater.inflate(R.layout.item_comentario, null);
            holder = new Holder();
            holder.lblDescripcion = (TextView) view.findViewById(R.id.lblDescripcion);
            holder.imgUsuario= (ImageView) view.findViewById(R.id.imgUsuario);
            holder.lblPuntos = (TextView) view.findViewById(R.id.lblPuntos);
            //holder.lblUsuario = (TextView) view.findViewById(R.id.lblUsuario);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Comentario comentario = _comentario.get(position);

        holder.lblDescripcion.setText(comentario.getDescripcion());
        //holder.lblUsuario=
        holder.lblPuntos.setText(comentario.getPuntaje()+" ");
        //holder.imgUsuario

        return view;

    }

    public void setLista(List<Comentario> listaComentarios) {
        _comentario.clear();
        _comentario.addAll(listaComentarios);
    }

    class Holder {
        TextView lblPuntos;
        TextView lblDescripcion;
        TextView lblUsuario;
        ImageView imgUsuario;
    }
}
