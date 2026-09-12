package mg.prodigy.ui.screens.profil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mg.prodigy.data.model.Citizen

/**
 * Écran simple, sans ViewModel : le citoyen est passé directement en
 * paramètre (state hoisting). Pas besoin de logique/état local ici,
 * donc pas besoin de ViewModel — bon exemple à citer dans le rapport
 * pour montrer que MVVM s'utilise quand c'est utile, pas partout.
 */
@Composable
fun ProfilScreen(citizen: Citizen) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Identité Numérique", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoLigne("UIN (identifiant unique)", citizen.uin)
                InfoLigne("Nom", citizen.nom)
                InfoLigne("Prénom", citizen.prenom)
                InfoLigne("Date de naissance", citizen.dateNaissance)
                InfoLigne("Lieu de naissance", citizen.lieuNaissance)
                InfoLigne("N° CIN", citizen.cinNumero)
            }
        }
    }
}

@Composable
private fun InfoLigne(label: String, valeur: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(valeur, style = MaterialTheme.typography.bodyLarge)
    }
}
