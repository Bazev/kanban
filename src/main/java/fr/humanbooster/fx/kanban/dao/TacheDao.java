package fr.humanbooster.fx.kanban.dao;

import fr.humanbooster.fx.kanban.business.Colonne;
import fr.humanbooster.fx.kanban.business.Developpeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.humanbooster.fx.kanban.business.Tache;

import java.util.Date;
import java.util.List;

public interface TacheDao extends JpaRepository<Tache, Long> {


    Page<Tache> findAllByColonne(Colonne colonne, Pageable pageable);

    List<Tache> findAllTachesAFaire(Developpeur developpeur);

    List<Tache> findAllByIntituleContains(String intitule);

    int findTotalHeuresPrevues(Date dateDebut, Date dateFin);
}