package fr.humanbooster.fx.kanban.service;

import java.util.Date;
import java.util.List;

import fr.humanbooster.fx.kanban.business.Colonne;
import fr.humanbooster.fx.kanban.business.Developpeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.humanbooster.fx.kanban.business.Tache;

public interface TacheService {

	Tache ajouterTache(String intitule);

	Tache ajouterTache(Tache tache);
	
	List<Tache> recupererTaches();

	Tache recupererTache(Long id);

	void supprimerTache(Tache tache);

	boolean supprimerTache(Long id);

	Tache enregistrerTache(Tache tache);

	Page<Tache> recupererTaches(Pageable pageable);

	Tache majTache(Tache tache, String description);

	Page<Tache> recupererTaches(Colonne colonne, Pageable pageable);

	List<Tache> recupererTachesAFaire(Developpeur developpeur);

	List<Tache> recupererTaches(String intitule);

	int recupererTotalHeuresPrevues(Date dateDebut, Date dateFin);

	Tache deplacerTache(Tache tache, Colonne colonne);
}
