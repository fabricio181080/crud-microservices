package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class CrudMicroservicesLocalModule extends AbstractModule {

	@Override
	protected void configure() {
		
	}

	@Provides
	public Supplier<Session> cassandraSession() {
		return Suppliers.ofInstance(Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices"));
	}
	
}
