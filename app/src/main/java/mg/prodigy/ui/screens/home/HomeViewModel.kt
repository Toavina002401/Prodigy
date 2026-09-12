package mg.prodigy.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mg.prodigy.data.model.Citizen

/**
 * État de l'écran d'accueil : simplement le citoyen connecté et
 * la liste des services proposés (miroir des 2 composantes de Prodigy :
 * gestion de l'identité + services publics).
 */
data class HomeUiState(
    val citizen: Citizen? = null,
    val services: List<String> = listOf(
        "Demande d'acte de naissance",
        "Vérification CIN",
        "Suivi de mes dossiers"
    )
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun setCitizen(citizen: Citizen) {
        _uiState.value = _uiState.value.copy(citizen = citizen)
    }
}
