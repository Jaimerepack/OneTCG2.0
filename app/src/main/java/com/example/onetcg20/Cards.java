package com.example.onetcg20;

/**
 * CLase cartas que da la estructura de las cartas para el RecyclerView
 */
public class Cards {
    /**
     * Declaracion de variables para definir las cartas.
     */
    private String serie;
    private String rarity;
    private String designation;
    private String nombre;
    private String life;
    private String attribute;
    private String power;
    private String counter;
    private String color;
    private String type;
    private String effect;
    private String _trigger;
    private String card_set;
    private String notes;
    private int imageResId;

    /**
     * Creamos la instancia de la nueva carta.
     *
     * @param serie       N°Serie de la carta.
     * @param rarity      Rareza de la carta.
     * @param designation Designacion de la carta.
     * @param nombre      Nombre de la carta.
     * @param life        Vida de la carta.
     * @param attribute   Atributo de la carta.
     * @param power       Poder de la carta.
     * @param counter     Marcador de Counter de la carta.
     * @param color       Color de la carta.
     * @param type        Tipo de la carta.
     * @param effect      Efecto de la carta.
     * @param _trigger    Efecto Trigger de la carta.
     * @param card_set    N°Serie de la edicion de la carta.
     * @param notes       Notas extera de la carta.
     * @param imageResId  Id para relacionar la carta con la imagen.
     */
    public Cards(String serie, String rarity, String designation, String nombre,
                 String life, String attribute, String power,String counter,String color,String type,
                 String effect, String _trigger,String card_set,String notes, int imageResId) {
        this.serie = serie;
        this.rarity = rarity;
        this.designation = designation;
        this.nombre = nombre;
        this.life = life;
        this.attribute = attribute;
        this.power = power;
        this.counter = counter;
        this.color = color;
        this.type = type;
        this.effect = effect;
        this._trigger = _trigger ;
        this.card_set = card_set ;
        this.notes = notes ;
        this.imageResId = imageResId;
    }

    /**
     * Metodo para obtener el N°Serie de la carta.
     *
     * @return Retorna el n°de serie de la carta.
     */
    public String getSerie() { return serie; }

    /**
     * Metodo para obtener la rareza de la carta.
     *
     * @return retorna la rareza.
     */
    public String getRarity() { return rarity; }

    /**
     * Metodo para obtener la designacion de la carta.
     *
     * @return retorna la designacion de la carta.
     */
    public String getDesignation() { return designation; }

    /**
     * Metodo para obtener el Nombre de la carta.
     *
     * @return Retorna el nombre de la carta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo para obtener la vida de la carta.
     *
     * @return Retorna la vida de la carta.
     */
    public String getLife() { return life; }

    /**
     * Metodo para obtener el Atributo de la carta.
     *
     * @return Retorna el atributo de la carta.
     */
    public String getAttribute() { return attribute; }

    /**
     * Metodo para obtener el Poder de la carta..
     *
     * @return Retorna el poder de la carta.
     */
    public String getPower() {
        return power;
    }

    /**
     * Metodo para obtener el Marcador de counter de la carta.
     *
     * @return Retorna el marcador de counter de la carta.
     */
    public String getCounter() { return counter; }

    /**
     * Metodo para obtener el Color de la carta.
     *
     * @return Retorna el color de la carta.
     */
    public String getColor() { return color; }

    /**
     * Metodo para obtener el tipo de la carta.
     *
     * @return Retorna el tipo de la carta
     */
    public String getType() { return type; }

    /**
     * Metodo para obtener el efecto de la carta.
     *
     * @return Retorna el efecto de la carta.
     */
    public String getEffect() { return effect; }

    /**
     * Metodo para obtener el efecto Trigger de la carta.
     *
     * @return Retornamos el efecto Trigger de la carta.
     */
    public String get_trigger() { return _trigger; }

    /**
     * Metodo para obtener el N° de la edicion de la carta.
     *
     * @return Retornamos el n° de la edicion de la carta.
     */
    public String getCard_set() { return card_set; }

    /**
     * Metodo para obtener las notas de la carta.
     *
     * @return Retornamos las notas de la carta.
     */
    public String getNotes() { return notes; }

    /**
     * Metodo para obtener el Id para la imagen de la carta.
     *
     * @return Retrona el id para la imagen de la carta.
     */
    public int getImageResId() {
        return imageResId;
    }
}