package mg.prodigy.ui.screens.suivi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import mg.prodigy.data.model.DemandeActe
import mg.prodigy.data.model.StatutDemande

@Composable
fun SuiviScreen(viewModel: SuiviViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text("Mes dossiers", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(uiState.demandes) { demande ->
                    DemandeItem(demande)
                }
            }
        }
    }
}

@Composable
private fun DemandeItem(demande: DemandeActe) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(demande.type, style = MaterialTheme.typography.bodyLarge)
                Text(
                    "N° ${demande.id} · ${demande.dateDemande}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            StatutBadge(demande.statut)
        }
    }
}

@Composable
private fun StatutBadge(statut: StatutDemande) {
    val (texte, couleur) = when (statut) {
        StatutDemande.EN_ATTENTE -> "En attente" to MaterialTheme.colorScheme.outline
        StatutDemande.EN_TRAITEMENT -> "En traitement" to MaterialTheme.colorScheme.primary
        StatutDemande.PRET -> "Prêt" to MaterialTheme.colorScheme.tertiary
        StatutDemande.REJETE -> "Rejeté" to MaterialTheme.colorScheme.error
    }
    AssistChip(onClick = {}, label = { Text(texte) }, colors = AssistChipDefaults.assistChipColors())
}
