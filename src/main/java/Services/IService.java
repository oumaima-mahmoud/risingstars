
package Services;

import java.util.List;

public interface IService<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    T getOne(int id);
    List<T> getAll();
}