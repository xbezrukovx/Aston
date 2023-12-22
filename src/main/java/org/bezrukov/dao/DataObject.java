package org.bezrukov.dao;

import org.bezrukov.model.Entity;

import java.util.List;

public interface DataObject <T extends Entity>{
    /**
     * Этот метод создаёт новую сущность или обновляет существующую, если такая есть.
     * @param entity
     * @return
     */
    T save(T entity);
    T getByID(Integer id);
    List<T> getAll();
    boolean remove(T entity);
}
