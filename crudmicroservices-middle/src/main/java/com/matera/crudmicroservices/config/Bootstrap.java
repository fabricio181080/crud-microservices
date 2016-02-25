package com.matera.crudmicroservices.config;

import com.google.inject.Binder;
import com.netflix.karyon.server.ServerBootstrap;

public class Bootstrap extends ServerBootstrap {

	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new RESTModule());
		binder.install(new CrudMicroservicesLocalModule());
	}
	
}
