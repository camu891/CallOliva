package com.matic.calloliva;

import android.content.ClipData;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by matic on 11/05/16.
 */
public class EntidadAdapter extends RecyclerView.Adapter<EntidadAdapter.EntidadViewHolder>{

    private List<Entidad> items;



    public static class EntidadViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ListAdapter";
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;
        public TextView telefono;



        public EntidadViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
            telefono=(TextView) v.findViewById(R.id.telefono);

        }




    }

    public EntidadAdapter(List<Entidad> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public EntidadViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new EntidadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntidadViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getLogo());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
        viewHolder.telefono.setText(items.get(i).getTelefono());


        /*viewHolder.telefono.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //notice I implemented onClickListener here
                // so I can associate this click with final Item item

            }

        });*/


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);

    }



}
