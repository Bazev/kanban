package fr.humanbooster.fx.kanban.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.humanbooster.fx.kanban.business.Tache;

public interface TacheDao extends JpaRepository<Tache, Long> {

	
}