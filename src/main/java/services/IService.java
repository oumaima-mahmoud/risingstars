package services;

import java.util.List;

public interface IService<T> {
    void ajouter(T var1);

    void modifier(T var1);

    void supprimer(int var1);

    T getOne(T var1);

    List<T> getAll(T var1);
}

