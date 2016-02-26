package com.matera.crudmicroservices.config;

import com.google.inject.Binder;
import com.netflix.karyon.server.ServerBootstrap;

/**
 * @author Igor K. Shiohara
 */
public class LocalBootstrap extends ServerBootstrap {

	@Override
	protected void configureBinder(Binder binder) {
		
		binder.install(new CrudMicroservicesLocalModule());
	}

}
