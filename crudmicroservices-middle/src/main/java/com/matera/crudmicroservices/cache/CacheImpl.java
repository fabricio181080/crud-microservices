package com.matera.crudmicroservices.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheException;

import rx.Observable;

@SuppressWarnings("unchecked")
public class CacheImpl implements Cache {

	static final Logger logger = LoggerFactory.getLogger(CacheImpl.class);
	
	private DynamicIntProperty timeToLive = DynamicPropertyFactory.getInstance().getIntProperty("crudmicroservices.cache.ttl", 900);
	
	private final Provider<EVCache> delegate;

	@Inject
	public CacheImpl(Provider<EVCache> delegate) {
		this.delegate = delegate;
	}
	
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

	@SuppressWarnings("rawtypes")
	public void set(String key, Object value) {
		try {
			final EVCache cache = delegate.get();
			if (value instanceof Iterable) {
				cache.set(key, Lists.newArrayList((Iterable)value), timeToLive.get());
			} else {
				cache.set(key, value, timeToLive.get());
			}
		} catch (EVCacheException throwable) {
			logger.warn("Failed to cache {}", value, throwable);
		}
	}
	
}
