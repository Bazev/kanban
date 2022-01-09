package fr.humanbooster.fx.kanban.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.humanbooster.fx.kanban.business.Tache;

public interface TacheService {

	Tache ajouterTache(String intitule);
	
	List<Tache> recupererTaches();

	Tache recupererTache(Long id);

	void supprimerTache(Tache tache);

	Tache enregistrerTache(Tache tache);

	Page<Tache> recupererTaches(Pageable pageable);
}
