import dao.IUsuariDao
import dao.UsuariDao
import database.DatabaseManager
import database.SupabaseManager
import org.example.controllers.UsuariController
import view.TerminalView
import java.sql.Connection

fun main() {
    // 1. Definim les connexions a BD local i remota
    val conLocal: Connection = DatabaseManager.connection
    val conRemot: Connection = SupabaseManager.connection

    // 2. Instanciem el DAO amb la connexió desitjada
    val dao: IUsuariDao = UsuariDao(conRemot)

    // 3. Creem el controlador d'usuaris passant-li el dao creat
    val usuariController: UsuariController = UsuariController(dao)

    // 4. Creem i iniciem la vista
    val VISTA_TERMINAL: TerminalView = TerminalView()
    VISTA_TERMINAL.menuPrincipal(usuariController)
}