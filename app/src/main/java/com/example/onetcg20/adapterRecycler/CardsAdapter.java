package com.example.onetcg20.adapterRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetcg20.Cards;
import com.example.onetcg20.R;
import com.example.onetcg20.Utilitys;
import com.example.onetcg20.ui.home.HomeFragment;

import java.util.List;

/**
 * Clase del adaptador de cartas.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsViewHolder> implements View.OnClickListener {
    /**
     * Lista array para las cartas.
     */
    private List<Cards> cardsList;
    /**
     * Escuchador ocupado para los click en la carta.
     */
    private View.OnClickListener listener;
    /**
     * Instanciamos el adaptador de las cartas.
     *
     * @param cardsList Lista de las cartas
     */
    public CardsAdapter(List<Cards> cardsList) {
        this.cardsList = cardsList;
    }
    /**
     * Creacion del ViewHolder de las cartas.
     *
     * @param parent   the parent
     * @param viewType tipo de vista
     * @return Retornamos el ViewHolder de las cartas
     */
    @NonNull
    @Override
    public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**
         * revisamos si se quiere ver en modo cuadricula o lista para implementar la vista
         */
        int layout = 0;
        if (Utilitys.display==Utilitys.grid){
            layout = R.layout.items_grid_cards;
        }else{
            layout = R.layout.item_cards;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        view.setOnClickListener(this);
        return new CardsViewHolder(view);
    }
    /**
     * Accion de hacer click en la carta.
     *
     * @param listener El escuchador que esta pendiente de si se hace click en la carta.
     */
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    /**
     * Click en carta
     *
     * @param view Vista
     */
    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    /**
     * Union del ViewHolder.
     *
     * @param holder   Contenedor de las cartas
     * @param position la posicion en que se esta(item de la lista)
     */
    @Override
    public void onBindViewHolder(@NonNull CardsViewHolder holder, int position) {
        Cards card = cardsList.get(position);
        holder.render(card);
    }
    /**
     * Cuenta la cantidad de items de la lista.
     *
     * @return Retorna la cuenta de la lista
     */
    @Override
    public int getItemCount() {
        return cardsList.size();
    }

}

