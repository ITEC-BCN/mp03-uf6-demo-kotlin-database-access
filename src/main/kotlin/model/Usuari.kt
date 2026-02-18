package model

class Usuari {
    private var id: Int
    private var nom: String
    private var nivell: NivellUsuari

    constructor(id: Int, nom: String, nivell: NivellUsuari){
        this.id = id
        this.nom = nom
        this.nivell = nivell
    }

    override fun toString(): String {
        return "Usuari(id=$id, nom='$nom', nivell='$nivell')"
    }

}