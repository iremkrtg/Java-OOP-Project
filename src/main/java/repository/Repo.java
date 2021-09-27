package repository;

import model.Identifiable;

import java.util.Collection;
import java.util.Collections;

public interface Repo<T extends Identifiable<ID>, ID> {
    T add(T object);
    void delete(ID id);
    void update(ID id, T object);
    T findByID(ID id);
    Iterable<T> findAll();
    Collection<T> getAll();
}