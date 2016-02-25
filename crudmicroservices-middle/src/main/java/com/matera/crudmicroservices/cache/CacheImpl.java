package com.matera.crudmicroservices.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheException;

import rx.Observable;

public class CacheImpl implements Cache {

	static final Logger logger = LoggerFactory.getLogger(CacheImpl.class);
	
	private final Provider<EVCache> delegate;

	@Inject
	public CacheImpl(Provider<EVCache> delegate) {
		this.delegate = delegate;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Observable<T> get(String key) {
		try {
			final EVCache cache = delegate.get();
			
			final T cached = cache.get(key);
			if (cached == null) {
				return Observable.empty();
			}
			if (cached instanceof Iterable) {
				return Observable.from((Iterable<T>)cached);
			}
			return Observable.just(cached);
			
		} catch (EVCacheException throwable) {
			logger.warn("Cache lookup failed for {}", key, throwable);
		}
		return Observable.empty();
	}

	public void set(String key, Serializable value) {
		try {
			final EVCache cache = delegate.get();
			cache.set(key, value);
		} catch (EVCacheException throwable) {
			logger.warn("Failed to cache {}", value, throwable);
		}
	}
	
}
