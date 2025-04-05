package com.example.onetcg20.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetcg20.CardActivity;
import com.example.onetcg20.Cards;
import com.example.onetcg20.ColllectionProvide;
import com.example.onetcg20.R;
import com.example.onetcg20.Utilitys;
import com.example.onetcg20.adapterRecycler.CardsAdapter;
import com.example.onetcg20.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del fragmento de la coleccion.
 */
public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    /**
     * Declaracion del RecyclerView.
     */
    private RecyclerView recyclerView; //Contenedor para mostrar las cartas
    /**
     * Declaracion del CardAdapter.
     */
    private CardsAdapter cardsAdapter; //Adaptador que conecta los datos con el RecyclerView
    /**
     * Declaracion del array list de las cartas.
     */
    private List<Cards> cardList; //Lista de cartas que se mostraran en la coleccion
    /**
     * Declaracion del Binding(une el fragmento con la vista de este).
     */
    private FragmentDashboardBinding binding;
    /**
     * Componentes de búsqueda
     */
    private EditText etSearch; //Componentes de busqueda
    private Button btnSearch; //Componentes de busqueda
    /**
     * Mensaje de colección vacía
     */
    private TextView tvEmptyCollection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        // Inicializar lista para evitar NullPointerException
        cardList = new ArrayList<>();
    }

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
        Log.d(TAG, "onCreateView called");

        try {
            DashboardViewModel dashboardViewModel =
                    new ViewModelProvider(this).get(DashboardViewModel.class);

            // Usar el layout correcto para dashboard
            View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

            // Inicializar componentes
            recyclerView = view.findViewById(R.id.recyclerCards);
            tvEmptyCollection = view.findViewById(R.id.tvEmptyCollection);

            // Componentes de búsqueda
            etSearch = view.findViewById(R.id.etSearch);
            btnSearch = view.findViewById(R.id.btnSearch);

            // Botones de vista
            Button btnCuadricula = view.findViewById(R.id.btnCuadricula);
            Button btnLista = view.findViewById(R.id.btnLista);

            if (btnCuadricula != null) {
                btnCuadricula.setOnClickListener(v -> {
                    Utilitys.display = Utilitys.grid;
                    updateRecyclerViewLayout();
                });
            }

            if (btnLista != null) {
                btnLista.setOnClickListener(v -> {
                    Utilitys.display = Utilitys.list;
                    updateRecyclerViewLayout();
                });
            }

            // Configurar búsqueda
            if (btnSearch != null && etSearch != null) {
                btnSearch.setOnClickListener(v -> {
                    String query = etSearch.getText().toString().trim();
                    searchCards(query);
                });
            }

            // Cargar las cartas
            loadCards();

            return view;

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView: " + e.getMessage());
            e.printStackTrace();

            // Retornar una vista vacía en caso de error para evitar que la app se cierre
            return new View(getContext());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        // Recargar la colección al volver al fragmento
        loadCards();
    }

    /**
     * Carga las cartas de la colección
     */
    private void loadCards() {
        try {
            // Obtener las cartas de la colección
            List<Cards> loadedCards = ColllectionProvide.getCardsList(getActivity());

            // Limpiar la lista actual
            cardList.clear();

            // Añadir las cartas cargadas si hay alguna
            if (loadedCards != null && !loadedCards.isEmpty()) {
                cardList.addAll(loadedCards);
                if (tvEmptyCollection != null) {
                    tvEmptyCollection.setVisibility(View.GONE);
                }
                if (recyclerView != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                // Mostrar mensaje si no hay cartas
                if (tvEmptyCollection != null) {
                    tvEmptyCollection.setVisibility(View.VISIBLE);
                }
                if (recyclerView != null) {
                    recyclerView.setVisibility(View.GONE);
                }
            }

            // Actualizar el RecyclerView
            updateRecyclerViewLayout();

        } catch (Exception e) {
            Log.e(TAG, "Error loading cards: " + e.getMessage());
            e.printStackTrace();

            // Mostrar mensaje de error
            if (getContext() != null) {
                Toast.makeText(getContext(), "Error al cargar la colección", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Actualiza el layout del RecyclerView basado en la configuración actual
     */
    private void updateRecyclerViewLayout() {
        if (recyclerView == null || getActivity() == null) {
            return;
        }

        try {
            // Configurar el layout manager según la vista seleccionada
            if (Utilitys.display == Utilitys.grid) {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            // Crear y configurar el adaptador
            cardsAdapter = new CardsAdapter(cardList);

            // Configurar evento de clic en las cartas
            cardsAdapter.setOnClickListener(view -> {
                try {
                    int position = recyclerView.getChildAdapterPosition(view);
                    if (position != RecyclerView.NO_POSITION && position < cardList.size()) {
                        openCardDetail(position);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error on card click: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            // Establecer el adaptador
            recyclerView.setAdapter(cardsAdapter);

        } catch (Exception e) {
            Log.e(TAG, "Error updating RecyclerView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Abre la actividad de detalle de carta
     * @param position Posición de la carta en la lista
     */
    private void openCardDetail(int position) {
        if (getActivity() == null || position >= cardList.size()) {
            return;
        }

        try {
            Cards card = cardList.get(position);
            Intent intent = new Intent(getActivity(), CardActivity.class);

            // Añadir datos de la carta al intent
            intent.putExtra("cardImageResId", card.getImageResId());
            intent.putExtra("cardSerie", card.getSerie());
            intent.putExtra("cardRarity", card.getRarity());
            intent.putExtra("cardDesignation", card.getDesignation());
            intent.putExtra("cardName", card.getNombre());
            intent.putExtra("cardLife", card.getLife());
            intent.putExtra("cardAttribute", card.getAttribute());
            intent.putExtra("cardPower", card.getPower());
            intent.putExtra("cardCounter", card.getCounter());
            intent.putExtra("cardColor", card.getColor());
            intent.putExtra("cardType", card.getType());
            intent.putExtra("cardEffect", card.getEffect());
            intent.putExtra("card_trigger", card.get_trigger());
            intent.putExtra("cardCard_set", card.getCard_set());
            intent.putExtra("cardNotes", card.getNotes());

            // Configurar para mostrar el botón de eliminar
            Utilitys.displayBtn = Utilitys.btnRemoveOff;

            // Iniciar la actividad
            startActivity(intent);

        } catch (Exception e) {
            Log.e(TAG, "Error opening card detail: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error al abrir detalles de la carta", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Busca cartas por código o nombre
     * @param query Texto de búsqueda
     */
    private void searchCards(String query) {
        if (query.isEmpty()) {
            loadCards();
            return;
        }

        try {
            List<Cards> filteredList = new ArrayList<>();

            // Filtrar las cartas que coincidan con la búsqueda
            for (Cards card : cardList) {
                if (card.getSerie().toLowerCase().contains(query.toLowerCase()) ||
                        card.getNombre().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(card);
                }
            }

            // Actualizar la lista
            cardList.clear();
            cardList.addAll(filteredList);

            // Actualizar el RecyclerView
            updateRecyclerViewLayout();

            // Mostrar mensaje si no hay resultados
            if (filteredList.isEmpty()) {
                Toast.makeText(getActivity(), "No se encontraron cartas que coincidan con la búsqueda en tu colección", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e(TAG, "Error searching cards: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView called");
        binding = null;
    }
}