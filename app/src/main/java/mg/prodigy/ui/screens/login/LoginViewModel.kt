package mg.prodigy.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mg.prodigy.data.model.Citizen
import mg.prodigy.data.repository.ProdigyRepository

/**
 * 1) L'ÉTAT (UiState)
 * Un seul objet immuable qui représente TOUT ce que l'écran doit afficher
 * à un instant donné. C'est la base du "flux unidirectionnel" (UDF) :
 * State descend vers l'UI, jamais l'inverse.
 */
data class LoginUiState(
    val cin: String = "",
    val motDePasse: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val citizenConnecte: Citizen? = null
)

/**
 * 2) LES ÉVÉNEMENTS (UiEvent)
 * L'UI ne modifie jamais l'état directement : elle envoie un événement
 * au ViewModel, qui décide comment faire évoluer l'état.
 * C'est le sens "remontant" du flux unidirectionnel : Event remonte
 * de l'UI vers le ViewModel.
 */
sealed interface LoginUiEvent {
    data class CinChanged(val value: String) : LoginUiEvent
    data class MotDePasseChanged(val value: String) : LoginUiEvent
    object SeConnecter : LoginUiEvent
}

/**
 * 3) LE VIEWMODEL (MVVM)
 * - Survit aux changements de configuration (rotation d'écran, etc.)
 * - Ne connaît AUCUNE classe Compose : il expose seulement un StateFlow.
 * - Toute la logique métier/appel réseau vit ici, pas dans le Composable.
 */
class LoginViewModel(
    private val repository: ProdigyRepository = ProdigyRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.CinChanged ->
                _uiState.update { it.copy(cin = event.value, errorMessage = null) }

            is LoginUiEvent.MotDePasseChanged ->
                _uiState.update { it.copy(motDePasse = event.value, errorMessage = null) }

            LoginUiEvent.SeConnecter -> seConnecter()
        }
    }

    private fun seConnecter() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.login(state.cin, state.motDePasse)
                .onSuccess { citizen ->
                    _uiState.update {
                        it.copy(isLoading = false, citizenConnecte = citizen)
                    }
                }
                .onFailure { erreur ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = erreur.message)
                    }
                }
        }
    }
}
