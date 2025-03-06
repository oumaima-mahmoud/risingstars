package services;

import java.util.List;
public interface IServiceCommande<T> {
    void ajouter(T t) ;
    void modifier(T t) ;
    void supprimer(int id);
    List<T> afficher();

}

