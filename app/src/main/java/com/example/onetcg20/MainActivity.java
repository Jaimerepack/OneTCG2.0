package com.example.onetcg20;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.onetcg20.databinding.ActivityMainBinding;
import com.example.onetcg20.ui.dashboard.DashboardFragment;
import com.example.onetcg20.ui.home.HomeFragment;
import com.example.onetcg20.ui.notifications.NotificationsFragment;
import com.example.onetcg20.ui.profile.ProfileFragment;

/**
 * primera actividad aqui se levanta la navbar de navegacion y los fragment de esta.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Configurar para que los fragmentos no se recreen al volver a seleccionarlos
            navView.setOnItemReselectedListener(item -> {
                // No hacemos nada cuando se vuelve a seleccionar el mismo ítem
                Log.d(TAG, "Item reselected: " + item.getItemId());
            });

            // Establecer el listener de clic en los ítems de la barra de navegación
            navView.setOnItemSelectedListener(item -> {
                Log.d(TAG, "Item selected: " + item.getItemId());

                try {
                    // Navegar al destino correspondiente
                    if (navController != null) {
                        navController.navigate(item.getItemId());
                        return true;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to destination: " + e.getMessage());
                    e.printStackTrace();
                }

                return false;
            });

            // Configuración del controlador de navegación
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard,
                    R.id.navigation_notifications, R.id.navigation_profile)
                    .build();
            // Se inicializa el NavController
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

            // Añadir un listener para controlar los cambios de destino
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                Log.d(TAG, "Destination changed to: " + destination.getLabel());
            });

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
            navView.setItemIconTintList(null);

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
            e.printStackTrace();
        }
    }
}