package com.example.onetcg20;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que provee la lista de cartas al RecyclerView.
 */
public class CardsProvide {

    /**
     * Tomamos las cartas de la base de datos para implementarlas en el RecyclerView.
     *
     * @param context contexto de la actividad de donde pedimos acceso a la base de datos.
     * @return retorna la lista de cartas.
     */
    public static List<Cards> getCardsList(Context context) {
        List<Cards> cardsList = new ArrayList<>();

        LogginActivity.bdonetcg dbHelper = new LogginActivity.bdonetcg(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "card_id",
                "rarity",
                "designation",
                "name",
                "life",
                "attribute",
                "power",
                "counter",
                "color",
                "type",
                "effect",
                "_trigger",
                "card_set",
                "notes"
        };

        Cursor cursor = db.query(
                "Cartas",
                projection, // The array of columns to return (pass null to get all)
                null,       // The columns for the WHERE clause
                null,       // The values for the WHERE clause
                null,       // Group the rows
                null,       // Filter by row groups
                null        // The sort order
        );

        while (cursor.moveToNext()) {
            String cardId = cursor.getString(cursor.getColumnIndexOrThrow("card_id"));
            String rarity = cursor.getString(cursor.getColumnIndexOrThrow("rarity"));
            String designation = cursor.getString(cursor.getColumnIndexOrThrow("designation"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String life = cursor.getString(cursor.getColumnIndexOrThrow("life"));
            String attribute = cursor.getString(cursor.getColumnIndexOrThrow("attribute"));
            String power = cursor.getString(cursor.getColumnIndexOrThrow("power"));
            String counter = cursor.getString(cursor.getColumnIndexOrThrow("counter"));
            String color = cursor.getString(cursor.getColumnIndexOrThrow("color"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String effect = cursor.getString(cursor.getColumnIndexOrThrow("effect"));
            String _trigger = cursor.getString(cursor.getColumnIndexOrThrow("_trigger"));
            String card_set = cursor.getString(cursor.getColumnIndexOrThrow("card_set"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));

            // Convertir el cardId al formato de imagen
            String imageName = convertCardIdToImageName(cardId);
            int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

            cardsList.add(new Cards(cardId, rarity, designation, name, life, attribute, power, counter,
                    color, type, effect, _trigger, card_set, notes, imageResId));
        }
        cursor.close();

        return cardsList;
    }

    /**
     * Convertir el cardId al formato de nombre al de la imagen.
     *
     * @param cardId el id de la carta tomado de la base de datos para cambiarlo al formato correspondiente.
     * @return retorna el id para la imagen convertido al formato.
     */
    public static String convertCardIdToImageName(String cardId) {
        // Conversión del formato ST01-001 a st01001
        return cardId.toLowerCase().replace("-", "");
    }
}


