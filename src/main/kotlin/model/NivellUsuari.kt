package model

enum class NivellUsuari(val valor: String) {
    DOCENT("docent"),
    COORDINADOR("coordinador"),
    DIRECTIU("directiu");

    // Mètode static que reb un String per paràmetre i intenta retornar el NivellUsuari equivalent
    companion object {
        fun fromString(valor: String): NivellUsuari {
            var nivellUsuari: NivellUsuari?

            nivellUsuari = NivellUsuari.entries.find { it.valor.equals(valor, ignoreCase = true) }

            if (nivellUsuari == null){
                nivellUsuari = NivellUsuari.DOCENT
            }

            return nivellUsuari
        }
    }
}