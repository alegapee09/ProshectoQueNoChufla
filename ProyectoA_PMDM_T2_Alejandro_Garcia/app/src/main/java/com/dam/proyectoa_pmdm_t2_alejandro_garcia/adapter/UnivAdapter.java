package com.dam.proyectoa_pmdm_t2_alejandro_garcia.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectoa_pmdm_t2_alejandro_garcia.R;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitdata.Graph;

import java.util.ArrayList;
import java.util.List;

public class UnivAdapter extends RecyclerView.Adapter<UnivAdapter.UniViewHolder> {

    private List<Graph> listaUnis;

    public UnivAdapter(List<Graph> listaUnis) {
        this.listaUnis = listaUnis;
    }


    @NonNull
    @Override
    public UnivAdapter.UniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.univ_item, parent, false);
        return new UniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniViewHolder holder, int position) {
        Graph graph = listaUnis.get(position);
        holder.getTvNombre().setText(graph.getTitle());

    }


    @Override
    public int getItemCount() {
        return listaUnis.size();
    }

    public static class UniViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre;

        public UniViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvItem);
        }

        public TextView getTvNombre() {
            return tvNombre;
        }
    }

}