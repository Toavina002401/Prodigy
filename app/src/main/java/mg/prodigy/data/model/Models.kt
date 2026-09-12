package mg.prodigy.data.model

/**
 * Représente le profil "identité numérique" d'un citoyen.
 * Inspiré de l'objectif réel de Prodigy : donner à chaque Malagasy
 * un identifiant unique et vérifiable (lutte contre les CIN frauduleuses).
 */
data class Citizen(
    val uin: String,              // Identifiant Unique National (concept clé de Prodigy)
    val nom: String,
    val prenom: String,
    val dateNaissance: String,
    val lieuNaissance: String,
    val cinNumero: String
)

/**
 * Statut possible d'une demande de document administratif
 * (ex : acte de naissance), un des services publics ciblés par Prodigy.
 */
enum class StatutDemande {
    EN_ATTENTE,
    EN_TRAITEMENT,
    PRET,
    REJETE
}

data class DemandeActe(
    val id: String,
    val type: String,           // ex: "Acte de naissance", "Copie CIN"
    val dateDemande: String,
    val statut: StatutDemande
)
