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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListadoFragment extends Fragment{


    private RecyclerView recyclerView;
    private UnivAdapter adapter;
    private ArrayList<Graph> centrosList;
    LinearLayoutManager llm;

    public ListadoFragment() {}


    public static ListadoFragment newInstance() {

        ListadoFragment fragment = new ListadoFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;

    }


    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_listado, container, false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listado, container, false);

        recyclerView = view.findViewById(R.id.rV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        centrosList = new ArrayList<>();
        adapter = new UnivAdapter(centrosList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    public void actualizarLista(APIRestService ars, Double lat, Double lon, Double dist) {

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
                    cargarRV(resultadoDatos.getGraph());

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

    private void cargarRV(List<Graph> results) {

        llm = new LinearLayoutManager(getActivity());
        adapter = new UnivAdapter((List<Graph>) results);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

}