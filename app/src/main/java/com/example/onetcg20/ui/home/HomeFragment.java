package com.example.onetcg20.ui.home;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onetcg20.CardActivity;
import com.example.onetcg20.Cards;
import com.example.onetcg20.CardsProvide;
import com.example.onetcg20.R;
import com.example.onetcg20.Utilitys;
import com.example.onetcg20.adapterRecycler.CardsAdapter;
import java.util.List;

/**
 * Clase principal del fragmento de las cartas.
 */
public class HomeFragment extends Fragment{
    /**
     * Declaracion del RecyclerView.
     */
    private RecyclerView recyclerView;
    /**
     * Declaracion del CardAdapter.
     */
    private CardsAdapter cardsAdapter;
    /**
     * Declaracion del array list de las cartas.
     */
    private List<Cards> cardList;

    /**
     * Clase que crea la vista del fragmento en general.
     *
     * @param inflater           Inflador del fragmento
     * @param container          Contenedor del fragmento
     * @param savedInstanceState Salva el estado la instancia
     * @return Retorna la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Inflamos el layout para este fragmento
         */
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerCards);
        initializeCardList(getActivity());
        /**
         * referenciamos los botones para cambiar la vista le recycler
         */
        Button btnCuadricula = view.findViewById(R.id.btnCuadricula);
        Button btnLista = view.findViewById(R.id.btnLista);
        /**
         * evento para al hacer click en el boton se vea en modo cuadricula
         */
        btnCuadricula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitys.display=Utilitys.grid;
                initializeCardList(getActivity());
            }
        });
        /**
         * evento para al hacer click en el boton se vea en modo lista
         */
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitys.display=Utilitys.list;
                initializeCardList(getActivity());

            }
        });
        return view;
    }


    /**
     * Iniciador de la lista de cartas.
     *
     * @param context Contexto de la actividad que se llama.
     */
    public void initializeCardList(Context context) {
        /**
         * revisamos el display que es para ver si ocupamos metodo lista o cuadricula
         */
        if (Utilitys.display==Utilitys.grid){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        cardList = CardsProvide.getCardsList(context);
        cardsAdapter = new CardsAdapter(cardList);
        /**
         * Evento cuando se hace click en una carta
         */
        cardsAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Inicializamos el intent para acuparlo
                 */
                Intent i = new Intent(getActivity(), CardActivity.class);
                /**
                 * Enviamos los datos a la otra actividad donde se mostrara la carta
                 */
                i.putExtra("cardImageResId", cardList.get(recyclerView.getChildAdapterPosition(view)).getImageResId());
                i.putExtra("cardSerie",cardList.get(recyclerView.getChildAdapterPosition(view)).getSerie());
                i.putExtra("cardRarity",cardList.get(recyclerView.getChildAdapterPosition(view)).getRarity());
                i.putExtra("cardDesignation",cardList.get(recyclerView.getChildAdapterPosition(view)).getDesignation());
                i.putExtra("cardName",cardList.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                i.putExtra("cardLife",cardList.get(recyclerView.getChildAdapterPosition(view)).getLife());
                i.putExtra("cardAttribute",cardList.get(recyclerView.getChildAdapterPosition(view)).getAttribute());
                i.putExtra("cardPower",cardList.get(recyclerView.getChildAdapterPosition(view)).getPower());
                i.putExtra("cardCounter",cardList.get(recyclerView.getChildAdapterPosition(view)).getCounter());
                i.putExtra("cardColor",cardList.get(recyclerView.getChildAdapterPosition(view)).getColor());
                i.putExtra("cardType",cardList.get(recyclerView.getChildAdapterPosition(view)).getType());
                i.putExtra("cardEffect",cardList.get(recyclerView.getChildAdapterPosition(view)).getEffect());
                i.putExtra("card_trigger",cardList.get(recyclerView.getChildAdapterPosition(view)).get_trigger());
                i.putExtra("cardCard_set",cardList.get(recyclerView.getChildAdapterPosition(view)).getCard_set());
                i.putExtra("cardNotes",cardList.get(recyclerView.getChildAdapterPosition(view)).getNotes());
                /**
                 * Iniciamos la nueva actividad donde se ve la carta
                 */
                Utilitys.displayBtn=Utilitys.btnRemoveOn;
                startActivity(i);
            }
        });
        recyclerView.setAdapter(cardsAdapter);
    }

}