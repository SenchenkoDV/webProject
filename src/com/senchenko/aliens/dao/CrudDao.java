package com.senchenko.aliens.dao;

import com.senchenko.aliens.entity.Entity;
import java.util.List;

public interface CrudDao<T extends Entity> {
    List<T> findAll();
    T findById(int id);
    boolean deleteById(int id);
    boolean delete(T entity);
    boolean create(T entity);
    T update(T entity);
}