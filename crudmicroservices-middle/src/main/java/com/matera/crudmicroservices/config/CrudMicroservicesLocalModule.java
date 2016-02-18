package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class CrudMicroservicesLocalModule extends AbstractModule {

	@Override
	protected void configure() {
		
	}

	@Provides
	public Session cassandraSession() {
		return Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices");
	}
	
}
