package com.dam.proyectoa_pmdm_t2_alejandro_garcia.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dam.proyectoa_pmdm_t2_alejandro_garcia.R;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.adapter.UnivAdapter;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitdata.Graph;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitdata.Result;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitutils.APIRestService;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaFragment extends Fragment {

    protected SupportMapFragment mapasup;
    private Result listaunivs;
    private ArrayList<Graph> centrosList;
    private RecyclerView recyclerView;
    private UnivAdapter adapter;
    LinearLayoutManager llm;

    public static  MapaFragment newInstance(Result listaunis) {

        MapaFragment mFragment = new MapaFragment();
        Bundle args = new Bundle();

        mFragment.setArguments(args);
        args.putSerializable("listaunivs", (Serializable) listaunis);

        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            listaunivs = (Result) getArguments().getSerializable("listaunivs");

        }
    }

    public void actualizarMapa(APIRestService ars, Double lat, Double lon, Double dist) {

        Call<Result> call;

        if (lat != null && lon != null && dist != 0) { //validación de datos
            call = ars.getDatosConFiltros(lat, lon, dist);
        } else {
            call = ars.getDatosgrl();
        }

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (response.isSuccessful()) {

                    Result resultadoDatos = response.body();
                    centrosList.addAll(resultadoDatos.getGraph());
                    cargarRV(centrosList);

                    if (centrosList.size() == 0) Toast.makeText(getActivity(), "No hay datos", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getActivity(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    private void cargarRV(ArrayList<Graph> results) {

        llm = new LinearLayoutManager(getActivity());
        adapter = new UnivAdapter((ArrayList<Graph>) results);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }
}