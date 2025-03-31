package com.example.onetcg20.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Clase que ocupamos para la visualizacion el fragmento de las Cartas.
 */
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    /**
     * Instanciamos el modelo del fragmento.
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
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