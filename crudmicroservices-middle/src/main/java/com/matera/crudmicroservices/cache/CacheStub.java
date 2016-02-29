package com.matera.crudmicroservices.cache;

import java.util.Map;

import com.google.common.collect.Maps;

import rx.Observable;

public class CacheStub implements Cache {

	private Map<String, Object> cache = Maps.newHashMap();
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Observable<T> get(String key) {
		final T cached = (T) cache.get(key);
		if (cached == null) {
			return Observable.empty();
		}
		if (cached instanceof Iterable) {
			return Observable.from((Iterable<T>) cached);
		}
		return Observable.just(cached);
	}

	@Override
	public void set(String key, Object value) {
		cache.put(key, value);
	}

}
