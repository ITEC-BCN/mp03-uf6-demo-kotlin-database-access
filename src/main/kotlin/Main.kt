import dao.UsuariDao
import database.DatabaseManager
import database.SupabaseManager
import org.example.controllers.UsuariController
import view.TerminalView

fun main() {
    val VISTA_TERMINAL: TerminalView = TerminalView()
    val usuariDaoLocal: UsuariDao = UsuariDao(DatabaseManager.connection)
    val usuariDaoRemot: UsuariDao = UsuariDao(SupabaseManager.connection)

    val usuariControllerRemot: UsuariController = UsuariController(usuariDaoRemot)

    // Executar la demo de BD en local
    VISTA_TERMINAL.menuSQLite(usuariControllerRemot)
}