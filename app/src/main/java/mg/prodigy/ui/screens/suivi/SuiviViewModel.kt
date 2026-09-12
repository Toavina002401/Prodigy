package mg.prodigy.ui.screens.suivi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mg.prodigy.data.model.DemandeActe
import mg.prodigy.data.repository.ProdigyRepository

data class SuiviUiState(
    val isLoading: Boolean = true,
    val demandes: List<DemandeActe> = emptyList()
)

class SuiviViewModel(
    private val repository: ProdigyRepository = ProdigyRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SuiviUiState())
    val uiState: StateFlow<SuiviUiState> = _uiState.asStateFlow()

    init {
        chargerDemandes()
    }

    fun chargerDemandes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val liste = repository.getDemandes()
            _uiState.update { it.copy(isLoading = false, demandes = liste) }
        }
    }
}
