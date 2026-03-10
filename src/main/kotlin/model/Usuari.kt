package model

public class Usuari {
    private var id: Int
    private var nom: String
    private var nivell: NivellUsuari

    constructor(id: Int, nom: String, nivell: NivellUsuari){
        this.id = id
        this.nom = nom
        this.nivell = nivell
    }

    public fun getId(): Int{
        return this.id
    }

    public fun getNom(): String{
        return this.nom
    }

    public fun getNivell(): String{
        return this.nivell.valor
    }

    override public fun toString(): String {
        return "[usuari@ id: $id -> '$nom' és '$nivell']"
    }
}