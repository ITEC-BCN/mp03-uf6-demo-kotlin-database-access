import dao.UsuariDao
import database.DatabaseManager
import database.SupabaseManager
import org.example.controllers.UsuariController
import view.TerminalView

fun main() {
    // Instanciem els DAO local i remot
    val usuariDaoLocal: UsuariDao = UsuariDao(DatabaseManager.connection)
    val usuariDaoRemot: UsuariDao = UsuariDao(SupabaseManager.connection)

    // Instanciem el UsuarisController amb la font de dades que vulguem
    val usuariController: UsuariController = UsuariController(usuariDaoRemot)

    // Creem l'objecte de la vista del terminal
    val VISTA_TERMINAL: TerminalView = TerminalView()

    // Executar la demo de BD en local
    VISTA_TERMINAL.menuPrincipal(usuariController)
}