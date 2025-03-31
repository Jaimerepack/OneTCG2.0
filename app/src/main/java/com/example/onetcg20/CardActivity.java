package com.example.onetcg20;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Actividad de la carta cuando se abre una en espesifico.
 */
public class CardActivity extends AppCompatActivity {
    /**
     * Declaracion de botones
     */
    private Button btnVolver, btnAddCollection, btnRemoveCollection, btnAddDeck;
    /**
     * Declaracion de ImageView para la carta
     */
    private ImageView ivCard;
    /**
     * Declaracion de TextView para los datos de la carta.
     */
    private TextView tvCardRarity, tvCardId, tvCardDesignation, tvCardName, tvCardLife, tvCardAttribute,
            tvCardPower, tvCardCounter, tvCardColor, tvCardType, tvCardEffect, tvCardSet, tvCardNotes, tvCard_trigger,
            tvCardQuantity, tvQuantity;
    /**
     * Creacion de la actividad
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        referencias();
        eventos();
        /**
         * seleccionamos si queremos que el boton de eliminar carta se veo o no
         */
        if (Utilitys.displayBtn == Utilitys.btnRemoveOn){
            btnRemoveCollection.setVisibility(View.GONE);
            tvQuantity.setVisibility(View.GONE);
            tvCardQuantity.setVisibility(View.GONE);
        }
        /**
         * Tomamos lo que se envio del fragment para ocuparlo en los TextView
         */
        Intent intent = getIntent();
        String cardId = intent.getStringExtra("cardSerie");
        String cantidad = String.valueOf(getAmountOfCardObtained(cardId));

        ivCard.setImageResource(intent.getIntExtra("cardImageResId", -1));
        tvCardId.setText(intent.getStringExtra("cardSerie"));
        tvCardRarity.setText(intent.getStringExtra("cardRarity"));
        tvCardDesignation.setText(intent.getStringExtra("cardDesignation"));
        tvCardName.setText(intent.getStringExtra("cardName"));
        tvCardLife.setText(intent.getStringExtra("cardLife"));
        tvCardAttribute.setText(intent.getStringExtra("cardAttribute"));
        tvCardPower.setText(intent.getStringExtra("cardPower"));
        tvCardCounter.setText(intent.getStringExtra("cardCounter"));
        tvCardColor.setText(intent.getStringExtra("cardColor"));
        tvCardType.setText(intent.getStringExtra("cardType"));
        tvCardEffect.setText(intent.getStringExtra("cardEffect"));
        tvCardSet.setText(intent.getStringExtra("cardCard_set"));
        tvCardNotes.setText(intent.getStringExtra("cardNotes"));
        tvCard_trigger.setText(intent.getStringExtra("card_trigger"));
        tvCardQuantity.setText(cantidad);
    };

    /**
     * Referencias de botones y textView.
     */
    public void referencias(){
        btnVolver = findViewById(R.id.btnVolver);
        ivCard = findViewById(R.id.ivCard);
        tvCardId = findViewById(R.id.tvCardId);
        tvCardRarity = findViewById(R.id.tvCardRarity);
        tvCardDesignation = findViewById(R.id.tvCardDesignation);
        tvCardName = findViewById(R.id.tvCardName);
        tvCardPower = findViewById(R.id.tvCardPower);
        tvCardAttribute = findViewById(R.id.tvCardAttribute);
        tvCardCounter = findViewById(R.id.tvCardCounter);
        tvCardColor = findViewById(R.id.tvCardColor);
        tvCardType = findViewById(R.id.tvCardType);
        tvCardEffect = findViewById(R.id.tvCardEffect);
        tvCardSet = findViewById(R.id.tvCardSet);
        tvCardNotes = findViewById(R.id.tvCardNotes);
        tvCard_trigger = findViewById(R.id.tvCard_trigger);
        tvCardLife = findViewById(R.id.tvCardLife);
        tvCardQuantity = findViewById(R.id.tvCardQuantity);
        tvQuantity = findViewById(R.id.tvQuantity);
        btnAddCollection = findViewById(R.id.btnAddCollector);
        btnRemoveCollection = findViewById(R.id.btnRemoveCollector);
        btnAddDeck = findViewById(R.id.btnAddDeck);
    }

    /**
     * Eventos de los botones y textView.
     */
    public void eventos(){
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String cardId = tvCardId.getText().toString();
            Integer cantidad = 1;
            registerCollection(cardId,cantidad);
            String cantidad2 = String.valueOf(getAmountOfCardObtained(cardId));
            tvCardQuantity.setText(cantidad2);
            }
        });

        btnRemoveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardId = tvCardId.getText().toString();
                Integer cantidad = -1;
                registerCollection(cardId,cantidad);
                String cantidad2 = String.valueOf(getAmountOfCardObtained(cardId));
                tvCardQuantity.setText(cantidad2);
            }
        });

        btnAddDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CardActivity.this,"Deck construction coming soon",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo para registrar las cartas en la coleccion.
     *
     * @param cardId   Id de la carta.
     * @param cantidad Cantidad de copias de la carta
     */
    public void registerCollection(String cardId, Integer cantidad) {
        SQLiteOpenHelper dbHelper = new LogginActivity.bdonetcg(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        /**
         * Verificar si la carta ya existe
         */
        Cursor cursor = db.rawQuery("SELECT * FROM Cartas_obtenidas WHERE id_carta_obtenida = ?", new String[]{cardId});
        if (cursor.moveToFirst()) {
            int cantidadIndex = cursor.getColumnIndex("cantidad");
            if (cantidadIndex != -1) {
                /**
                 * La carta ya existe, obtener la cantidad actual y actualizarla
                 */
                int cantidadActual = cursor.getInt(cantidadIndex);
                int nuevaCantidad = cantidadActual + cantidad;
                if (nuevaCantidad != 0){
                    db.execSQL("UPDATE Cartas_obtenidas SET cantidad = ? WHERE id_carta_obtenida = ?", new Object[]{nuevaCantidad, cardId});
                    Toast.makeText(this, "Cantidad de carta actualizada", Toast.LENGTH_SHORT).show();
                }else {
                    db.execSQL("DELETE FROM Cartas_obtenidas WHERE id_carta_obtenida = ?", new Object[]{cardId});
                    Toast.makeText(this, "La carta se elimino de la coleccion", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            /**
                 * La carta no existe, insertar la nueva carta
                 */
            if (cantidad == 1){
                db.execSQL("INSERT INTO Cartas_obtenidas (id_carta_obtenida, cantidad) VALUES (?, ?)",
                        new Object[]{cardId, cantidad});
                Toast.makeText(this, "Carta añadida", Toast.LENGTH_SHORT).show();
            }

        }
        cursor.close();
        db.close();
    }

    private int getAmountOfCardObtained(String cardId) {
        SQLiteOpenHelper dbHelper = new LogginActivity.bdonetcg(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta para obtener la cantidad de cartas obtenidas para el cardId específico
        Cursor cursor = db.rawQuery("SELECT cantidad FROM Cartas_obtenidas WHERE id_carta_obtenida = ?",
                new String[]{cardId});

        int cantidad = 0;
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"));
        }
        cursor.close();
        db.close();
        return cantidad;
    }
}