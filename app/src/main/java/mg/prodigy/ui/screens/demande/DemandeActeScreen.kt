package mg.prodigy.ui.screens.demande

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

private val TYPES_DISPONIBLES = listOf("Acte de naissance", "Copie CIN", "Casier judiciaire")

@Composable
fun DemandeActeScreen(
    onRetour: () -> Unit,
    viewModel: DemandeActeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Nouvelle demande", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Type de document")
        Column {
            TYPES_DISPONIBLES.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.typeSelectionne == type,
                        onClick = { viewModel.onEvent(DemandeActeUiEvent.TypeChanged(type)) }
                    )
                    Text(type)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onEvent(DemandeActeUiEvent.EnvoyerDemande) },
            enabled = !uiState.isEnvoiEnCours,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isEnvoiEnCours) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Envoyer la demande")
            }
        }
    }

    if (uiState.confirmationVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(DemandeActeUiEvent.FermerConfirmation) },
            title = { Text("Demande envoyée") },
            text = { Text("Votre dossier n° ${uiState.numeroDossier} a été enregistré.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onEvent(DemandeActeUiEvent.FermerConfirmation)
                    onRetour()
                }) { Text("OK") }
            }
        )
    }
}
