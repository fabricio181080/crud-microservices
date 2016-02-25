package com.matera.crudmicroservices.cache;

import java.io.Serializable;

import rx.Observable;

public interface Cache {

	<T> Observable<T> get(String key);
	
	void set(String key, Serializable value);
	
}
