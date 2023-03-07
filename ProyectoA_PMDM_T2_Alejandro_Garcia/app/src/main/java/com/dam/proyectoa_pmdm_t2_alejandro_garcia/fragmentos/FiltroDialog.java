package com.dam.proyectoa_pmdm_t2_alejandro_garcia.fragmentos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.proyectoa_pmdm_t2_alejandro_garcia.listener.OnXListener;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.R;


public class FiltroDialog extends DialogFragment {

    EditText etLatitud;
    EditText etLongitud;
    EditText etDistancia;
    OnXListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertdg = new AlertDialog.Builder(getActivity());

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_filtro_dialog, null);

        etLatitud = v.findViewById(R.id.etLatitud);
        etLongitud = v.findViewById(R.id.etLongitud);
        etDistancia = v.findViewById(R.id.etDistancia);

        alertdg.setView(v);

        alertdg.setTitle(R.string.selFil).setPositiveButton("Filtrar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String latitud = etLatitud.getText().toString();
                String longitud = etLongitud.getText().toString();
                String distancia = etDistancia.getText().toString();

                if (latitud.isEmpty() || longitud.isEmpty() || distancia.isEmpty()) {

                    dialog.cancel();
                    Toast.makeText(getActivity(), "Debe introducir todos los datos", Toast.LENGTH_LONG).show();

                } else if(listener != null) {

                        listener.OnXListener(Double.parseDouble(latitud),
                                Double.parseDouble(longitud),
                                Double.parseDouble(distancia));
                    }
                }
        } ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        return alertdg.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

            listener = (OnXListener) context;
    }

    @Override
    public void onDetach() {
        if (listener != null) {
            listener = null;
        }
        super.onDetach();
    }
}