package com.matera.crudmicroservices.cache;

import rx.Observable;

public interface Cache {

	<T> Observable<T> get(String key);
	
	void set(String key, Object value);
	
}
