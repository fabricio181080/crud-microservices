package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

public class CrudMicroservicesModule extends AbstractModule {

	private final DynamicStringProperty cassandraHost = 
			DynamicPropertyFactory.getInstance()
				.getStringProperty("crudmicroservices.cassandra.host", "");
	
	private final DynamicStringProperty cassandraKeyspace = 
			DynamicPropertyFactory.getInstance().getStringProperty("crudmicroservices.cassandra.keyspace", "");
	
	@Override
	protected void configure() {
		
	}

	@Provides
	public Supplier<Session> cassandraSession() {
		return Suppliers.ofInstance(Cluster.builder().addContactPoint(cassandraHost.get()).build().connect(cassandraKeyspace.get()));
	}
	
}
