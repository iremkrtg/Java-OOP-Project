package repository;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.Identifiable;

public abstract class AbstractRepo <T extends Identifiable<ID>, ID> implements Repo<T, ID>, Serializable {
    protected Map<ID, T> elements;

    public AbstractRepo(){ elements = new HashMap<>();
    }


    @Override
    public T add(T object) {
        if(elements.containsKey(object.getID())){
            throw new RuntimeException("This element already exists." + object.getID());
        }
        else
            elements.put(object.getID(), object);
        return object;
    }

    @Override
    public void delete(ID id) {
        if(elements.containsKey(id))
            elements.remove(id);
    }

    @Override
    public void update(ID id, T object) {
        if(elements.containsKey(id))
            elements.put(object.getID(), object);
        else
            throw new RuntimeException("This element does not exist.");
    }

    @Override
    public T findByID(ID id) {
        if(elements.containsKey(id))
            return elements.get(id);
        else
            throw new RuntimeException("Element does not exists." + id);
    }

    @Override
    public Iterable<T> findAll() {
        return elements.values();
    }

    @Override
    public Collection<T> getAll(){ return elements.values(); }
}

