package com.example.onetcg20.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetcg20.CardActivity;
import com.example.onetcg20.Cards;
import com.example.onetcg20.CardsProvide;
import com.example.onetcg20.ColllectionProvide;
import com.example.onetcg20.R;
import com.example.onetcg20.Utilitys;
import com.example.onetcg20.adapterRecycler.CardsAdapter;
import com.example.onetcg20.databinding.FragmentDashboardBinding;

import java.util.List;

/**
 * Clase principal del fragmento de la coleccion.
 */
public class DashboardFragment extends Fragment {
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
     * Declaracion del Binding(une el fragmento con la vista de este).
     */
    private FragmentDashboardBinding binding;

    /**
     * Clase que crea la vista del fragmento en general.
     *
     * @param inflater           Inflador del fragmento
     * @param container          Contenedor del fragmento
     * @param savedInstanceState Salva el estado la instancia
     * @return Retorna la vista del fragmento
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.recyclerCards);
        initializeCardList(getActivity());
        /**
         * referenciamos los botones para cambiar la vista le recycler
         */
        Button btnCuadricula = root.findViewById(R.id.btnCuadricula);
        Button btnLista = root.findViewById(R.id.btnLista);
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

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Vuelve a cargar la lista de cartas
        initializeCardList(getActivity());
    }

    /**
     * Destructor de la vista.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Iniciador de la lista de cartas.
     *
     * @param context Contexto de la actividad que se llama
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
        cardList = ColllectionProvide.getCardsList(context);
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
                Utilitys.displayBtn=Utilitys.btnRemoveOff;
                startActivity(i);
            }
        });
        recyclerView.setAdapter(cardsAdapter);
    }

}