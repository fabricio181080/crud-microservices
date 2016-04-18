package com.matera.crudmicroservices.store;

import com.matera.crudmicroservices.core.domain.Person;

import rx.Observable;

public interface PersonStore {

    /**
     * 
     * @return an {@link Observable} of {@link Person} with all persons found on
     *         store.
     */
    Observable<Person> findAll();

    /**
     * Search person using provided id
     * 
     * @param id
     * @return an {@link Observable} of {@link Person} with a single value
     */
    Observable<Person> findById(long id);

    /**
     * Search person using provided name
     * 
     * @param name
     * @return an {@link Observable} of {@link Person} with provided name
     */
    Observable<Person> findByName(String name);

    /**
     * Save person
     * 
     * @param person
     */
    void save(Person person);

    /**
     * Update person
     * 
     * @throws // TODO, if that person doesn't exist
     * @param person
     */
    void update(Person person);

    /**
     * Delete person
     * 
     * @throws // TODO, if that person doesn't exist
     * @param person
     */
    void delete(Person person);

    /**
     * Retrieves max id from database
     * 
     * @return maxId
     */
    Long getMaxId();
}
