package mg.prodigy.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import mg.prodigy.data.model.Citizen

/**
 * 4) LE COMPOSABLE (UI)
 * Le Composable est "stateless" : il ne fait que LIRE l'état (uiState)
 * et ENVOYER des événements (viewModel.onEvent). Il ne contient aucune
 * logique métier.
 *
 * collectAsStateWithLifecycle() : à chaque nouvelle valeur émise par le
 * StateFlow du ViewModel, Compose déclenche une RECOMPOSITION, c'est-à-dire
 * qu'il ré-exécute cette fonction pour ne redessiner que les parties de
 * l'UI dont les données ont changé (pas tout l'écran).
 */
@Composable
fun LoginScreen(
    onLoginSuccess: (Citizen) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Effet de bord : dès que citizenConnecte devient non-null,
    // on déclenche la navigation vers l'écran suivant.
    LaunchedEffect(uiState.citizenConnecte) {
        uiState.citizenConnecte?.let { onLoginSuccess(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Identité Numérique",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Connectez-vous avec votre CIN",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.cin,
            onValueChange = { viewModel.onEvent(LoginUiEvent.CinChanged(it)) },
            label = { Text("Numéro CIN") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.motDePasse,
            onValueChange = { viewModel.onEvent(LoginUiEvent.MotDePasseChanged(it)) },
            label = { Text("Mot de passe") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        uiState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onEvent(LoginUiEvent.SeConnecter) },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Se connecter")
            }
        }
    }
}
