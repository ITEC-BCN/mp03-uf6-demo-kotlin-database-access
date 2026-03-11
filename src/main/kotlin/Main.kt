import org.example.controllers.SQLiteController
import view.TerminalView

fun main() {
    val usuarisController: SQLiteController = SQLiteController()
    val vista_terminal: TerminalView = TerminalView(usuarisController)

    // Executar la demo de BD en local
    vista_terminal.menuSQLite()
}