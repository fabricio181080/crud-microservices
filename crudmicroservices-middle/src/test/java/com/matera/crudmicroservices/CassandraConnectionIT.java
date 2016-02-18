package com.matera.crudmicroservices;

import org.junit.Assert;
import org.junit.Test;

import com.datastax.driver.core.Session;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.matera.crudmicroservices.config.CrudMicroservicesLocalModule;

public class CassandraConnectionIT {

	@Test
	public void shouldConnect() {
	
		Injector injector = Guice.createInjector(new CrudMicroservicesLocalModule());
		Provider<Session> session = injector.getProvider(Session.class);
		
		Assert.assertNotNull(session.get());
		Assert.assertFalse(session.get().isClosed());
	}
	
	
}
