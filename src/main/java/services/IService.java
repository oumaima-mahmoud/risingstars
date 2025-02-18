package services;

import java.util.List;

/**
 * Interface générique pour définir les opérations CRUD (Create, Read, Update, Delete).
 *
 * @param <T> Le type d'entité géré par ce service.
 */
public interface IService<T> {

    /**
     * Ajoute une nouvelle entité dans la base de données.
     *
     * @param t L'entité à ajouter.
     */
    void ajouter(T t);

    /**
     * Met à jour une entité existante dans la base de données.
     *
     * @param t L'entité mise à jour.
     */
    void modifier(T t);

    /**
     * Supprime une entité de la base de données en fonction de son ID.
     *
     * @param id L'ID de l'entité à supprimer.
     */
    void supprimer(int id);

    /**
     * Récupère une entité spécifique en fonction de son ID.
     *
     * @param t L'entité contenant l'ID à rechercher.
     * @return L'entité correspondante ou null si elle n'existe pas.
     */
    T getOne(T t);

    /**
     * Récupère toutes les entités de la base de données.
     *
     * @return Une liste de toutes les entités.
     */
    List<T> getAll();
}