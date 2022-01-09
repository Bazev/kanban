package fr.humanbooster.fx.kanban.controller;

import fr.humanbooster.fx.kanban.business.Colonne;
import fr.humanbooster.fx.kanban.business.Developpeur;
import fr.humanbooster.fx.kanban.business.Tache;
import fr.humanbooster.fx.kanban.service.ColonneService;
import fr.humanbooster.fx.kanban.service.DeveloppeurService;
import fr.humanbooster.fx.kanban.service.TacheService;
import fr.humanbooster.fx.kanban.service.TypeTacheService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("ws")
public class KanbanRestController {

    private Random random;
    private final TacheService tacheService;
    private final TypeTacheService typeTacheService;
    private final ColonneService colonneService;
    private final DeveloppeurService developpeurService;

    public KanbanRestController(TacheService tacheService, TypeTacheService typeTacheService, ColonneService colonneService, DeveloppeurService developpeurService) {
        this.random = new Random();
        this.tacheService = tacheService;
        this.typeTacheService = typeTacheService;
        this.colonneService = colonneService;
        this.developpeurService = developpeurService;
    }


    /**
     * méthode qui ajoute une tâche en précisant son intitulé et le type de tâche. La
     * méthode choisira au hasard un développeur et un nombre d’heures prévues entre 1
     * et 144. La tâche sera ajoutée sur la colonne 1 : « A faire »
     * @param nom
     * @param type
     * @return
     */
    @PostMapping("taches/{nom}/{type}")
    public Tache ajouterTache(@PathVariable String nom, @PathVariable String type){

        Tache tache = new Tache();
        tache.setIntitule(nom);
        tache.setTypeTache(typeTacheService.recupererTypeTache(type));
        tache.setColonne(colonneService.recupererColonne("A faire"));
        Long idDev = (long)random.nextInt(144 - 1) + 1;
        Developpeur dev = developpeurService.recupererDeveloppeur(idDev);
        if(dev!=null){
            tache.getDeveloppeurs().add(dev);
        }
        int nbHeures = random.nextInt(100 - 1) + 1;
        tache.setNbHeuresEstimees(nbHeures);
        return tacheService.ajouterTache(tache);
    }

    /**
     *  méthode permettant d’obtenir toutes les informations sur une tâche
     * @param id
     * @return
     */
    @GetMapping("taches/{id}")
    Tache récupérerInfosTache(@PathVariable Long id){
        return tacheService.recupererTache(id);
    }

    /**
     * méthode qui permet de mettre à jour l’intitulé d’une tâche dont l’id est
     * précisé dans l’URL
     * @param id
     * @param description
     * @return
     */
    @PutMapping("taches/{id}/{description}")
    Tache editerTache(@PathVariable Long id, @PathVariable String description){
        Tache tache = tacheService.recupererTache(id);
        return tacheService.majTache(tache, description);
    }

    /**
     * méthode permettant de supprimer une tâche en précisant son id
     * @param id
     * @return
     */
    @DeleteMapping("taches/{id}")
    boolean supprimerTache(@PathVariable Long id){
        return tacheService.supprimerTache(id);
    }

    /**
     * méthode permettant d’obtenir une page de toutes les tâches placées dans une
     * colonne
     * @param id
     * @param pageable
     * @return
     */
    @GetMapping("colonnes/{id}/taches")
    Page<Tache> recupererTachesParColonne(@PathVariable Long id,
                                          @PageableDefault(size=10,page=0,sort="dateCreation",direction = Sort.Direction.DESC) Pageable pageable){
        System.out.println(id);
        Colonne colonne = colonneService.recupererColonne(id);
        return tacheService.recupererTaches(colonne,pageable);
    }

    /**
     * méthode permettant d’obtenir les tâches ayant le statut « à faire » et confiées
     * à un développeur en particulier
     * @param id
     * @return
     */
    @GetMapping("developpeurs/{id}/tachesAfaire")
    List<Tache> recupererTachesAFaire(@PathVariable Long id){
        Developpeur developpeur = developpeurService.recupererDeveloppeur(id);
        System.out.println(developpeur);
        return tacheService.recupererTachesAFaire(developpeur);
    }

    /**
     * méthode permettant d’obtenir toutes les tâches dont l’intitulé contient le mot
     * précisé dans l’URL
     * @param intitule
     * @return
     */
    @GetMapping("taches")
    List<Tache> recupererTaches(@RequestParam String intitule){
        return tacheService.recupererTaches(intitule);
    }


    /**
     * méthode permettant de déterminer le total des heures prévues pour les
     * tâchées créées entre deux dates données en paramètre
     * @param dateDebut
     * @param dateFin
     * @return
     */
    @GetMapping("totalHeuresPrevues")
    int recupererTotalHeuresPrevues( @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "dateDebut", required = false) Date dateDebut,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "dateFin", required = false) Date dateFin ){
        return tacheService.recupererTotalHeuresPrevues(dateDebut,dateFin);
    }

    /**
     * méthode permettant de supprimer toutes les tâches d’une colonne
     * @param id
     * @return
     */
    @DeleteMapping("colonnes/{id}/taches")
    public boolean supprimerTaches(@PathVariable Long id){
        Colonne colonne = colonneService.recupererColonne(id);
        return colonneService.supprimerTaches(colonne);
    }

    /**
     * méthode permettant de gérer le déplacement d’une tâche effectué sur le
     * tableau Kanban de la page Web de l’application
     * @param idTache
     * @param idColonne
     * @return
     */
    @PutMapping("taches/{idTache}/colonnes/{idColonne}")
    public Tache deplacerTache(@PathVariable Long idTache,@PathVariable Long idColonne){
        Tache tache = tacheService.recupererTache(idTache);
        Colonne colonne = colonneService.recupererColonne(idColonne);
        return tacheService.deplacerTache(tache,colonne);
    }

}
