package com.matera.crudmicroservices.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.matera.crudmicroservices.service.PersonCacheKey.byId;
import static com.matera.crudmicroservices.service.PersonCacheKey.byName;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.matera.crudmicroservices.cache.Cache;
import com.matera.crudmicroservices.core.convert.PersonConverter;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.store.PersonStore;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.functions.Action1;

public class PersonService {

    static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    private final PersonStore personStore;
    private final Cache cache;

    @Inject
    public PersonService(PersonStore personStore, Cache cache) {
        this.personStore = personStore;
        this.cache = cache;
    }

    /**
     * 
     * @return an {@link Observable} of {@link Person} with all persons found on
     *         store.
     */
    public Observable<Person> findAll() {

        return personStore.findAll().map(PersonConverter::toEntity);
    }

    /**
     * Search person using provided id
     * 
     * @param id
     * @return an {@link Observable} of {@link Person} with a single value
     */
    public Observable<Person> findById(long id) {

        return cache.get(PersonCacheKey.byId(id)).cast(Person.class)
            .switchIfEmpty(personStore.findById(id).map(PersonConverter::toEntity).doOnNext(cache(byId(id))));
    }

    /**
     * Search person using provided name
     * 
     * @param name
     * @return an {@link Observable} of {@link Person} with provided name
     */
    public Observable<Person> findByName(String name) {

        checkArgument(StringUtils.isNotBlank(name), "name mustn't be null or empty");

        return cache.get(byName(name)).cast(Person.class)
            .switchIfEmpty(personStore.findByName(name).map(PersonConverter::toEntity).toList()
                .doOnNext(cache(byName(name))).flatMap((persons) -> Observable.from(persons)));

    }

    /**
     * Insert a {@link Person} into the data store.
     * 
     * @param person object to insert
     * @return inserted person
     */
    public Observable<Person> insert(Person person) {

        //checkArgument(person.getId() != null, "id must not be empty");
        checkArgument(!Strings.isNullOrEmpty(person.getName()), "name must not be empty");
        checkArgument(personStore.findById(person.getId()).isEmpty().toBlocking().single(), "id must be unique");

        personStore.save(PersonConverter.toDomain(person));

        return this.findById(person.getId());
    }

    /**
     * Updates a {@link Person} given a valid object.
     * 
     * @param person the person object
     * @return updated person
     */
    public Observable<Person> update(Long id, Person person) {

        checkArgument(!personStore.findById(id).isEmpty().toBlocking().single(),
            String.format("person not found for %d", id));
        checkArgument(person.getId() != null, "id must not be empty");
        checkArgument(!Strings.isNullOrEmpty(person.getName()), "name must not be empty");
        
        person.setId(id);
        personStore.update(PersonConverter.toDomain(person));

        return this.findById(person.getId());
    }

    /**
     * Deletes a {@link Person} by its id.
     * 
     * @param id the person id
     * @return nothing
     */
    public void delete(Long id) {

        personStore.delete(personStore.findById(id).toBlocking().single());
    }

    private Action1<Object> cache(String key) {

        return (value) -> cache.set(key, value);
    }

}
