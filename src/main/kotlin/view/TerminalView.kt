package view

import model.NivellUsuari
import model.Usuari
import org.example.controllers.SQLiteController
import org.example.readInt
import org.example.readSentence

class TerminalView{
    private val usuarisController: SQLiteController

    constructor(usuarisController: SQLiteController){
        this.usuarisController = usuarisController
    }

    /**
     * Funció que mostra el menú principal a la vista de terminal
     * @author RIS
     */
    public fun menuSQLite() {
        do {
            println("############")
            println("### MENÚ ###")
            println("############")
            println(
                """
            1. Mostrar dades
            2. Afegir usuari
            3. Modificar usuari
            4. Eliminar usuari
            5. Esborrar llista completa
            0. Sortir
        """.trimIndent()
            )
            val opcio = readInt("Tria una opció", "Ha de ser numèric", "Les opcions son entre 0 i 5", 0, 5)
            when (opcio) {
                1 -> mostrarUsuaris()
                2 -> upsertUsuari()
                3 -> modificarUsuari()
                4 -> eliminarUsuari()
                5 -> esborrarLlista()
                0 -> println("A reveure!")
            }
        } while (opcio != 0)
    }

    /**
     * Funció que recupera el llistat d'usuaris i els mostra pel terminal
     * @param usuarisController reb una instància de SQLiteController per poder accedir als mètodes del controlador d'Usuaris
     * @author RIS
     */
    private fun mostrarUsuaris() {
        // Recuperem tots els usuaris de la BD cridant el mètode getUsuaris() del Controller
        val llistatUsuaris: List<Usuari> = usuarisController.getUsuaris()

        if (llistatUsuaris.isEmpty()){
            println("Actualment la llista d'usuaris/es és buida")
        }else {
            println("##########################")
            println("### USUARIS ACTUALS A LA BD ###")
            println("##########################")

            for (u in llistatUsuaris) {
                println(u)
            }
        }
    }

    /**
     * Aquesta funció permet inserir nous usuaris o modificar-ne de ja existents. Per això es diu UPSERT (INSERT & UPDATE)
     * Demana les dades per teclat sobre quin usuari es vol modificar i fa una actualització de dades
     * @param usuarisController reb una instància de SQLiteController per poder accedir als mètodes del controlador d'Usuaris
     * @param id és un paràmetre opcional de tipus Int. Si no se li passa, demana el seu valor per paràmetre.
     * @param update és un paràmetre opcional de tipus Boolean. Per defecte és false i, per tant, es fa INSERT
     * @author RIS
     */
    private fun upsertUsuari(id: Int = -1, update: Boolean = false) {
        println("#####################")
        println("### NOU USUARI ###")
        println("#####################")
        var idUsuari: Int = id

        if (idUsuari == -1) {
            // Aquesta part es pot millorar per tal d'evitar repetir ids d'Usuari
            // Només caldria comprovar si l'id introduït per l'usuari@ ja existeix a la BD o no
            idUsuari = readInt(
                "Escriu l'id de l'usuari a crear",
                "Has d'escriure un enter",
                "Valor fora de rang. Has d'escriure un valor entre 1 i ${Int.MAX_VALUE}",
                1,
                Int.MAX_VALUE
            )
        }

        val nomUsuari: String = readSentence("Introdueix el NOM de l'usuari", "Ha de ser un text")
        var nivellString: String

        var nivellCorrecte: Boolean = false
        do {
            nivellString = readSentence(
                "Introdueix el NIVELL D'ACCÉS de l'usuari dins dels següents ${NivellUsuari.entries}",
                "Ha de ser un dels valors ${NivellUsuari.entries}"
            )

            for (n in NivellUsuari.entries) {
                if (n.name == nivellString)
                    nivellCorrecte = true
            }
        } while (!nivellCorrecte)

        val nivellEnum = NivellUsuari.fromString(nivellString)

        // Creem el nou usuari per a ser inserit a BD
        val nouUsuari: Usuari = Usuari(idUsuari, nomUsuari, nivellEnum)

        if (!update) {
            // La crida al mètode usuarisController.inserirUsuari(nouUsuari) retorna el número de files que han estat afectades després d'executar la sentència SQL
            println("S'han inserit ${usuarisController.inserirUsuari(nouUsuari)} usuaris/es")
        } else {
            // La crida al mètode usuarisController.modificarUsuari(nouUsuari) retorna el número de files que han estat afectades després d'executar la sentència SQL
            println("S'han modificat ${usuarisController.modificarUsuari(nouUsuari)} usuaris/es")
        }
    }

