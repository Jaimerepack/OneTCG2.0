package com.example.onetcg20.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onetcg20.LogginActivity;
import com.example.onetcg20.R;
import com.example.onetcg20.databinding.FragmentProfileBinding;

/**
 * Clase principal del fragmento de perfil de usuario.
 */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private TextView tvUsername, tvUserEmail, tvUserId, tvUserInitials, tvCollectionCount, tvTotalCards;
    private Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Referencias de componentes UI
        tvUsername = root.findViewById(R.id.tvUsername);
        tvUserEmail = root.findViewById(R.id.tvUserEmail);
        tvUserId = root.findViewById(R.id.tvUserId);
        tvUserInitials = root.findViewById(R.id.tvUserInitials);
        tvCollectionCount = root.findViewById(R.id.tvCollectionCount);
        tvTotalCards = root.findViewById(R.id.tvTotalCards);
        btnLogout = root.findViewById(R.id.btnLogout);

        // Cargar datos del usuario
        loadUserData();

        // Cargar estadísticas de colección
        loadCollectionStats();

        // Evento para cerrar sesión
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión y volver a la pantalla de login
                Intent intent = new Intent(getActivity(), LogginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return root;
    }

    /**
     * Carga la información del usuario desde la base de datos
     */
    private void loadUserData() {
        LogginActivity.bdonetcg dbHelper = new LogginActivity.bdonetcg(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Obtener el primer usuario
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario LIMIT 1", null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id_usuario");
            int nombreIndex = cursor.getColumnIndex("nombre");
            int correoIndex = cursor.getColumnIndex("correo");

            if (idIndex != -1 && nombreIndex != -1 && correoIndex != -1) {
                String userId = cursor.getString(idIndex);
                String nombre = cursor.getString(nombreIndex);
                String correo = cursor.getString(correoIndex);

                // Mostrar datos en la UI
                tvUserId.setText(userId);
                tvUsername.setText(nombre);
                tvUserEmail.setText(correo);

                // Obtener iniciales para el avatar
                if (nombre != null && !nombre.isEmpty()) {
                    String initials;
                    if (nombre.length() > 1) {
                        initials = nombre.substring(0, 2).toUpperCase();
                    } else {
                        initials = nombre.substring(0, 1).toUpperCase();
                    }
                    tvUserInitials.setText(initials);
                }
            }
        }
        cursor.close();
        db.close();
    }

    /**
     * Carga estadísticas sobre la colección de cartas del usuario
     */
    private void loadCollectionStats() {
        LogginActivity.bdonetcg dbHelper = new LogginActivity.bdonetcg(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Contar cartas en la colección
        Cursor collectionCursor = db.rawQuery("SELECT COUNT(*) FROM Cartas_obtenidas", null);
        int collectionCount = 0;
        if (collectionCursor.moveToFirst()) {
            collectionCount = collectionCursor.getInt(0);
        }
        collectionCursor.close();

        // Contar total de cartas disponibles
        Cursor totalCardsCursor = db.rawQuery("SELECT COUNT(*) FROM Cartas", null);
        int totalCards = 0;
        if (totalCardsCursor.moveToFirst()) {
            totalCards = totalCardsCursor.getInt(0);
        }
        totalCardsCursor.close();

        // Mostrar estadísticas en la UI
        tvCollectionCount.setText(String.valueOf(collectionCount));
        tvTotalCards.setText(String.valueOf(totalCards));

        db.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}