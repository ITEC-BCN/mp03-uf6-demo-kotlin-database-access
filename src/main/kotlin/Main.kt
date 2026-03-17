import dao.IUsuariDao
import dao.UsuariDao
import database.ConnectionManager
import database.DBSyncManager
import org.example.controllers.UsuariController
import view.TerminalView
import java.sql.Connection

fun main() {
    // 0. Sincronitzem les dades del SQLite local amb les dades del Supabase remot
    DBSyncManager.syncDatabases()

    // 1. Inicialitzem la connexió a BD a través del ConnectionManager que prioritzarà la Supabase remota
    val conn: Connection = ConnectionManager.getConnection()

    // 2. Instanciem el DAO amb la connexió desitjada
    val dao: IUsuariDao = UsuariDao(conn)

    // 3. Creem el controlador d'usuaris passant-li el dao creat
    val usuariController: UsuariController = UsuariController(dao)

    // 4. Creem i iniciem la vista
    val VISTA_TERMINAL: TerminalView = TerminalView()
    VISTA_TERMINAL.menuPrincipal(usuariController)

    // 5. Sincronitzem les dades del SQLite local amb les dades del Supabase remot amb els canvis que s'hagin fet durant la sessió d'ús de la app
    DBSyncManager.syncDatabases()
}