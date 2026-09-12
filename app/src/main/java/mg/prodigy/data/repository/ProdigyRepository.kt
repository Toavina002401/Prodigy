package mg.prodigy.data.repository

import kotlinx.coroutines.delay
import mg.prodigy.data.model.Citizen
import mg.prodigy.data.model.DemandeActe
import mg.prodigy.data.model.StatutDemande

/**
 * Repository = couche qui isole l'UI de la source de données réelle
 * (API, base locale, etc.). Ici on simule un appel réseau avec delay().
 *
 * Dans une vraie app connectée à Prodigy, cette classe appellerait
 * l'API du portail Torolalana / X-Road au lieu de renvoyer des données
 * fictives.
 */
class ProdigyRepository {

    private val demandes = mutableListOf(
        DemandeActe("D-001", "Acte de naissance", "02/09/2026", StatutDemande.EN_TRAITEMENT),
        DemandeActe("D-002", "Copie CIN", "28/08/2026", StatutDemande.PRET)
    )

    suspend fun login(cin: String, motDePasse: String): Result<Citizen> {
        delay(800) // simule la latence réseau
        return if (cin.length >= 6 && motDePasse.isNotBlank()) {
            Result.success(
                Citizen(
                    uin = "MDG-2026-0417832",
                    nom = "RAKOTO",
                    prenom = "Belouh",
                    dateNaissance = "12/05/2001",
                    lieuNaissance = "Antananarivo",
                    cinNumero = cin
                )
            )
        } else {
            Result.failure(IllegalArgumentException("Identifiants invalides"))
        }
    }

    suspend fun getDemandes(): List<DemandeActe> {
        delay(500)
        return demandes.toList()
    }

    suspend fun creerDemande(type: String): DemandeActe {
        delay(700)
        val nouvelle = DemandeActe(
            id = "D-${(100..999).random()}",
            type = type,
            dateDemande = "10/09/2026",
            statut = StatutDemande.EN_ATTENTE
        )
        demandes.add(0, nouvelle)
        return nouvelle
    }
}
