package com.epam.esm.dao;

import java.util.List;

public interface BaseDao<T> {

    boolean add(T t);

    T find(Long id);

    List<T> findAll(Integer limit, Integer offset);

    T update(T t);

    boolean delete(Long id);

}
