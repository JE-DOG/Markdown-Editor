import androidx.compose.ui.window.ComposeUIViewController
import ru.khinkal.markdown_editer.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
