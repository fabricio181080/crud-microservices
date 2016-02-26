package com.matera.crudmicroservices.config;

import com.google.inject.Binder;
import com.netflix.karyon.server.ServerBootstrap;

/**
 * @author Igor K. Shiohara
 */
public class Bootstrap extends ServerBootstrap {

	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new CrudMicroservicesModule());
	}
	
}
