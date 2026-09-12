package mg.prodigy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Couleurs inspirées du drapeau malgache (blanc / rouge / vert)
private val ProdigyColors = lightColorScheme(
    primary = Color(0xFF008751),      // vert
    secondary = Color(0xFFFC3D32),    // rouge
    background = Color(0xFFFFFFFF)
)

@Composable
fun ProdigyIDTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ProdigyColors,
        content = content
    )
}
