package com.matera.crudmicroservices.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.matera.crudmicroservices.service.PersonCacheKey.byId;
import static com.matera.crudmicroservices.service.PersonCacheKey.byName;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.matera.crudmicroservices.cache.Cache;
import com.matera.crudmicroservices.core.convert.PersonConverter;
import com.matera.crudmicroservices.core.entities.Person;
import com.matera.crudmicroservices.store.PersonStore;

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
	 * @return an {@link Observable} of {@link Person} with all persons
	 * found on store.
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
	
		return cache.get(PersonCacheKey.byId(id))
				.cast(Person.class)
				.switchIfEmpty(
						personStore.findById(id)
							.map(PersonConverter::toEntity)
							.doOnNext(cache(byId(id)))
					);
	}

	/**
	 * Search person using provided name
	 * 
	 * @param name
	 * @return an {@link Observable} of {@link Person} with provided name
	 */
	public Observable<Person> findByName(String name) {
		
		checkArgument(StringUtils.isNotBlank(name), "name mustn't be null or empty");
		
		return cache.get(byName(name))
					.cast(Person.class)
					.switchIfEmpty(
							personStore.findByName(name)
								.map(PersonConverter::toEntity)
								.toList()
								.doOnNext(cache(byName(name)))
								.flatMap((persons) -> Observable.from(persons))
						);
		
	}
	
	private Action1<Object> cache(String key) {
		return (value) -> cache.set(key, value);
	}
	
}
