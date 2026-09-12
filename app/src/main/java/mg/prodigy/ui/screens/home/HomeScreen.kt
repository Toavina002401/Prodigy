package mg.prodigy.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import mg.prodigy.data.model.Citizen

@Composable
fun HomeScreen(
    citizen: Citizen,
    onServiceClick: (String) -> Unit,
    onProfilClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    // On "pousse" le citoyen connecté (reçu via la navigation) dans l'état
    // du ViewModel une seule fois, au premier affichage de l'écran.
    LaunchedEffect(citizen) { viewModel.setCitizen(citizen) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Bonjour,", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "${uiState.citizen?.prenom ?: ""} ${uiState.citizen?.nom ?: ""}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            IconButton(onClick = onProfilClick) {
                // Ici tu peux mettre Icons.Default.Person (import à ajouter)
                Text("👤")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Services disponibles", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.services) { service ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onServiceClick(service) }
                ) {
                    Text(
                        text = service,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
