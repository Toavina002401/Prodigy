package mg.prodigy.ui.screens.demande

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mg.prodigy.data.repository.ProdigyRepository

data class DemandeActeUiState(
    val typeSelectionne: String = "Acte de naissance",
    val isEnvoiEnCours: Boolean = false,
    val confirmationVisible: Boolean = false,
    val numeroDossier: String? = null
)

sealed interface DemandeActeUiEvent {
    data class TypeChanged(val type: String) : DemandeActeUiEvent
    object EnvoyerDemande : DemandeActeUiEvent
    object FermerConfirmation : DemandeActeUiEvent
}

class DemandeActeViewModel(
    private val repository: ProdigyRepository = ProdigyRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DemandeActeUiState())
    val uiState: StateFlow<DemandeActeUiState> = _uiState.asStateFlow()

    fun onEvent(event: DemandeActeUiEvent) {
        when (event) {
            is DemandeActeUiEvent.TypeChanged ->
                _uiState.update { it.copy(typeSelectionne = event.type) }

            DemandeActeUiEvent.EnvoyerDemande -> envoyerDemande()

            DemandeActeUiEvent.FermerConfirmation ->
                _uiState.update { it.copy(confirmationVisible = false) }
        }
    }

    private fun envoyerDemande() {
        viewModelScope.launch {
            _uiState.update { it.copy(isEnvoiEnCours = true) }
            val demande = repository.creerDemande(_uiState.value.typeSelectionne)
            _uiState.update {
                it.copy(
                    isEnvoiEnCours = false,
                    confirmationVisible = true,
                    numeroDossier = demande.id
                )
            }
        }
    }
}
