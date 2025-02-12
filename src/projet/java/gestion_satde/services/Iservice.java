package gestion_satde.services;
import java.util.List;
public interface Iservice <T> {
    public void ajouter(T t);
    public void modifier(T t,int id);
    public void supprimer(int id);
    public T getOne(int id);
    public List<T> getAll();
}
