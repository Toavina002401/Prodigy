package mg.prodigy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mg.prodigy.data.model.Citizen
import mg.prodigy.ui.screens.demande.DemandeActeScreen
import mg.prodigy.ui.screens.home.HomeScreen
import mg.prodigy.ui.screens.login.LoginScreen
import mg.prodigy.ui.screens.profil.ProfilScreen
import mg.prodigy.ui.screens.suivi.SuiviScreen

/**
 * Chaque écran (Composable) ne connaît QUE ses propres paramètres et
 * callbacks (onServiceClick, onRetour...). C'est le NavGraph qui décide
 * quel écran afficher ensuite. Cela garde le flux unidirectionnel intact
 * même au niveau de la navigation : les écrans ne naviguent pas eux-mêmes,
 * ils "remontent" l'intention (callback), et c'est ce niveau supérieur qui agit.
 */
object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val DEMANDE = "demande"
    const val SUIVI = "suivi"
    const val PROFIL = "profil"
}

@Composable
fun ProdigyNavGraph(navController: NavHostController = rememberNavController()) {
    // On garde le citoyen connecté au niveau du graphe de navigation.
    // remember { mutableStateOf(...) } = état Compose qui survit aux
    // recompositions (mais pas à une rotation d'écran ou à la fermeture
    // du process ; dans une vraie app on utiliserait un ViewModel partagé
    // au niveau de l'Activity ou un DataStore/session).
    var citizenConnecte by remember { mutableStateOf<Citizen?>(null) }

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { citizen ->
                    citizenConnecte = citizen
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            citizenConnecte?.let { citizen ->
                HomeScreen(
                    citizen = citizen,
                    onServiceClick = { service ->
                        when (service) {
                            "Demande d'acte de naissance" -> navController.navigate(Routes.DEMANDE)
                            "Suivi de mes dossiers" -> navController.navigate(Routes.SUIVI)
                            else -> navController.navigate(Routes.DEMANDE)
                        }
                    },
                    onProfilClick = { navController.navigate(Routes.PROFIL) }
                )
            }
        }

        composable(Routes.DEMANDE) {
            DemandeActeScreen(onRetour = { navController.popBackStack() })
        }

        composable(Routes.SUIVI) {
            SuiviScreen()
        }

        composable(Routes.PROFIL) {
            citizenConnecte?.let { citizen -> ProfilScreen(citizen = citizen) }
        }
    }
}
