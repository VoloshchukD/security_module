package com.epam.esm.service;

import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;

import java.util.List;

/**
 * Base interface with logics for every program entity.
 *
 * @author Daniil Valashchuk
 */
public interface BaseService<T> {

    /**
     * Method with saving entity data logics.
     *
     * @param t - required entity to add
     * @return boolean result of saving
     */
    boolean add(T t);

    /**
     * Method with entity seeking logics.
     *
     * @param id - required entity identifier
     * @return founded entity
     * @throws {@link ParameterNotPresentException}
     * @throws {@link DataNotFoundException} when entity do not exist
     */
    T find(Long id) throws ParameterNotPresentException, DataNotFoundException;

    /**
     * Method with all entities seeking logics.
     *
     * @param page      - required page number with data
     * @param itemCount - required items amount for the page
     * @return list of all founded entities
     * @throws {@link IllegalPageNumberException} when page invalid number
     */
    List<T> findAll(Integer page, Integer itemCount) throws IllegalPageNumberException;

    /**
     * Method with entity data refresh.
     *
     * @param t - required entity data for update
     * @return updated entity
     * @throws {@link ParameterNotPresentException}
     * @throws {@link DataNotFoundException} when entity do not exist
     */
    T update(T t) throws ParameterNotPresentException, DataNotFoundException;

    /**
     * Method with entity data deletion.
     *
     * @param id - identifier of target entity
     * @return boolean result of deletion
     * @throws {@link ParameterNotPresentException}
     * @throws {@link DataNotFoundException}
     */
    boolean delete(Long id) throws ParameterNotPresentException, DataNotFoundException;

}
