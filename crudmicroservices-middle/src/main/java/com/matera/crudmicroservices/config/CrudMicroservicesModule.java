package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.matera.crudmicroservices.service.PersonService;
import com.matera.crudmicroservices.service.impl.PersonServiceImpl;
import com.matera.crudmicroservices.store.PersonStore;
import com.matera.crudmicroservices.store.impl.PersonStoreImpl;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.governator.guice.lazy.LazySingletonScope;

public class CrudMicroservicesModule extends AbstractModule {

	private final DynamicStringProperty cassandraHost = 
			DynamicPropertyFactory.getInstance()
				.getStringProperty("crudmicroservices.cassandra.host", "");
	
	private final DynamicStringProperty cassandraKeyspace = 
			DynamicPropertyFactory.getInstance().getStringProperty("crudmicroservices.cassandra.keyspace", "");
	
	@Override
	protected void configure() {
		bind(PersonStore.class).to(PersonStoreImpl.class).in(LazySingletonScope.get());
		bind(PersonService.class).to(PersonServiceImpl.class).in(LazySingletonScope.get());
	}

	@Provides
	public Supplier<Session> cassandraSession() {
		return Suppliers.ofInstance(Cluster.builder().addContactPoint(cassandraHost.get()).build().connect(cassandraKeyspace.get()));
	}
	
}
