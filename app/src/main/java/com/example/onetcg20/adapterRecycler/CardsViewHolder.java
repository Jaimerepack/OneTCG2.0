package com.example.onetcg20.adapterRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onetcg20.Cards;
import com.example.onetcg20.R;
import com.example.onetcg20.Utilitys;
import com.example.onetcg20.ui.home.HomeFragment;

import java.util.List;

/**
 * Clase del ViewHolder del Recycler de las cartas.
 */
public class CardsViewHolder extends RecyclerView.ViewHolder {
    /**
     * Declaracion de textView
     */
    private TextView serie;
    private TextView nombre;
    private TextView power;
    private TextView rarity;
    private TextView counter;
    private TextView set;
    /**
     * Declaracion de la ImageView
     */
    private ImageView image;


    /**
     * Instanciamos un nuevo ViewHolder para las cartas.
     *
     * @param view Vista
     */
    public CardsViewHolder(View view) {
        super(view);
        /**
         * ocupamos el if para las referencias del modo lista o cuadricula
         */
        if (Utilitys.display==Utilitys.grid){
            image = view.findViewById(R.id.ivCard);
        }else{
            serie = view.findViewById(R.id.tvCardSerie);
            nombre = view.findViewById(R.id.tvCardName);
            power = view.findViewById(R.id.tvCardPower);
            image = view.findViewById(R.id.ivCard);
            rarity = view.findViewById(R.id.tvCardRarity);
            counter = view.findViewById(R.id.tvCardCounter);
            set = view.findViewById(R.id.tvCardSet);
        }
    }

    /**
     * El renderizador de las cartas
     *
     * @param cardsModel Modelo de vista de las cartas
     */
    public void render(Cards cardsModel) {
        /**
         * ocupamos el if para ver si mostramos los datos en modo lista o cuadricula
         */
        if (Utilitys.display==Utilitys.grid){
            image.setImageResource(cardsModel.getImageResId());
        }else{
            serie.setText(cardsModel.getSerie());
            nombre.setText(cardsModel.getNombre());
            power.setText(cardsModel.getPower());
            image.setImageResource(cardsModel.getImageResId());
            rarity.setText(cardsModel.getRarity());
            counter.setText(cardsModel.getCounter());
            set.setText(cardsModel.getCard_set());
        }
    }
}
