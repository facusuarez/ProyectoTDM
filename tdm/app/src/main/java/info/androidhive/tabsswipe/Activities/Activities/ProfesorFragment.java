package info.androidhive.tabsswipe.Activities.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.androidhive.tabsswipe.R;

/**
 * Created by USUARIO on 10/10/2017.
 */

public class ProfesorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_datos_profesor, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String nombre = getArguments().getString("profesor");
        TextView tv = (TextView) getView().findViewById(R.id.txtNombre);
        tv.setText(nombre);

    }
}
