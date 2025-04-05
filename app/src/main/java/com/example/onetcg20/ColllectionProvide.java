package com.example.onetcg20;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que provee la lista de cartas de la coleccion al RecyclerView.
 */
public class ColllectionProvide {
    private static final String TAG = "CollectionProvider";

    /**
     * Tomamos las cartas de la base de datos para implementarlas en el RecyclerView.
     *
     * @param context contexto de la actividad de donde pedimos acceso a la base de datos
     * @return retorna la lista de cartas para la parte de coleccion
     */
    public static List<Cards> getCardsList(Context context) {
        List<Cards> cardsList = new ArrayList<>();

        if (context == null) {
            Log.e(TAG, "Contexto nulo");
            return cardsList;
        }

        LogginActivity.bdonetcg dbHelper = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            // Obtener acceso a la base de datos
            dbHelper = new LogginActivity.bdonetcg(context);
            db = dbHelper.getReadableDatabase();

            // Verificar si la tabla Cartas_obtenidas existe y tiene datos
            cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Cartas_obtenidas'", null);
            boolean tableExists = cursor != null && cursor.moveToFirst();
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }

            if (!tableExists) {
                Log.w(TAG, "Tabla Cartas_obtenidas no existe");
                return cardsList;
            }

            // Consultar las cartas en la colección
            cursor = db.rawQuery("SELECT id_carta_obtenida FROM Cartas_obtenidas", null);

            if (cursor == null || cursor.getCount() == 0) {
                Log.d(TAG, "No hay cartas en la colección");
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
                return cardsList;
            }

            // Procesar cada carta en la colección
            List<String> cardIds = new ArrayList<>();
            while (cursor.moveToNext()) {
                String cardId = cursor.getString(0);
                if (cardId != null && !cardId.isEmpty()) {
                    cardIds.add(cardId);
                }
            }
            cursor.close();
            cursor = null;

            // Obtener detalles de cada carta
            for (String cardId : cardIds) {
                try {
                    Cursor cardCursor = db.rawQuery(
                            "SELECT * FROM Cartas WHERE card_id = ?",
                            new String[]{cardId});

                    if (cardCursor != null && cardCursor.moveToFirst()) {
                        int cardIdIndex = cardCursor.getColumnIndex("card_id");
                        int rarityIndex = cardCursor.getColumnIndex("rarity");
                        int designationIndex = cardCursor.getColumnIndex("designation");
                        int nameIndex = cardCursor.getColumnIndex("name");
                        int lifeIndex = cardCursor.getColumnIndex("life");
                        int attributeIndex = cardCursor.getColumnIndex("attribute");
                        int powerIndex = cardCursor.getColumnIndex("power");
                        int counterIndex = cardCursor.getColumnIndex("counter");
                        int colorIndex = cardCursor.getColumnIndex("color");
                        int typeIndex = cardCursor.getColumnIndex("type");
                        int effectIndex = cardCursor.getColumnIndex("effect");
                        int triggerIndex = cardCursor.getColumnIndex("_trigger");
                        int cardSetIndex = cardCursor.getColumnIndex("card_set");
                        int notesIndex = cardCursor.getColumnIndex("notes");

                        // Verificar que todos los índices sean válidos
                        if (cardIdIndex >= 0 && rarityIndex >= 0 && designationIndex >= 0 &&
                                nameIndex >= 0 && lifeIndex >= 0 && attributeIndex >= 0 &&
                                powerIndex >= 0 && counterIndex >= 0 && colorIndex >= 0 &&
                                typeIndex >= 0 && effectIndex >= 0 && triggerIndex >= 0 &&
                                cardSetIndex >= 0 && notesIndex >= 0) {

                            String rarity = cardCursor.getString(rarityIndex);
                            String designation = cardCursor.getString(designationIndex);
                            String name = cardCursor.getString(nameIndex);
                            String life = cardCursor.getString(lifeIndex);
                            String attribute = cardCursor.getString(attributeIndex);
                            String power = cardCursor.getString(powerIndex);
                            String counter = cardCursor.getString(counterIndex);
                            String color = cardCursor.getString(colorIndex);
                            String type = cardCursor.getString(typeIndex);
                            String effect = cardCursor.getString(effectIndex);
                            String _trigger = cardCursor.getString(triggerIndex);
                            String card_set = cardCursor.getString(cardSetIndex);
                            String notes = cardCursor.getString(notesIndex);

                            // Convertir el cardId al formato de imagen
                            String imageName = convertCardIdToImageName(cardId);
                            int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

                            // Si no se encuentra la imagen, usar una por defecto
                            if (imageResId == 0) {
                                imageResId = R.drawable.ic_launcher_foreground;
                                Log.w(TAG, "Imagen no encontrada para carta: " + cardId);
                            }

                            // Crear la carta y añadirla a la lista
                            Cards card = new Cards(cardId, rarity, designation, name, life, attribute, power, counter,
                                    color, type, effect, _trigger, card_set, notes, imageResId);
                            cardsList.add(card);
                        } else {
                            Log.e(TAG, "Índices de columna no válidos para carta: " + cardId);
                        }
                    }

                    if (cardCursor != null) {
                        cardCursor.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error al procesar carta " + cardId + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error al obtener cartas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el cursor si sigue abierto
            if (cursor != null) {
                cursor.close();
            }
            // Cerrar la base de datos
            if (db != null) {
                db.close();
            }
        }

        Log.d(TAG, "Cargadas " + cardsList.size() + " cartas en la colección");
        return cardsList;
    }

    /**
     * Convertir el cardId al formato de nombre al de la imagen.
     *
     * @param cardId el id de la carta tomado de la base de datos para cambiarlo al formato correspondiente.
     * @return retorna el id para la imagen convertido al formato.
     */
    public static String convertCardIdToImageName(String cardId) {
        if (cardId == null || cardId.isEmpty()) {
            return "";
        }
        // Conversión del formato ST01-001 a st01001
        return cardId.toLowerCase().replace("-", "");
    }
}