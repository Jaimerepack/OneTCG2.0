package com.example.onetcg20.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Clase que ocupamos para la visualizacion el fragmento de la Coleccion.
 */
public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    /**
     * Instanciamos el modelo del fragmento.
     */
    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    /**
     * metodo para obtener texto de la lista mutable
     *
     * @return regresamos el texto
     */
    public LiveData<String> getText() {
        return mText;
    }
}