    /**
     * Funció que permet modificar les dades d'un Usuari
     * @param usuarisController reb una instància de SQLiteController per poder accedir als mètodes del controlador d'Usuaris
     * @author RIS
     */
    private fun modificarUsuari() {
        val llistatIds: List<Int> = usuarisController.idsUsuari()
        var nouIdUsuari: Int = -1

        if (llistatIds.isEmpty()) {
            println("Actualment no hi ha cap usuari@ a la BD, es procedirà a crear-ne un de nou...")
            // Aprofitem el mateix mètode de crearUsuari que ja hem definit abans
            upsertUsuari()
        } else {
            println("Actualment hi ha els següents ids d'usuari a dins de la BD:")
            for (id in llistatIds) {
                println(id)
            }
            nouIdUsuari = readInt(
                "Escriu l'id de l'usuari a modificar",
                "Has d'escriure un enter",
                "Valor fora de rang. Has d'escriure un valor entre ${llistatIds.min()} i ${llistatIds.max()} i que existeixi entre els mostrats anteriorment",
                llistatIds.min(),
                llistatIds.max()
            )

            val usuariModificar: Usuari? = usuarisController.getUsuariById(nouIdUsuari)
            if (usuariModificar != null) {
                println("L'usuari@ a modificar és...")
                println(usuariModificar)
                // Cridem a la funció upsert amb el modificador update = true per tal de que executi un UPDATE i no un INSERT
                upsertUsuari( nouIdUsuari, true)
            } else {
                println("Id d'usuari@ no trobat, es procedirà a crear un usuari@ nou amb id = $nouIdUsuari ...")
                // Aprofitem el mateix mètode de crearUsuari que ja hem definit abans passant-li l'id que s'ha inserit
                upsertUsuari( nouIdUsuari)
            }
        }
    }

    /**
     * Funció demanarà les dades per pantalla de l'usuari a eliminar i, si el troba, cridarà a la funció d'eliminar de controller.
     * @param usuarisController Controlador que permet gestionar els estats dels usuaris
     * @author RIS
     */
    private fun eliminarUsuari() {
        val llistatIds: List<Int> = usuarisController.idsUsuari()
        var idUsuariEliminar: Int = -1

        if (llistatIds.isEmpty()) {
            println("Actualment no hi ha cap usuari@ a dins de la llista per a poder ser esborrat.")
        } else {
            println("Actualment hi ha els següents ids d'usuari a dins de la BD:")
            for (id in llistatIds) {
                println(id)
            }
            idUsuariEliminar = readInt(
                "Escriu l'id de l'usuari a eliminar",
                "Has d'escriure un enter",
                "Valor fora de rang. Has d'escriure un valor entre ${llistatIds.min()} i ${llistatIds.max()} i que existeixi entre els mostrats anteriorment",
                llistatIds.min(),
                llistatIds.max()
            )

            val usuariEliminar: Usuari? = usuarisController.getUsuariById(idUsuariEliminar)
            // Si s'ha trobat l'Usuari
            if (usuariEliminar != null) {
                println("L'usuari@ a eliminar és...")
                println(usuariEliminar)
                // Cridem a la funció eliminar usuaris
                println("S'han eliminat ${usuarisController.eliminarUsuari(usuariEliminar)} usuaris/es")
            } else {
                println("Id d'usuari@ no trobat, no s'esborrarà a ningú!")
            }
        }
    }

    /**
     * Funció demanarà les dades per pantalla de l'usuari a eliminar i, si el troba, cridarà a la funció d'eliminar de controller.
     * @param usuarisController Controlador que permet gestionar els estats dels usuaris
     * @author RIS
     */
    private fun esborrarLlista() {
        val llistatIds: List<Int> = usuarisController.idsUsuari()

        if (llistatIds.isEmpty()){
            println("No hi ha res per esborrar!")
        }else{
            println("S'han eliminat ${usuarisController.eliminarLlista()} usuaris/es")
        }
    }
}