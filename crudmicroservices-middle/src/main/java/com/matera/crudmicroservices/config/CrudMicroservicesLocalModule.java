package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.matera.crudmicroservices.service.PersonService;
import com.matera.crudmicroservices.service.impl.PersonServiceImpl;
import com.matera.crudmicroservices.store.PersonStore;
import com.matera.crudmicroservices.store.impl.PersonStoreImpl;
import com.netflix.governator.guice.lazy.LazySingletonScope;

public class CrudMicroservicesLocalModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersonStore.class).to(PersonStoreImpl.class).in(LazySingletonScope.get());
		bind(PersonService.class).to(PersonServiceImpl.class).in(LazySingletonScope.get());
	}

	@Provides
	public Session cassandraSession() {
		return Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices");
	}
	
}
