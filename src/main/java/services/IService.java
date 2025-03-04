package services;

import java.util.List;
import models.Panier;
public interface IService <T>  {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public T getOne(T t);
    public List<T> getAll(T t);
    List<Panier> trierParTotal(boolean ascendant);
}
