package com.example.onetcg20.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Clase que ocupamos para la visualización del fragmento de perfil.
 */
public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    /**
     * Instanciamos el modelo del fragmento.
     */
    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento de perfil");
    }

    /**
     * Método para obtener texto de la lista mutable
     *
     * @return regresamos el texto
     */
    public LiveData<String> getText() {
        return mText;
    }
}